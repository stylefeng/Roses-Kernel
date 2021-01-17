package cn.stylefeng.roses.kernel.rule.enums;

import lombok.Getter;

/**
 * 树节点类型的枚举
 *
 * @author liuhanqing
 * @date 2021/1/15 13:36
 */
@Getter
public enum TreeNodeEnum {

    /**
     * 机构
     */
    ORG("org", "机构"),

    /**
     * 用户
     */
    USER("user", "用户");

    private final String code;

    private final String name;

    TreeNodeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * 根据code获取枚举
     *
     * @author liuhanqing
     * @date 2021/1/15 13:36
     */
    public static TreeNodeEnum codeToEnum(String code) {
        if (null != code) {
            for (TreeNodeEnum e : TreeNodeEnum.values()) {
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
     * @author liuhanqing
     * @date 2021/1/15 13:36
     */
    public static String codeToName(String code) {
        if (null != code) {
            for (TreeNodeEnum e : TreeNodeEnum.values()) {
                if (e.getCode().equals(code)) {
                    return e.getName();
                }
            }
        }
        return "未知";
    }

}
