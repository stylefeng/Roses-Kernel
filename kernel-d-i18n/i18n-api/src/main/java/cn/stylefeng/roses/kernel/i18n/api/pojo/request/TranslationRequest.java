package cn.stylefeng.roses.kernel.i18n.api.pojo.request;

import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
    @NotNull(message = "tranId不能为空", groups = {edit.class, detail.class, delete.class})
    private Long tranId;

    /**
     * 编码
     */
    @NotBlank(message = "tranCode不能为空", groups = {add.class, edit.class})
    private String tranCode;

    /**
     * 多语言条例名称
     */
    @NotBlank(message = "tranName不能为空", groups = {add.class, edit.class})
    private String tranName;

    /**
     * 语种字典
     */
    @NotBlank(message = "tranLanguageCode不能为空", groups = {add.class, edit.class})
    private String tranLanguageCode;

    /**
     * 翻译的值
     */
    @NotBlank(message = "tranValue不能为空", groups = {add.class, edit.class})
    private String tranValue;

}
