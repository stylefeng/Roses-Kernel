package cn.stylefeng.roses.kernel.auth.api.enums;

import cn.stylefeng.roses.kernel.auth.api.exception.AuthException;
import lombok.Getter;

import static cn.stylefeng.roses.kernel.auth.api.exception.enums.AuthExceptionEnum.DATA_SCOPE_ERROR;

/**
 * 数据范围类型枚举，数据范围的值越小，数据权限越小
 *
 * @author fengshuonan
 * @date 2020/11/5 15:22
 */
@Getter
public enum DataScopeTypeEnum {

    /**
     * 仅本人数据
     */
    SELF(10, "仅本人数据"),

    /**
     * 本部门数据
     */
    DEPT(20, "本部门数据"),

    /**
     * 本部门及以下数据
     */
    DEPT_WITH_CHILD(30, "本部门及以下数据"),

    /**
     * 指定部门数据
     */
    DEFINE(40, "指定部门数据"),

    /**
     * 全部数据
     */
    ALL(50, "全部数据");

    private final Integer code;

    private final String message;

    DataScopeTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 根据code获取枚举
     *
     * @author fengshuonan
     * @date 2020/10/29 18:59
     */
    public static DataScopeTypeEnum codeToEnum(Integer code) {
        if (null != code) {
            for (DataScopeTypeEnum dataScopeTypeEnum : DataScopeTypeEnum.values()) {
                if (dataScopeTypeEnum.getCode().equals(code)) {
                    return dataScopeTypeEnum;
                }
            }
        }
        throw new AuthException(DATA_SCOPE_ERROR, code);
    }

}
