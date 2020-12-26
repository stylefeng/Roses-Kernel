package cn.stylefeng.roses.kernel.auth.api.prop;

import cn.stylefeng.roses.kernel.auth.api.pojo.login.LoginUser;

/**
 * 当前登录用户属性的组装和填充
 * <p>
 * LoginUser对象的属性比较多，为了方便后期拓展，统一用此接口为新字段set值
 * <p>
 * 使用方法：
 * <p>
 * 第一步，为LoginUser加新的字段
 * <p>
 * 第二步，新写一个类实现本接口，在expandAction方法为新属性赋值即可
 * <p>
 * 注意，这个方法里边的实现在编写时，不能使用 LoginContext 获取当前用户
 *
 * @author fengshuonan
 * @date 2020/12/22 14:18
 */
public interface LoginUserPropExpander {

    /**
     * 为loginUser赋值
     *
     * @param loginUser 当前登录用户
     * @author fengshuonan
     * @date 2020/12/22 14:26
     */
    void expandAction(LoginUser loginUser);

}
