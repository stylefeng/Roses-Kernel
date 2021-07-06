package cn.stylefeng.roses.kernel.customer.api;

/**
 * 旧版密码校验api
 *
 * @author fengshuonan
 * @date 2021/7/6 22:01
 */
public interface OldPasswordValidateApi {

    /**
     * 校验密码
     *
     * @author fengshuonan
     * @date 2021/7/6 22:02
     */
    boolean validatePassword(String passwordOriginal, String passwordEncrypt, String salt);

}
