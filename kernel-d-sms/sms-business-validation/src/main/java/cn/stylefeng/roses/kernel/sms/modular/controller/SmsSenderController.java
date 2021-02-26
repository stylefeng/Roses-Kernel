package cn.stylefeng.roses.kernel.sms.modular.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.RandomUtil;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.PostResource;
import cn.stylefeng.roses.kernel.sms.modular.param.SysSmsInfoParam;
import cn.stylefeng.roses.kernel.sms.modular.param.SysSmsSendParam;
import cn.stylefeng.roses.kernel.sms.modular.param.SysSmsVerifyParam;
import cn.stylefeng.roses.kernel.sms.modular.service.SysSmsInfoService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * 短信发送控制器
 *
 * @author fengshuonan
 * @date 2020/10/26 18:34
 */
@RestController
@ApiResource(name = "短信发送控制器")
public class SmsSenderController {

    @Resource
    private SysSmsInfoService sysSmsInfoService;

    /**
     * 发送记录查询
     *
     * @author fengshuonan
     * @date 2020/10/26 18:34
     */
    @GetResource(name = "发送记录查询", path = "/sms/page")
    public ResponseData page(SysSmsInfoParam sysSmsInfoParam) {
        return new SuccessResponseData(sysSmsInfoService.page(sysSmsInfoParam));
    }

    /**
     * 发送验证码短信
     *
     * @author fengshuonan
     * @date 2020/10/26 18:34
     */
    @PostResource(name = "发送验证码短信", path = "/sms/sendLoginMessage", requiredLogin = false, requiredPermission = false)
    public ResponseData sendMessage(@RequestBody @Validated SysSmsSendParam sysSmsSendParam) {

        // 清空params参数
        sysSmsSendParam.setParams(null);

        // 设置模板中的参数
        HashMap<String, Object> paramMap = CollectionUtil.newHashMap();
        paramMap.put("code", RandomUtil.randomNumbers(6));
        sysSmsSendParam.setParams(paramMap);

        return new SuccessResponseData(sysSmsInfoService.sendShortMessage(sysSmsSendParam));
    }

    /**
     * 验证短信验证码
     *
     * @author fengshuonan
     * @date 2020/10/26 18:35
     */
    @PostResource(name = "验证短信验证码", path = "/sms/validateMessage", requiredLogin = false, requiredPermission = false)
    public ResponseData validateMessage(@RequestBody @Validated SysSmsVerifyParam sysSmsVerifyParam) {
        sysSmsInfoService.validateSmsInfo(sysSmsVerifyParam);
        return new SuccessResponseData("短信验证成功");
    }

}
