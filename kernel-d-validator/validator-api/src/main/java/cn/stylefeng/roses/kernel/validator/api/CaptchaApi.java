package cn.stylefeng.roses.kernel.validator.api;

import cn.stylefeng.roses.kernel.validator.api.pojo.EasyCaptcha;

/**
 * 图形验证码Api
 * <p>
 * 开启用户登录图形验证码后获取图形验证码
 *
 * @author chenjinlong
 * @date 2021/1/15 13:46
 */
public interface CaptchaApi {

    /**
     * 生成图形验证码
     *
     * @author chenjinlong
     * @date 2021/1/15 12:38
     */
    EasyCaptcha captcha();

    /**
     * 校验图形验证码
     *
     * @param verKey  缓存key值
     * @param verCode 验证码
     * @return true-验证码正确，false-验证码错误
     * @author chenjinlong
     * @date 2021/1/15 12:38
     */
    boolean validateCaptcha(String verKey, String verCode);

    /**
     * 根据key值获取验证码
     *
     * @param verKey 缓存key值
     * @author chenjinlong
     * @date 2021/1/15 12:40
     */
    String getVerCode(String verKey);

}
