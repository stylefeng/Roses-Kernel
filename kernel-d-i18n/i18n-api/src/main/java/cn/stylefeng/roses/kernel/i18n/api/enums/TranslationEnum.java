package cn.stylefeng.roses.kernel.i18n.api.enums;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 翻译的语种
 *
 * @author fengshuonan
 * @date 2021/1/24 18:57
 */
@Getter
public enum TranslationEnum {

    /**
     * 中文
     */
    CHINESE(1, "中文"),

    /**
     * 英文
     */
    ENGLISH(2, "English");

    /**
     * 语种编码
     */
    private final Integer code;

    /**
     * 语种的中文描述
     */
    private final String description;

    TranslationEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 获取所有的type（返回code编码集合）
     *
     * @author fengshuonan
     * @date 2019/10/18 10:13
     */
    public static List<String> types() {
        ArrayList<String> integers = new ArrayList<>();
        for (TranslationEnum value : TranslationEnum.values()) {
            integers.add(value.name());
        }
        return integers;
    }

    /**
     * 通过code值获取枚举
     *
     * @author fengshuonan
     * @date 2019/10/18 10:33
     */
    public static TranslationEnum valueOf(Integer value) {
        if (value != null) {
            for (TranslationEnum translationLanguages : TranslationEnum.values()) {
                if (translationLanguages.getCode().equals(value)) {
                    return translationLanguages;
                }
            }
        }
        return null;
    }

}
