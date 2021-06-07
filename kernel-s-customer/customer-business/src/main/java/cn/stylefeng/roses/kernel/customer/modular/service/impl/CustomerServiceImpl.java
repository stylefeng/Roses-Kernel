package cn.stylefeng.roses.kernel.customer.modular.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kernel.auth.api.SessionManagerApi;
import cn.stylefeng.roses.kernel.auth.api.exception.AuthException;
import cn.stylefeng.roses.kernel.auth.api.exception.enums.AuthExceptionEnum;
import cn.stylefeng.roses.kernel.auth.api.expander.AuthConfigExpander;
import cn.stylefeng.roses.kernel.auth.api.password.PasswordStoredEncryptApi;
import cn.stylefeng.roses.kernel.auth.api.pojo.auth.LoginRequest;
import cn.stylefeng.roses.kernel.auth.api.pojo.auth.LoginResponse;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.LoginUser;
import cn.stylefeng.roses.kernel.customer.api.exception.CustomerException;
import cn.stylefeng.roses.kernel.customer.api.exception.enums.CustomerExceptionEnum;
import cn.stylefeng.roses.kernel.customer.modular.entity.Customer;
import cn.stylefeng.roses.kernel.customer.modular.factory.CustomerFactory;
import cn.stylefeng.roses.kernel.customer.modular.mapper.CustomerMapper;
import cn.stylefeng.roses.kernel.customer.modular.request.CustomerRequest;
import cn.stylefeng.roses.kernel.customer.modular.service.CustomerService;
import cn.stylefeng.roses.kernel.db.api.factory.PageFactory;
import cn.stylefeng.roses.kernel.db.api.factory.PageResultFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.email.api.MailSenderApi;
import cn.stylefeng.roses.kernel.email.api.pojo.SendMailParam;
import cn.stylefeng.roses.kernel.jwt.api.context.JwtContext;
import cn.stylefeng.roses.kernel.jwt.api.pojo.payload.DefaultJwtPayload;
import cn.stylefeng.roses.kernel.log.api.LoginLogServiceApi;
import cn.stylefeng.roses.kernel.rule.enums.StatusEnum;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import cn.stylefeng.roses.kernel.rule.exception.base.ServiceException;
import cn.stylefeng.roses.kernel.rule.exception.enums.defaults.DefaultBusinessExceptionEnum;
import cn.stylefeng.roses.kernel.rule.util.HttpServletUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * C端用户表业务实现层
 *
 * @author fengshuonan
 * @date 2021/06/07 11:40
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {

    /**
     * 用于操作缓存时候加锁
     */
    private static final Object SESSION_OPERATE_LOCK = new Object();

    @Resource
    private MailSenderApi mailSenderApi;

    @Resource
    private PasswordStoredEncryptApi passwordStoredEncryptApi;

    @Resource
    private SessionManagerApi sessionManagerApi;

    @Resource
    private LoginLogServiceApi loginLogServiceApi;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void reg(CustomerRequest customerRequest) {

        // 创建C端用户
        Customer regCustomer = CustomerFactory.createRegCustomer(customerRequest);

        // 保存用户
        this.save(regCustomer);

        // 发送邮箱验证码
        SendMailParam regEmailParam = CustomerFactory.createRegEmailParam(regCustomer.getEmail(), regCustomer.getVerifyCode());
        mailSenderApi.sendMail(regEmailParam);
    }

    @Override
    public void active(CustomerRequest customerRequest) {

        // 更新验证码的账号为激活状态
        LambdaUpdateWrapper<Customer> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(Customer::getVerifiedFlag, YesOrNotEnum.Y.getCode());
        wrapper.eq(Customer::getVerifyCode, customerRequest.getVerifyCode());
        this.update(wrapper);
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {

        // 不创建cookie，默认开启记住我（7天会话）
        loginRequest.setCreateCookie(false);
        loginRequest.setRememberMe(true);

        // 查询用户信息
        LambdaQueryWrapper<Customer> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Customer::getAccount, loginRequest.getAccount())
                .or().eq(Customer::getEmail, loginRequest.getAccount())
                .or().eq(Customer::getTelephone, loginRequest.getAccount());
        Customer customer = this.getOne(wrapper, false);
        if (customer == null) {
            throw new CustomerException(CustomerExceptionEnum.CANT_FIND_CUSTOMER, loginRequest.getAccount());
        }

        // 校验用户密码
        Boolean passwordFlag = passwordStoredEncryptApi.checkPassword(loginRequest.getPassword(), customer.getPassword());
        if (!passwordFlag) {
            throw new AuthException(AuthExceptionEnum.USERNAME_PASSWORD_ERROR);
        }

        // 校验用户状态
        if (!StatusEnum.ENABLE.getCode().equals(customer.getStatusFlag())) {
            throw new CustomerException(CustomerExceptionEnum.CUSTOMER_STATUS_ERROR, customer.getStatusFlag());
        }

        // 获取LoginUser，用于用户的缓存
        LoginUser loginUser = CustomerFactory.createLoginUser(customer);

        // 生成用户的token
        DefaultJwtPayload defaultJwtPayload = new DefaultJwtPayload(loginUser.getUserId(), loginUser.getAccount(), loginRequest.getRememberMe(), null);
        String jwtToken = JwtContext.me().generateTokenDefaultPayload(defaultJwtPayload);
        loginUser.setToken(jwtToken);

        synchronized (SESSION_OPERATE_LOCK) {
            // 缓存用户信息，创建会话
            sessionManagerApi.createSession(jwtToken, loginUser, loginRequest.getCreateCookie());

            // 如果开启了单账号单端在线，则踢掉已经上线的该用户
            if (AuthConfigExpander.getSingleAccountLoginFlag()) {
                sessionManagerApi.removeSessionExcludeToken(jwtToken);
            }
        }

        // 更新用户ip和登录时间
        String ip = HttpServletUtil.getRequestClientIp(HttpServletUtil.getRequest());
        LambdaUpdateWrapper<Customer> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(Customer::getLastLoginIp, ip);
        updateWrapper.set(Customer::getLastLoginTime, new Date());
        this.update(updateWrapper);

        // 登录成功日志
        loginLogServiceApi.loginSuccess(loginUser.getUserId());

        // 组装返回结果
        return new LoginResponse(loginUser, jwtToken, defaultJwtPayload.getExpirationDate());
    }

    @Override
    public void add(CustomerRequest customerRequest) {
        Customer customer = new Customer();
        BeanUtil.copyProperties(customerRequest, customer);
        this.save(customer);
    }

    @Override
    public void del(CustomerRequest customerRequest) {
        Customer customer = this.queryCustomer(customerRequest);
        this.removeById(customer.getCustomerId());
    }

    @Override
    public void edit(CustomerRequest customerRequest) {
        Customer customer = this.queryCustomer(customerRequest);
        BeanUtil.copyProperties(customerRequest, customer);
        this.updateById(customer);
    }

    @Override
    public Customer detail(CustomerRequest customerRequest) {
        return this.queryCustomer(customerRequest);
    }

    @Override
    public PageResult<Customer> findPage(CustomerRequest customerRequest) {
        LambdaQueryWrapper<Customer> wrapper = createWrapper(customerRequest);
        Page<Customer> sysRolePage = this.page(PageFactory.defaultPage(), wrapper);
        return PageResultFactory.createPageResult(sysRolePage);
    }

    @Override
    public List<Customer> findList(CustomerRequest customerRequest) {
        LambdaQueryWrapper<Customer> wrapper = this.createWrapper(customerRequest);
        return this.list(wrapper);
    }

    /**
     * 获取信息
     *
     * @author fengshuonan
     * @date 2021/06/07 11:40
     */
    private Customer queryCustomer(CustomerRequest customerRequest) {
        Customer customer = this.getById(customerRequest.getCustomerId());
        if (ObjectUtil.isEmpty(customer)) {
            throw new ServiceException(DefaultBusinessExceptionEnum.SYSTEM_RUNTIME_ERROR);
        }
        return customer;
    }

    /**
     * 创建查询wrapper
     *
     * @author fengshuonan
     * @date 2021/06/07 11:40
     */
    private LambdaQueryWrapper<Customer> createWrapper(CustomerRequest customerRequest) {
        LambdaQueryWrapper<Customer> queryWrapper = new LambdaQueryWrapper<>();

        Long customerId = customerRequest.getCustomerId();
        String account = customerRequest.getAccount();
        String password = customerRequest.getPassword();
        String nickName = customerRequest.getNickName();
        String email = customerRequest.getEmail();
        String telephone = customerRequest.getTelephone();
        Long avatar = customerRequest.getAvatar();
        String avatarObjectName = customerRequest.getAvatarObjectName();
        Integer score = customerRequest.getScore();

        queryWrapper.eq(ObjectUtil.isNotNull(customerId), Customer::getCustomerId, customerId);
        queryWrapper.like(ObjectUtil.isNotEmpty(account), Customer::getAccount, account);
        queryWrapper.like(ObjectUtil.isNotEmpty(password), Customer::getPassword, password);
        queryWrapper.like(ObjectUtil.isNotEmpty(nickName), Customer::getNickName, nickName);
        queryWrapper.like(ObjectUtil.isNotEmpty(email), Customer::getEmail, email);
        queryWrapper.like(ObjectUtil.isNotEmpty(telephone), Customer::getTelephone, telephone);
        queryWrapper.eq(ObjectUtil.isNotNull(avatar), Customer::getAvatar, avatar);
        queryWrapper.like(ObjectUtil.isNotEmpty(avatarObjectName), Customer::getAvatarObjectName, avatarObjectName);
        queryWrapper.eq(ObjectUtil.isNotNull(score), Customer::getScore, score);

        return queryWrapper;
    }

}