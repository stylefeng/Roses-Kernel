package cn.stylefeng.roses.kernel.auth.password;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.BCrypt;
import cn.stylefeng.roses.kernel.auth.api.password.PasswordStoredEncryptApi;

/**
 * 基于BCrypt算法实现的密码加密解密器
 *
 * @author fengshuonan
 * @date 2020/12/21 17:02
 */
public class BcryptPasswordStoredEncrypt implements PasswordStoredEncryptApi {

    @Override
    public String encrypt(String originPassword) {
        if (StrUtil.isBlank(originPassword)) {
            return null;
        }

        return BCrypt.hashpw(originPassword, BCrypt.gensalt());
    }

    @Override
    public Boolean checkPassword(String encryptBefore, String encryptAfter) {
        return BCrypt.checkpw(encryptBefore, encryptAfter);
    }

}
