package cn.stylefeng.roses.kernel.dict.api.enums;

import lombok.Getter;

/**
 * 字典类型的分类枚举
 *
 * @author fengshuonan
 * @date 2020/10/30 10:19
 */
@Getter
public enum DictTypeClassEnum {

    /**
     * 字典类型为业务类型
     */
    BUSINESS_TYPE(1),

    /**
     * 字典类型为系统类型
     */
    SYSTEM_TYPE(2);

    private final Integer code;

    DictTypeClassEnum(Integer code) {
        this.code = code;
    }

}
