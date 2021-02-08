package cn.stylefeng.roses.kernel.rule.enums;

import lombok.Getter;

/**
 * 公共状态，一般用来表示开启和关闭
 *
 * @author fengshuonan
 * @date 2020/10/14 21:31
 */
@Getter
public enum StatusEnum {

    /**
     * 启用
     */
    ENABLE(1, "启用"),

    /**
     * 禁用
     */
    DISABLE(2, "禁用");

    private final Integer code;

    private final String message;

    StatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 根据code获取枚举
     *
     * @author fengshuonan
     * @date 2020/10/29 18:59
     */
    public static StatusEnum codeToEnum(Integer code) {
        if (null != code) {
            for (StatusEnum e : StatusEnum.values()) {
                if (e.getCode().equals(code)) {
                    return e;
                }
            }
        }
        return null;
    }

}
