package cn.stylefeng.roses.kernel.customer.modular.service;

import cn.hutool.crypto.SecureUtil;
import cn.stylefeng.roses.kernel.customer.api.OldPasswordValidateApi;
import org.springframework.stereotype.Service;

/**
 * 旧验证码校验
 *
 * @author fengshuonan
 * @date 2021/7/6 22:03
 */
@Service
public class OldPasswordValidateService implements OldPasswordValidateApi {

    @Override
    public boolean validatePassword(String passwordOriginal, String passwordEncrypt, String salt) {
        return SecureUtil.md5(passwordOriginal + salt).equals(passwordEncrypt);
    }

}
