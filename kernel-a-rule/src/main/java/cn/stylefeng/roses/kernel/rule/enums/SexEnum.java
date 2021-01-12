package cn.stylefeng.roses.kernel.rule.enums;

import lombok.Getter;

/**
 * 性别的枚举
 *
 * @author fengshuonan
 * @date 2020/10/17 10:01
 */
@Getter
public enum SexEnum {

    /**
     * 男
     */
    M("M", "男"),

    /**
     * 女
     */
    F("F", "女");

    private final String code;

    private final String message;

    SexEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 根据code获取枚举
     *
     * @author fengshuonan
     * @date 2020/10/29 18:59
     */
    public static SexEnum codeToEnum(String code) {
        if (null != code) {
            for (SexEnum e : SexEnum.values()) {
                if (e.getCode().equals(code)) {
                    return e;
                }
            }
        }
        return null;
    }

    /**
     * 编码转化成中文含义
     *
     * @author fengshuonan
     * @date 2021/1/11 22:34
     */
    public static String codeToMessage(String code) {
        if (null != code) {
            for (SexEnum e : SexEnum.values()) {
                if (e.getCode().equals(code)) {
                    return e.getMessage();
                }
            }
        }
        return "未知";
    }

}
