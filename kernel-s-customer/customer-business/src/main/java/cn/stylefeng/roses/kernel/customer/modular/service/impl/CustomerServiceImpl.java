package cn.stylefeng.roses.kernel.customer.modular.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kernel.customer.modular.entity.Customer;
import cn.stylefeng.roses.kernel.customer.modular.mapper.CustomerMapper;
import cn.stylefeng.roses.kernel.customer.modular.request.CustomerRequest;
import cn.stylefeng.roses.kernel.customer.modular.service.CustomerService;
import cn.stylefeng.roses.kernel.db.api.factory.PageFactory;
import cn.stylefeng.roses.kernel.db.api.factory.PageResultFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.rule.exception.base.ServiceException;
import cn.stylefeng.roses.kernel.rule.exception.enums.defaults.DefaultBusinessExceptionEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * C端用户表业务实现层
 *
 * @author fengshuonan
 * @date 2021/06/07 11:40
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {

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