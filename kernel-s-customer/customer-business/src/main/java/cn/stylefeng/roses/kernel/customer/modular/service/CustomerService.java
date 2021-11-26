package cn.stylefeng.roses.kernel.customer.modular.service;

import cn.stylefeng.roses.kernel.auth.api.pojo.auth.LoginRequest;
import cn.stylefeng.roses.kernel.auth.api.pojo.auth.LoginResponse;
import cn.stylefeng.roses.kernel.customer.api.CustomerApi;
import cn.stylefeng.roses.kernel.customer.api.pojo.CustomerInfoRequest;
import cn.stylefeng.roses.kernel.customer.modular.entity.Customer;
import cn.stylefeng.roses.kernel.customer.modular.request.CustomerRequest;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * C端用户表 服务类
 *
 * @author fengshuonan
 * @date 2021/06/07 11:40
 */
public interface CustomerService extends IService<Customer>, CustomerApi {

    /**
     * 注册用户
     *
     * @author fengshuonan
     * @date 2021/06/07 11:40
     */
    void reg(CustomerRequest customerRequest);

    /**
     * 激活用户
     *
     * @author fengshuonan
     * @date 2021/06/07 11:40
     */
    void active(CustomerRequest customerRequest);

    /**
     * C端用户登录
     *
     * @author fengshuonan
     * @date 2021/6/7 16:20
     */
    LoginResponse login(LoginRequest loginRequest);

    /**
     * 发送找回密码的邮件
     *
     * @author fengshuonan
     * @date 2021/6/7 22:11
     */
    void sendResetPwdEmail(CustomerRequest customerRequest);

    /**
     * 重置密码
     *
     * @author fengshuonan
     * @date 2021/6/7 22:13
     */
    void resetPassword(CustomerRequest customerRequest);

    /**
     * 新增
     *
     * @author fengshuonan
     * @date 2021/06/07 11:40
     */
    void add(CustomerRequest customerRequest);

    /**
     * 删除
     *
     * @author fengshuonan
     * @date 2021/06/07 11:40
     */
    void del(CustomerRequest customerRequest);

    /**
     * 编辑
     *
     * @author fengshuonan
     * @date 2021/06/07 11:40
     */
    void edit(CustomerRequest customerRequest);

    /**
     * 查询详情
     *
     * @author fengshuonan
     * @date 2021/06/07 11:40
     */
    Customer detail(CustomerRequest customerRequest);

    /**
     * 获取列表
     *
     * @author fengshuonan
     * @date 2021/06/07 11:40
     */
    List<Customer> findList(CustomerRequest customerRequest);

    /**
     * 获取列表（带分页）
     *
     * @author fengshuonan
     * @date 2021/06/07 11:40
     */
    PageResult<Customer> findPage(CustomerRequest customerRequest);

    /**
     * 更新密码
     *
     * @author fengshuonan
     * @date 2021/6/18 17:16
     */
    void updatePassword(CustomerInfoRequest customerInfoRequest);

    /**
     * 更新头像
     *
     * @author fengshuonan
     * @date 2021/6/18 17:16
     */
    void updateAvatar(CustomerInfoRequest customerInfoRequest);

    /**
     * 重置个人秘钥
     *
     * @return 用户秘钥
     * @author fengshuonan
     * @date 2021/7/20 10:44
     */
    String updateSecret();

}