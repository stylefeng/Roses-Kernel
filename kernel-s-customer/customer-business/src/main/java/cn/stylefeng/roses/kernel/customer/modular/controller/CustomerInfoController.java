package cn.stylefeng.roses.kernel.customer.modular.controller;

import cn.stylefeng.roses.kernel.customer.api.pojo.CustomerInfo;
import cn.stylefeng.roses.kernel.customer.api.pojo.CustomerInfoRequest;
import cn.stylefeng.roses.kernel.customer.modular.request.CustomerRequest;
import cn.stylefeng.roses.kernel.customer.modular.service.CustomerService;
import cn.stylefeng.roses.kernel.rule.annotation.BusinessLog;
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
 * C端用户个人信息修改
 *
 * @author fengshuonan
 * @date 2021/6/18 16:28
 */
@RestController
@ApiResource(name = "C端用户个人信息修改")
public class CustomerInfoController {

    @Resource
    private CustomerService customerService;

    /**
     * 获取个人信息
     *
     * @author fengshuonan
     * @date 2021/6/18 16:29
     */
    @GetResource(name = "获取个人信息", path = "/customerInfo/getPersonInfo", requiredPermission = false)
    public ResponseData<CustomerInfo> getPersonInfo(@Validated(CustomerInfoRequest.detail.class) CustomerRequest customerRequest) {
        CustomerInfo customerInfo = customerService.getCustomerInfoById(customerRequest.getCustomerId());
        return new SuccessResponseData<>(customerInfo);
    }

    /**
     * 修改个人密码
     *
     * @author fengshuonan
     * @date 2021/6/18 16:29
     */
    @PostResource(name = "修改个人密码", path = "/customerInfo/updatePassword", requiredPermission = false)
    @BusinessLog
    public ResponseData<?> updatePassword(@RequestBody @Validated(CustomerInfoRequest.changePassword.class) CustomerInfoRequest customerInfoRequest) {
        this.customerService.updatePassword(customerInfoRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 修改个人头像
     *
     * @author fengshuonan
     * @date 2021/6/18 16:29
     */
    @PostResource(name = "修改个人头像", path = "/customerInfo/updateAvatar", requiredPermission = false)
    @BusinessLog
    public ResponseData<?> updateAvatar(@RequestBody @Validated(CustomerInfoRequest.changeAvatar.class) CustomerInfoRequest customerInfoRequest) {
        this.customerService.updateAvatar(customerInfoRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 重置个人秘钥
     *
     * @author fengshuonan
     * @date 2021/7/20 10:44
     */
    @PostResource(name = "重置个人秘钥", path = "/customerInfo/resetPersonalSecret", requiredPermission = false)
    @BusinessLog
    public ResponseData<String> resetPersonalSecret() {
        String secret = customerService.updateSecret();
        return new SuccessResponseData<>(secret);
    }

}
