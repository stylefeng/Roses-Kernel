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
    @PostResource(name = "注册C端用户", path = "/customer/reg", requiredPermission = false, requiredLogin = false)
    public ResponseData reg(@RequestBody @Validated(CustomerRequest.reg.class) CustomerRequest customerRequest) {
        customerService.reg(customerRequest);
        return new SuccessResponseData();
    }

    /**
     * 激活C端用户
     *
     * @author fengshuonan
     * @date 2021/6/7 16:03
     */
    @GetResource(name = "激活C端用户", path = "/customer/active", requiredPermission = false, requiredLogin = false)
    public ResponseData active(@Validated(CustomerRequest.active.class) CustomerRequest customerRequest) {
        customerService.active(customerRequest);
        return new SuccessResponseData();
    }

    /**
     * C端用户登录
     *
     * @author fengshuonan
     * @date 2021/06/07 11:40
     */
    @PostResource(name = "C端用户登录", path = "/customer/login", requiredPermission = false, requiredLogin = false)
    public ResponseData login(@RequestBody @Validated LoginRequest loginRequest) {
        LoginResponse loginResponse = customerService.login(loginRequest);
        return new SuccessResponseData(loginResponse);
    }

    /**
     * 添加
     *
     * @author fengshuonan
     * @date 2021/06/07 11:40
     */
    @PostResource(name = "添加", path = "/customer/add")
    public ResponseData add(@RequestBody @Validated(CustomerRequest.add.class) CustomerRequest customerRequest) {
        customerService.add(customerRequest);
        return new SuccessResponseData();
    }

    /**
     * 删除
     *
     * @author fengshuonan
     * @date 2021/06/07 11:40
     */
    @PostResource(name = "删除", path = "/customer/delete")
    public ResponseData delete(@RequestBody @Validated(CustomerRequest.delete.class) CustomerRequest customerRequest) {
        customerService.del(customerRequest);
        return new SuccessResponseData();
    }

    /**
     * 编辑
     *
     * @author fengshuonan
     * @date 2021/06/07 11:40
     */
    @PostResource(name = "编辑", path = "/customer/edit")
    public ResponseData edit(@RequestBody @Validated(CustomerRequest.edit.class) CustomerRequest customerRequest) {
        customerService.edit(customerRequest);
        return new SuccessResponseData();
    }

    /**
     * 查看详情
     *
     * @author fengshuonan
     * @date 2021/06/07 11:40
     */
    @GetResource(name = "查看详情", path = "/customer/detail")
    public ResponseData detail(@Validated(CustomerRequest.detail.class) CustomerRequest customerRequest) {
        return new SuccessResponseData(customerService.detail(customerRequest));
    }

    /**
     * 获取列表
     *
     * @author fengshuonan
     * @date 2021/06/07 11:40
     */
    @GetResource(name = "获取列表", path = "/customer/list")
    public ResponseData list(CustomerRequest customerRequest) {
        return new SuccessResponseData(customerService.findList(customerRequest));
    }

    /**
     * 获取列表（带分页）
     *
     * @author fengshuonan
     * @date 2021/06/07 11:40
     */
    @GetResource(name = "分页查询", path = "/customer/page")
    public ResponseData page(CustomerRequest customerRequest) {
        return new SuccessResponseData(customerService.findPage(customerRequest));
    }

}