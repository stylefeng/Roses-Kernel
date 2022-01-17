package cn.stylefeng.roses.kernel.customer.modular.controller;

import cn.stylefeng.roses.kernel.auth.api.pojo.auth.LoginRequest;
import cn.stylefeng.roses.kernel.auth.api.pojo.auth.LoginResponse;
import cn.stylefeng.roses.kernel.customer.modular.request.CustomerRequest;
import cn.stylefeng.roses.kernel.customer.modular.service.CustomerService;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.PostResource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * C端用户表控制器
 *
 * @author fengshuonan
 * @date 2021/06/07 11:40
 */
@RestController
@ApiResource(name = "C端用户表")
public class CustomerController {

    @Resource
    private CustomerService customerService;

    /**
     * 注册C端用户
     *
     * @author fengshuonan
     * @date 2021/06/07 11:40
     */
    @PostResource(name = "注册", path = "/customer/reg", requiredPermission = false, requiredLogin = false)
    public ResponseData<?> reg(@RequestBody @Validated(CustomerRequest.reg.class) CustomerRequest customerRequest) {
        customerService.reg(customerRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 激活C端用户
     *
     * @author fengshuonan
     * @date 2021/6/7 16:03
     */
    @GetResource(name = "激活用户", path = "/customer/active", requiredPermission = false, requiredLogin = false)
    public ResponseData<?> active(@Validated(CustomerRequest.active.class) CustomerRequest customerRequest) {
        customerService.active(customerRequest);
        return new SuccessResponseData<>();
    }

    /**
     * C端用户登录
     *
     * @author fengshuonan
     * @date 2021/06/07 11:40
     */
    @PostResource(name = "登录", path = "/customer/login", requiredPermission = false, requiredLogin = false)
    public ResponseData<LoginResponse> login(@RequestBody @Validated LoginRequest loginRequest) {
        LoginResponse loginResponse = customerService.login(loginRequest);
        return new SuccessResponseData<>(loginResponse);
    }

    /**
     * 找回密码-发送邮件
     *
     * @author fengshuonan
     * @date 2021/06/07 11:40
     */
    @PostResource(name = "找回密码-发送邮件", path = "/customer/sendResetPwdEmail", requiredPermission = false, requiredLogin = false)
    public ResponseData<?> sendResetPwdEmail(@RequestBody @Validated(CustomerRequest.sendResetPwdEmail.class) CustomerRequest customerRequest) {
        customerService.sendResetPwdEmail(customerRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 重置密码
     *
     * @author fengshuonan
     * @date 2021/06/07 11:40
     */
    @PostResource(name = "重置密码", path = "/customer/resetPassword", requiredPermission = false, requiredLogin = false)
    public ResponseData<?> resetPassword(@RequestBody @Validated(CustomerRequest.resetPassword.class) CustomerRequest customerRequest) {
        customerService.resetPassword(customerRequest);
        return new SuccessResponseData<>();
    }

}