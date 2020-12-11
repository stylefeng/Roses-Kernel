package cn.stylefeng.roses.kernel.auth.api;

import cn.stylefeng.roses.kernel.auth.api.exception.AuthException;
import cn.stylefeng.roses.kernel.auth.api.pojo.auth.LoginRequest;
import cn.stylefeng.roses.kernel.auth.api.pojo.auth.LoginResponse;

/**
 * 认证服务的接口，包括基本的登录退出操作和校验token等操作
 *
 * @author fengshuonan
 * @date 2020/10/26 14:41
 */
public interface AuthServiceApi {

    /**
     * 常规登录操作
     *
     * @param loginRequest 登录的请求
     * @return token 一般为jwt token
     * @author fengshuonan
     * @date 2020/10/26 14:41
     */
    LoginResponse login(LoginRequest loginRequest);

    /**
     * 登录（直接用账号登录），一般用在第三方登录
     *
     * @param username 账号
     * @author fengshuonan
     * @date 2020/10/26 14:40
     */
    LoginResponse loginWithUserName(String username);

    /**
     * 当前登录人退出登录
     *
     * @author fengshuonan
     * @date 2020/10/19 14:16
     */
    void logout();

    /**
     * 移除某个token，也就是退出某个用户
     *
     * @param token 某个用户的登录token
     * @author fengshuonan
     * @date 2020/10/19 14:16
     */
    void logoutWithToken(String token);

    /**
     * 校验token
     *
     * @param token 某个用户的登录token
     * @throws AuthException 认证异常，如果token错误或过期，会有相关的异常抛出
     * @author fengshuonan
     * @date 2020/10/19 14:16
     */
    void validateToken(String token) throws AuthException;

    /**
     * 获取token是否正确
     *
     * @param token 某个用户的登录token
     * @return true-token正确，false-token错误
     * @author fengshuonan
     * @date 2020/10/19 14:16
     */
    boolean getTokenFlag(String token);

    /**
     * 校验用户访问的url是否认证通过
     *
     * @param token      用户登陆的token
     * @param requestUrl 被校验的url
     * @author fengshuonan
     * @date 2020/10/22 16:03
     */
    void checkAuth(String token, String requestUrl);

}
