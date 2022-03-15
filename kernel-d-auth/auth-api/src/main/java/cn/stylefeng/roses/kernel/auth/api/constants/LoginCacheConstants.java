package cn.stylefeng.roses.kernel.auth.api.constants;

/**
 * 登录前缀相关的常量
 *
 * @author xixiaowei
 * @date 2022/1/22 17:37
 */
public interface LoginCacheConstants {

    /**
     * 登录失败最大次数
     */
    Integer MAX_ERROR_LOGIN_COUNT = 5;

    /**
     * 登录冻结缓存前缀
     */
    String LOGIN_ERROR_CACHE_PREFIX = "LOGIN:COUNT:";

    /**
     * 冻结时间：30分钟
     */
    Long LOGIN_CACHE_TIMEOUT_SECONDS = 1800L;

}
