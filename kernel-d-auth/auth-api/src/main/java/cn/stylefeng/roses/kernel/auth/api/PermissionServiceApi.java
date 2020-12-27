package cn.stylefeng.roses.kernel.auth.api;

import cn.stylefeng.roses.kernel.auth.api.exception.AuthException;

/**
 * 权限相关的服务接口
 *
 * @author fengshuonan
 * @date 2020/10/19 14:24
 */
public interface PermissionServiceApi {

    /**
     * 校验当前用户是否有某个接口的权限
     * <p>
     * 只要权限校验不通过，则会抛出异常
     *
     * @param token      用户登陆的token
     * @param requestUrl 被校验的url
     * @throws AuthException 认证失败的异常信息
     * @author fengshuonan
     * @date 2020/10/19 14:50
     */
    void checkPermission(String token, String requestUrl) throws AuthException;

}
