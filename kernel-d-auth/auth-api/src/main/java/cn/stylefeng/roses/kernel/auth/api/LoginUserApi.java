package cn.stylefeng.roses.kernel.auth.api;

import cn.stylefeng.roses.kernel.auth.api.exception.AuthException;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.LoginUser;

/**
 * 当前登陆用户相关的一系列方法
 *
 * @author fengshuonan
 * @date 2020/10/17 10:27
 */
public interface LoginUserApi {

    /**
     * 获取当前登陆用户的token
     * <p>
     * 如果获取不到，返回null
     *
     * @return 当前用户的token或null
     * @author fengshuonan
     * @date 2020/10/17 11:05
     */
    String getToken();

    /**
     * 获取当前登陆用户
     * <p>
     * 如果获取不到当前登陆用户会抛出 AuthException
     *
     * @return 当前登陆用户信息
     * @throws AuthException 权限异常
     * @author fengshuonan
     * @date 2020/10/17 10:27
     */
    LoginUser getLoginUser() throws AuthException;

    /**
     * 获取当前登陆用户
     * <p>
     * 如果获取不到当前登陆用户返回null
     *
     * @return 当前登录用户信息
     * @author fengshuonan
     * @date 2020/10/17 11:00
     */
    LoginUser getLoginUserNullable();

    /**
     * 获取是否是超级管理员的标识
     *
     * @return true-是超级管理员，false-不是超级管理员
     * @author fengshuonan
     * @date 2020/11/4 15:45
     */
    boolean getSuperAdminFlag();

    /**
     * 判断当前用户是否登录
     *
     * @return 是否登录，true是，false否
     * @author fengshuonan
     * @date 2020/10/17 11:02
     */
    boolean hasLogin();

}
