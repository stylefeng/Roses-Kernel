package cn.stylefeng.roses.kernel.validator.pojo;

import lombok.Builder;
import lombok.Data;

/**
 * EasyCaptcha 图形验证码参数
 *
 * @author fengshuonan
 * @date 2020/8/17 21:43
 */
@Data
@Builder
public class EasyCaptcha {

    /**
     * 缓存Key
     */
    private String verKey;

    /**
     * Base64 图形验证码
     */
    private String verImage;
}
