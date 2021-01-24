package cn.stylefeng.roses.kernel.i18n.api.pojo.request;

import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 多语言请求信息
 *
 * @author stylefeng
 * @since 2019-10-17
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TranslationRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long tranId;

    /**
     * 编码
     */
    private String tranCode;

    /**
     * 多语言条例名称
     */
    private String tranName;

    /**
     * 1:中文  2:英语
     */
    private Integer languages;

    /**
     * 翻译的值
     */
    private String tranValue;

}
