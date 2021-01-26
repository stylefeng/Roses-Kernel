package cn.stylefeng.roses.kernel.i18n.api.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * 多语言翻译信息
 *
 * @author stylefeng
 * @since 2019-10-17
 */
@Data
public class TranslationDict implements Serializable {

    /**
     * 翻译项的编码，例如：FIELD_ACCOUNT
     */
    private String tranCode;

    /**
     * 多语言翻译项的说明，例如：针对账号输入框的
     */
    private String tranName;

    /**
     * 多语言语种的编码，值是字典的值，字典类型的编码是 languages
     */
    private String translationLanguages;

    /**
     * 翻译的值，例如：账号
     */
    private String tranValue;

}
