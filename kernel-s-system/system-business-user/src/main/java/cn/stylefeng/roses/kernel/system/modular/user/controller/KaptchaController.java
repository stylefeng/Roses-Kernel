package cn.stylefeng.roses.kernel.system.modular.user.controller;


import cn.stylefeng.roses.kernel.resource.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.resource.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.validator.CaptchaApi;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 图形验证码
 *
 * @author chenjinlong
 * @date 2021/1/15 15:11
 */
@RestController
@ApiResource(name = "用户登录图形验证码")
public class KaptchaController {

    @Resource
    private CaptchaApi captchaApi;


    @GetResource(name = "获取图形验证码", path = "/kaptcha", requiredPermission = false, requiredLogin = false)
    public ResponseData kaptcha() {
        return new SuccessResponseData(captchaApi.captcha());
    }


}
