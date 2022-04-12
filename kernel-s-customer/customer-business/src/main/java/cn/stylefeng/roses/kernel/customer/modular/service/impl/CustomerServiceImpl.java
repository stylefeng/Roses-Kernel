package cn.stylefeng.roses.kernel.customer.modular.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.auth.api.SessionManagerApi;
import cn.stylefeng.roses.kernel.auth.api.context.LoginContext;
import cn.stylefeng.roses.kernel.auth.api.exception.AuthException;
import cn.stylefeng.roses.kernel.auth.api.exception.enums.AuthExceptionEnum;
import cn.stylefeng.roses.kernel.auth.api.expander.AuthConfigExpander;
import cn.stylefeng.roses.kernel.auth.api.password.PasswordStoredEncryptApi;
import cn.stylefeng.roses.kernel.auth.api.pojo.auth.LoginRequest;
import cn.stylefeng.roses.kernel.auth.api.pojo.auth.LoginResponse;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.LoginUser;
import cn.stylefeng.roses.kernel.cache.api.CacheOperatorApi;
import cn.stylefeng.roses.kernel.customer.api.OldPasswordValidateApi;
import cn.stylefeng.roses.kernel.customer.api.constants.CustomerConstants;
import cn.stylefeng.roses.kernel.customer.api.exception.CustomerException;
import cn.stylefeng.roses.kernel.customer.api.exception.enums.CustomerExceptionEnum;
import cn.stylefeng.roses.kernel.customer.api.expander.CustomerConfigExpander;
import cn.stylefeng.roses.kernel.customer.api.pojo.CustomerInfo;
import cn.stylefeng.roses.kernel.customer.api.pojo.CustomerInfoRequest;
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
import cn.stylefeng.roses.kernel.file.api.FileInfoApi;
import cn.stylefeng.roses.kernel.file.api.FileOperatorApi;
import cn.stylefeng.roses.kernel.file.api.pojo.response.SysFileInfoResponse;
import cn.stylefeng.roses.kernel.jwt.api.context.JwtContext;
import cn.stylefeng.roses.kernel.jwt.api.pojo.payload.DefaultJwtPayload;
import cn.stylefeng.roses.kernel.log.api.LoginLogServiceApi;
import cn.stylefeng.roses.kernel.rule.enums.StatusEnum;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import cn.stylefeng.roses.kernel.rule.exception.base.ServiceException;
import cn.stylefeng.roses.kernel.rule.exception.enums.defaults.DefaultBusinessExceptionEnum;
import cn.stylefeng.roses.kernel.rule.util.HttpServletUtil;
import cn.stylefeng.roses.kernel.security.api.DragCaptchaApi;
import cn.stylefeng.roses.kernel.security.api.ImageCaptchaApi;
import cn.stylefeng.roses.kernel.security.api.expander.SecurityConfigExpander;
import cn.stylefeng.roses.kernel.validator.api.exception.enums.ValidatorExceptionEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {

    /**
     * 用于操作缓存时候加锁
     */
    private static final Object SESSION_OPERATE_LOCK = new Object();

    /**
     * 用于注册用户时候的加锁
     */
    private static final Object REG_LOCK = new Object();

    @Resource
    private MailSenderApi mailSenderApi;

    @Resource
    private PasswordStoredEncryptApi passwordStoredEncryptApi;

    @Resource
    private SessionManagerApi sessionManagerApi;

    @Resource
    private LoginLogServiceApi loginLogServiceApi;

    @Resource
    private CacheOperatorApi<CustomerInfo> customerInfoCacheOperatorApi;

    @Resource
    private FileOperatorApi fileOperatorApi;

    @Resource
    private FileInfoApi fileInfoApi;

    @Resource
    private DragCaptchaApi dragCaptchaApi;

    @Resource
    private OldPasswordValidateApi oldPasswordValidateApi;

    @Resource
    private ImageCaptchaApi imageCaptchaApi;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void reg(CustomerRequest customerRequest) {

        // 验证验证码
        this.validateCaptcha(customerRequest.getVerKey(), customerRequest.getVerCode());

        synchronized (REG_LOCK) {
            // 校验邮箱和账号是否重复
            validateRepeat(customerRequest);

            // 创建C端用户
            Customer regCustomer = CustomerFactory.createRegCustomer(customerRequest);

            // 保存用户
            this.save(regCustomer);

            // 发送邮箱验证码
            try {
                SendMailParam regEmailParam = CustomerFactory.createRegEmailParam(regCustomer.getEmail(), regCustomer.getVerifyCode());
                mailSenderApi.sendMailHtml(regEmailParam);
            } catch (Exception exception) {
                log.error("注册时，发送邮件失败！", exception);
                throw new CustomerException(CustomerExceptionEnum.EMAIL_SEND_ERROR);
            }
        }
    }

    @Override
    public void active(CustomerRequest customerRequest) {
        // 更新验证码的账号为激活状态
        LambdaUpdateWrapper<Customer> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(Customer::getVerifiedFlag, YesOrNotEnum.Y.getCode());
        wrapper.eq(Customer::getVerifyCode, customerRequest.getVerifyCode());
        boolean result = this.update(wrapper);
        if (!result) {
            throw new CustomerException(CustomerExceptionEnum.ACTIVE_ERROR);
        }
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {

        // 不创建cookie，默认开启记住我（7天会话）
        loginRequest.setCreateCookie(false);
        loginRequest.setRememberMe(true);

        // 验证拖拽验证码
        this.validateCaptcha(loginRequest.getVerKey(), loginRequest.getVerCode());

        // 查询用户信息
        LambdaQueryWrapper<Customer> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Customer::getAccount, loginRequest.getAccount())
                .or().eq(Customer::getEmail, loginRequest.getAccount())
                .or().eq(Customer::getTelephone, loginRequest.getAccount());
        Customer customer = this.getOne(wrapper, false);
        if (customer == null) {
            throw new CustomerException(CustomerExceptionEnum.CANT_FIND_CUSTOMER, loginRequest.getAccount());
        }

        // 如果开启了旧版密码，并且bcrypt密码是空
        if (CustomerConfigExpander.getOldPasswordValidate()
                && customer.getPassword().equals(CustomerConstants.DEFAULT_EMPTY_PASSWORD)) {
            if (!oldPasswordValidateApi.validatePassword(loginRequest.getPassword(), customer.getOldPassword(), customer.getOldPasswordSalt())) {
                throw new AuthException(AuthExceptionEnum.USERNAME_PASSWORD_ERROR);
            }
        } else {
            // 校验用户密码
            Boolean passwordFlag = passwordStoredEncryptApi.checkPassword(loginRequest.getPassword(), customer.getPassword());
            if (!passwordFlag) {
                throw new AuthException(AuthExceptionEnum.USERNAME_PASSWORD_ERROR);
            }
        }

        // 校验用户状态
        if (!StatusEnum.ENABLE.getCode().equals(customer.getStatusFlag())) {
            throw new CustomerException(CustomerExceptionEnum.CUSTOMER_STATUS_ERROR, customer.getStatusFlag());
        }

        // 校验用户是否激活
        if (!YesOrNotEnum.Y.getCode().equals(customer.getVerifiedFlag())) {
            throw new CustomerException(CustomerExceptionEnum.CUSTOMER_NOT_VERIFIED);
        }

        // 获取LoginUser，用于用户的缓存
        LoginUser loginUser = CustomerFactory.createLoginUser(customer, fileOperatorApi);

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
        updateWrapper.eq(Customer::getCustomerId, loginUser.getUserId());
        updateWrapper.set(Customer::getLastLoginIp, ip);
        updateWrapper.set(Customer::getLastLoginTime, new Date());
        this.update(updateWrapper);

        // 登录成功日志
        loginLogServiceApi.loginSuccess(loginUser.getUserId());

        // 组装返回结果
        return new LoginResponse(loginUser, jwtToken, defaultJwtPayload.getExpirationDate());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sendResetPwdEmail(CustomerRequest customerRequest) {

        // 验证拖拽验证码
        this.validateCaptcha(customerRequest.getVerKey(), customerRequest.getVerCode());

        // 验证邮箱是否存在
        LambdaQueryWrapper<Customer> customerLambdaQueryWrapper = new LambdaQueryWrapper<>();
        customerLambdaQueryWrapper.eq(Customer::getEmail, customerRequest.getEmail());
        Customer customer = this.getOne(customerLambdaQueryWrapper, false);
        if (customer == null) {
            throw new CustomerException(CustomerExceptionEnum.CANT_FIND_CUSTOMER, customerRequest.getEmail());
        }

        // 邮箱验证码
        String randomCode = RandomUtil.randomNumbers(6);

        // 存储到数据库验证码
        customer.setVerifyCode(randomCode);
        this.updateById(customer);

        // 发送邮箱验证码
        SendMailParam resetPwdEmail = CustomerFactory.createResetPwdEmail(customerRequest.getEmail(), randomCode);
        mailSenderApi.sendMailHtml(resetPwdEmail);
    }

    @Override
    public void resetPassword(CustomerRequest customerRequest) {

        // 检查校验码是否正确
        LambdaQueryWrapper<Customer> customerLambdaQueryWrapper = new LambdaQueryWrapper<>();
        customerLambdaQueryWrapper.eq(Customer::getEmail, customerRequest.getEmail())
                .and(i -> i.eq(Customer::getVerifyCode, customerRequest.getVerifyCode()));
        Customer customer = this.getOne(customerLambdaQueryWrapper, false);

        // 如果不存在则为验证码错误
        if (customer == null) {
            throw new CustomerException(CustomerExceptionEnum.EMAIL_VERIFY_COD_ERROR);
        }

        // 根据请求密码，重置账号的密码
        String password = customerRequest.getPassword();
        String encrypt = passwordStoredEncryptApi.encrypt(password);
        customer.setPassword(encrypt);
        this.updateById(customer);

        // 清除缓存中的用户信息
        customerInfoCacheOperatorApi.remove(String.valueOf(customer.getCustomerId()));
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

        // 清除缓存中的用户信息
        customerInfoCacheOperatorApi.remove(String.valueOf(customer.getCustomerId()));
    }

    @Override
    public void edit(CustomerRequest customerRequest) {
        Customer customer = this.queryCustomer(customerRequest);
        BeanUtil.copyProperties(customerRequest, customer);
        this.updateById(customer);

        // 清除缓存中的用户信息
        customerInfoCacheOperatorApi.remove(String.valueOf(customer.getCustomerId()));
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

    @Override
    public void updatePassword(CustomerInfoRequest customerInfoRequest) {

        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setCustomerId(customerInfoRequest.getCustomerId());
        Customer customer = this.detail(customerRequest);

        // 校验旧密码是否正确
        Boolean passwordRightFlag = passwordStoredEncryptApi.checkPassword(customerInfoRequest.getOldPassword(), customer.getPassword());

        // 上一代密码校验md5
        boolean lastPasswordRightFlag = oldPasswordValidateApi.validatePassword(customerInfoRequest.getOldPassword(), customer.getOldPassword(), customer.getOldPasswordSalt());

        if (!passwordRightFlag && !lastPasswordRightFlag) {
            throw new CustomerException(CustomerExceptionEnum.PWD_ERROR);
        }

        // 更新密码
        String encryptPwd = passwordStoredEncryptApi.encrypt(customerInfoRequest.getNewPassword());
        customer.setPassword(encryptPwd);

        // 如果有上一代密码，则清空掉
        customer.setOldPassword(CustomerConstants.DEFAULT_EMPTY_PASSWORD);
        customer.setOldPasswordSalt(CustomerConstants.DEFAULT_EMPTY_PASSWORD);

        this.updateById(customer);

        // 清除缓存中的用户信息
        customerInfoCacheOperatorApi.remove(String.valueOf(customer.getCustomerId()));
    }

    @Override
    public void updateAvatar(CustomerInfoRequest customerInfoRequest) {

        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setCustomerId(customerInfoRequest.getCustomerId());
        Customer customer = this.detail(customerRequest);

        // 根据id查询文件obj名称
        SysFileInfoResponse fileInfo = fileInfoApi.getFileInfoWithoutContent(customerInfoRequest.getAvatar());

        // 更新头像
        customer.setAvatar(customerInfoRequest.getAvatar());
        customer.setAvatarObjectName(fileInfo.getFileObjectName());
        this.updateById(customer);

        // 清除缓存中的用户信息
        customerInfoCacheOperatorApi.remove(String.valueOf(customer.getCustomerId()));
    }

    @Override
    public String updateSecret() {
        // 获取当前登录用户
        Long userId = LoginContext.me().getLoginUser().getUserId();
        return this.createOrUpdateCustomerSecret(userId);
    }

    @Override
    public String createOrUpdateCustomerSecret(Long customerId) {
        if (customerId == null) {
            return null;
        }

        // 重新生成秘钥
        String randomString = RandomUtil.randomString(32);

        // 秘钥过期时间24个月
        Date memberExpireTime = DateUtil.offset(new Date(), DateField.MONTH, 24);

        // 更新用户秘钥
        LambdaUpdateWrapper<Customer> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(Customer::getSecretKey, randomString);
        wrapper.set(Customer::getMemberExpireTime, memberExpireTime);
        wrapper.eq(Customer::getCustomerId, customerId);
        this.update(wrapper);

        // 清除缓存中的用户信息
        customerInfoCacheOperatorApi.remove(String.valueOf(customerId));

        return randomString;
    }

    @Override
    public CustomerInfo getCustomerInfoByKeyWords(String keyWords) {
        LambdaQueryWrapper<Customer> customerLambdaQueryWrapper = new LambdaQueryWrapper<>();
        customerLambdaQueryWrapper.eq(Customer::getAccount, keyWords)
                .or().eq(Customer::getEmail, keyWords)
                .or().eq(Customer::getCustomerId, keyWords)
                .select(Customer::getCustomerId);
        Customer one = this.getOne(customerLambdaQueryWrapper, false);
        if (one == null) {
            return null;
        } else {
            return this.getCustomerInfoById(one.getCustomerId());
        }
    }

    @Override
    public CustomerInfo getCustomerInfoById(Long customerId) {

        // 查询缓存中有没有用户信息
        String customerIdKey = String.valueOf(customerId);
        CustomerInfo customerInfo = customerInfoCacheOperatorApi.get(customerIdKey);
        if (customerInfo != null) {
            return customerInfo;
        }

        // 获取C端用户详情
        Customer customer = this.getById(customerId);
        if (customer == null) {
            return null;
        }

        CustomerInfo result = new CustomerInfo();
        BeanUtil.copyProperties(customer, result);

        // 获取头像的url
        String fileAuthUrl = fileOperatorApi.getFileUnAuthUrl(
                CustomerConfigExpander.getCustomerBucket(),
                customer.getAvatarObjectName());
        result.setAvatarObjectUrl(fileAuthUrl);

        // 更新用户会员信息
        if (result.getMemberExpireTime() == null) {
            result.setMemberFlag(false);
        } else {
            if (DateUtil.compare(result.getMemberExpireTime(), new Date()) < 0) {
                result.setMemberFlag(false);
            } else {
                result.setMemberFlag(true);
            }
        }

        // 放入缓存用户信息
        customerInfoCacheOperatorApi.put(customerIdKey, result, CustomerConfigExpander.getCustomerCacheExpiredSeconds());

        return result;
    }

    @Override
    public CustomerInfo getCustomerInfoBySecretKey(String secretKey) {

        if (StrUtil.isEmpty(secretKey)) {
            return null;
        }

        // 先通过secretKey获取用户id
        LambdaQueryWrapper<Customer> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Customer::getSecretKey, secretKey);
        wrapper.select(Customer::getCustomerId);
        Customer customer = this.getOne(wrapper, false);
        if (customer == null) {
            return null;
        }

        // 再通过用户id，获取用户的信息
        return this.getCustomerInfoById(customer.getCustomerId());
    }

    @Override
    public void updateMemberExpiryDate(Long customerId, Date expiryDate) {
        LambdaUpdateWrapper<Customer> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(Customer::getMemberExpireTime, expiryDate);
        wrapper.eq(Customer::getCustomerId, customerId);
        this.update(wrapper);

        // 清除缓存中的用户信息
        customerInfoCacheOperatorApi.remove(String.valueOf(customerId));
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

    /**
     * 校验是否存在重复的账号和邮箱
     *
     * @author fengshuonan
     * @date 2021/6/7 21:43
     */
    private void validateRepeat(CustomerRequest customerRequest) {

        LambdaQueryWrapper<Customer> accountWrapper = new LambdaQueryWrapper<>();
        accountWrapper.eq(Customer::getAccount, customerRequest.getAccount());
        int count = this.count(accountWrapper);
        if (count > 0) {
            throw new CustomerException(CustomerExceptionEnum.ACCOUNT_REPEAT);
        }

        LambdaQueryWrapper<Customer> emailWrapper = new LambdaQueryWrapper<>();
        emailWrapper.eq(Customer::getEmail, customerRequest.getEmail());
        int emailCount = this.count(emailWrapper);
        if (emailCount > 0) {
            throw new CustomerException(CustomerExceptionEnum.EMAIL_REPEAT);
        }
    }

    /**
     * 验证码是否正确
     *
     * @author fengshuonan
     * @date 2021/7/6 15:07
     */
    private void validateCaptcha(String verKey, String verCode) {

        // 如果开启了验证码校验，则验证当前请求的验证码是否正确
        if (SecurityConfigExpander.getCaptchaOpen()) {
            if (StrUtil.isEmpty(verKey) || StrUtil.isEmpty(verCode)) {
                throw new AuthException(ValidatorExceptionEnum.CAPTCHA_EMPTY);
            }
            if (!imageCaptchaApi.validateCaptcha(verKey, verCode)) {
                throw new AuthException(ValidatorExceptionEnum.CAPTCHA_ERROR);
            }
            return;
        }

        // 如果开启了拖拽验证码
        if (SecurityConfigExpander.getDragCaptchaOpen()) {
            if (StrUtil.isEmpty(verKey) || StrUtil.isEmpty(verCode)) {
                throw new AuthException(ValidatorExceptionEnum.CAPTCHA_EMPTY);
            }
            if (!dragCaptchaApi.validateCaptcha(verKey, Convert.toInt(verCode))) {
                throw new AuthException(ValidatorExceptionEnum.DRAG_CAPTCHA_ERROR);
            }
        }
    }

}
