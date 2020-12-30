package cn.stylefeng.roses.kernel.system.constants;

/**
 * 系统管理模块的常量
 *
 * @author fengshuonan
 * @date 2020/11/4 15:48
 */
public interface SystemConstants {

    /**
     * 系统管理模块的名称
     */
    String SYSTEM_MODULE_NAME = "kernel-s-system";

    /**
     * 异常枚举的步进值
     */
    String SYSTEM_EXCEPTION_STEP_CODE = "18";

    /**
     * 一级节点的父节点id
     */
    Long DEFAULT_PARENT_ID = -1L;

    /**
     * 虚拟的根节点的父级id
     */
    Long VIRTUAL_ROOT_PARENT_ID = -2L;

    /**
     * pids系列字段，每个id的左分隔符
     */
    String PID_LEFT_DIVIDE_SYMBOL = "[";

    /**
     * pids系列字段，每个id的右分隔符
     */
    String PID_RIGHT_DIVIDE_SYMBOL = "]";

    /**
     * 默认的系统版本号
     */
    String DEFAULT_SYSTEM_VERSION = "20210101";

    /**
     * 默认多租户的开关：关闭
     */
    Boolean DEFAULT_TENANT_OPEN = false;

    /**
     * 默认验证码的开关：关闭
     */
    Boolean DEFAULT_CAPTCHA_OPEN = false;

    /**
     * 默认的系统的名称
     */
    String DEFAULT_SYSTEM_NAME = "Guns快速开发平台";

}