package cn.stylefeng.roses.kernel.system.enums;

import lombok.Getter;

/**
 * 菜单链接打开方式
 *
 * @author fengshuonan
 * @date 2020/11/23 21:30
 */
@Getter
public enum LinkOpenTypeEnum {

    /**
     * 内部链接（内置iframe页面打开链接）
     */
    COMPONENT(1, "内链"),

    /**
     * 外部链接方式打开链接，新标签页打开
     */
    INNER(2, "外链");

    private final Integer code;

    private final String message;

    LinkOpenTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
