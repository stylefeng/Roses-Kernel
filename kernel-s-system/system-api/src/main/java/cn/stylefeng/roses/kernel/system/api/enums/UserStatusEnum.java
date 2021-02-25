package cn.stylefeng.roses.kernel.system.api.enums;

import cn.stylefeng.roses.kernel.system.api.exception.enums.user.SysUserExceptionEnum;
import cn.stylefeng.roses.kernel.system.api.exception.SystemModularException;
import lombok.Getter;

/**
 * 用户状态的枚举
 *
 * @author fengshuonan
 * @date 2020/10/20 18:19
 */
@Getter
public enum UserStatusEnum {

    /**
     * 启用
     */
    ENABLE(1, "启用"),

    /**
     * 禁用
     */
    DISABLE(2, "禁用"),

    /**
     * 冻结
     */
    FREEZE(3, "冻结");

    private final Integer code;

    private final String message;

    UserStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * code转化为enum
     *
     * @author fengshuonan
     * @date 2020/10/21 9:29
     */
    public static UserStatusEnum toEnum(Integer code) {
        for (UserStatusEnum userStatusEnum : UserStatusEnum.values()) {
            if (userStatusEnum.getCode().equals(code)) {
                return userStatusEnum;
            }
        }
        return null;
    }

    /**
     * 获取code对应的message
     *
     * @author fengshuonan
     * @date 2020/10/21 9:29
     */
    public static String getCodeMessage(Integer code) {
        UserStatusEnum userStatusEnum = toEnum(code);
        if (userStatusEnum != null) {
            return userStatusEnum.getMessage();
        } else {
            return "";
        }
    }

    /**
     * 检查请求参数的状态是否正确
     *
     * @author stylefeng
     * @date 2020/4/30 22:43
     */
    public static void validateUserStatus(Integer code) {
        if (code == null) {
            throw new SystemModularException(SysUserExceptionEnum.REQUEST_USER_STATUS__EMPTY);
        }
        if (ENABLE.getCode().equals(code) || DISABLE.getCode().equals(code) || FREEZE.getCode().equals(code)) {
            return;
        }
        throw new SystemModularException(SysUserExceptionEnum.REQUEST_USER_STATUS_ERROR, code);
    }

}
