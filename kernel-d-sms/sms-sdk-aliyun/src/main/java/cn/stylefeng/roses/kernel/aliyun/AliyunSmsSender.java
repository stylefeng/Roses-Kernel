package cn.stylefeng.roses.kernel.aliyun;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.aliyun.enums.AliyunSmsResultEnum;
import cn.stylefeng.roses.kernel.aliyun.msign.MultiSignManager;
import cn.stylefeng.roses.kernel.sms.api.SmsSenderApi;
import cn.stylefeng.roses.kernel.sms.api.exception.SmsException;
import cn.stylefeng.roses.kernel.sms.api.exception.enums.SmsExceptionEnum;
import cn.stylefeng.roses.kernel.sms.api.pojo.AliyunSmsProperties;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import static cn.stylefeng.roses.kernel.sms.api.exception.enums.SmsExceptionEnum.SEND_SMS_PARAM_NULL;

/**
 * 阿里云短信发送服务
 *
 * @author fengshuonan
 * @date 2020/10/26 17:12
 */
@Slf4j
public class AliyunSmsSender implements SmsSenderApi {

    private final MultiSignManager multiSignManager;

    private final AliyunSmsProperties aliyunSmsProperties;

    public AliyunSmsSender(MultiSignManager multiSignManager, AliyunSmsProperties aliyunSmsProperties) {
        this.multiSignManager = multiSignManager;
        this.aliyunSmsProperties = aliyunSmsProperties;
    }

    @Override
    public void sendSms(String phone, String templateCode, Map<String, Object> params) {

        log.info("开始发送阿里云短信，手机号是：" + phone + ",模板号是：" + templateCode + ",参数是：" + params);

        // 检验参数是否合法
        assertSendSmsParams(phone, templateCode, params, aliyunSmsProperties);

        // 初始化client profile
        IAcsClient iAcsClient = initClient();

        // 组装请求对象
        JSONObject smsRes = createSmsRequest(phone, templateCode, params, iAcsClient);

        // 如果返回ok则发送成功
        if (!AliyunSmsResultEnum.OK.getCode().equals(smsRes.getString("Code"))) {

            // 返回其他状态码根据情况抛出业务异常
            String code = AliyunSmsResultEnum.SYSTEM_ERROR.getCode();
            String errorMessage = AliyunSmsResultEnum.SYSTEM_ERROR.getMessage();
            for (AliyunSmsResultEnum smsExceptionEnum : AliyunSmsResultEnum.values()) {
                if (smsExceptionEnum.getCode().equals(smsRes.getString("Code"))) {
                    code = smsExceptionEnum.getCode();
                    errorMessage = smsExceptionEnum.getMessage();
                }
            }

            // 组装错误信息
            throw new SmsException(SmsExceptionEnum.ALIYUN_SMS_ERROR, code, errorMessage);
        }
    }

    /**
     * 初始化短信发送的客户端
     *
     * @author fengshuonan
     * @date 2018/7/6 下午3:57
     */
    private IAcsClient initClient() {
        final String accessKeyId = aliyunSmsProperties.getAccessKeyId();
        final String accessKeySecret = aliyunSmsProperties.getAccessKeySecret();

        // 创建DefaultAcsClient实例并初始化
        DefaultProfile profile = DefaultProfile.getProfile(aliyunSmsProperties.getRegionId(), accessKeyId, accessKeySecret);
        return new DefaultAcsClient(profile);
    }

    /**
     * 组装请求对象
     *
     * @author fengshuonan
     * @date 2018/7/6 下午3:00
     */
    private JSONObject createSmsRequest(String phoneNumber, String templateCode, Map<String, Object> params, IAcsClient acsClient) {
        CommonRequest request = new CommonRequest();
        request.setSysDomain(aliyunSmsProperties.getSmsDomain());
        request.setSysVersion(aliyunSmsProperties.getSmsVersion());
        request.setSysAction(aliyunSmsProperties.getSmsSendAction());

        // 接收短信的手机号码
        request.putQueryParameter("PhoneNumbers", phoneNumber);

        // 短信签名名称。请在控制台签名管理页面签名名称一列查看（必须是已添加、并通过审核的短信签名）。
        request.putQueryParameter("SignName", this.getSmsSign(phoneNumber));

        // 短信模板ID
        request.putQueryParameter("TemplateCode", templateCode);

        // 短信模板变量对应的实际值，JSON格式。
        request.putQueryParameter("TemplateParam", JSON.toJSONString(params));

        //请求失败这里会抛ClientException异常
        CommonResponse commonResponse;
        try {
            commonResponse = acsClient.getCommonResponse(request);
            String data = commonResponse.getData();
            String jsonResult = data.replaceAll("'\'", "");
            log.info("获取到发送短信的响应结果！{}", jsonResult);
            return JSON.parseObject(jsonResult);
        } catch (ClientException e) {
            log.error("初始化阿里云sms异常！可能是accessKey和secret错误！", e);

            // 组装错误信息
            throw new SmsException(SmsExceptionEnum.ALIYUN_SMS_KEY_ERROR, aliyunSmsProperties.getAccessKeyId());
        }
    }

    /**
     * 校验发送短信的参数是否正确
     *
     * @author fengshuonan
     * @date 2018/7/6 下午3:19
     */
    private void assertSendSmsParams(String phoneNumber, String templateCode, Map<String, Object> params,
                                     AliyunSmsProperties aliyunSmsProperties) {

        if (StrUtil.isBlank(phoneNumber)) {
            throw new SmsException(SEND_SMS_PARAM_NULL, "电话号码");
        }

        if (StrUtil.isBlank(templateCode)) {
            throw new SmsException(SEND_SMS_PARAM_NULL, "模板号templateCode");
        }

        if (ObjectUtil.isEmpty(params)) {
            throw new SmsException(SEND_SMS_PARAM_NULL, "模板参数");
        }

        if (ObjectUtil.isEmpty(aliyunSmsProperties)) {
            throw new SmsException(SEND_SMS_PARAM_NULL, "短信配置properties");
        }
    }

    /**
     * 获取sms发送的sign标识，参数phone是发送的手机号码
     *
     * @author stylefeng
     * @date 2018/8/13 21:23
     */
    private String getSmsSign(String phone) {
        String signName = aliyunSmsProperties.getSignName();

        // 如果是单个签名就用一个签名发
        if (!signName.contains(",")) {
            log.info("发送短信，签名为：" + signName + ",电话为：" + phone);
            return signName;
        } else {
            return multiSignManager.getSign(phone, signName);
        }
    }

}
