package cn.stylefeng.roses.kernel.expand.modular.api.enums;

import lombok.Getter;

/**
 * 字段类型枚举
 *
 * @author fengshuonan
 * @date 2022/4/1 10:34
 */
@Getter
public enum FieldTypeEnum {

    /**
     * 字符串
     */
    STR(1),

    /**
     * 数字
     */
    NUM(2),

    /**
     * 字典格式
     */
    DICT(3);

    private final Integer code;

    FieldTypeEnum(Integer code) {
        this.code = code;
    }

}
