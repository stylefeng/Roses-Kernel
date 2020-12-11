package cn.stylefeng.roses.kernel.auth.api.constants;

/**
 * auth，鉴权模块的常量
 *
 * @author fengshuonan
 * @date 2020/10/16 11:05
 */
public interface AuthConstants {

    /**
     * auth模块的名称
     */
    String AUTH_MODULE_NAME = "kernel-d-auth";

    /**
     * 异常枚举的步进值
     */
    String AUTH_EXCEPTION_STEP_CODE = "03";

    /**
     * 登录用户的缓存前缀
     */
    String LOGGED_TOKEN_PREFIX = "LOGGED_TOKEN_";

    /**
     * 登录用户id的缓存前缀
     */
    String LOGGED_USERID_PREFIX = "LOGGED_USERID_";

    /**
     * 默认http请求携带token的header名称
     */
    String DEFAULT_AUTH_HEADER_NAME = "Authorization";

    /**
     * 获取http请求携带token的param的名称
     */
    String DEFAULT_AUTH_PARAM_NAME = "token";

    /**
     * 默认密码
     */
    String DEFAULT_PASSWORD = "123456";

}
