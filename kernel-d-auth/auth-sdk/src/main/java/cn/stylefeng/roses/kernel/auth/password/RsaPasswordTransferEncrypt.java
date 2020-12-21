package cn.stylefeng.roses.kernel.auth.password;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.stylefeng.roses.kernel.auth.api.password.PasswordTransferEncryptApi;

/**
 * 基于RSA加密算法的，用于密码传输用的，密码加密解密器
 *
 * @author fengshuonan
 * @date 2020/12/21 17:02
 */
public class RsaPasswordTransferEncrypt implements PasswordTransferEncryptApi {

    /**
     * rsa公钥
     */
    private final String publicKey;

    /**
     * RSA私钥
     */
    private final String privateKey;

    public RsaPasswordTransferEncrypt(String publicKey, String privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    @Override
    public String encrypt(String originPassword) {
        if (StrUtil.isBlank(originPassword)) {
            return null;
        }
        RSA rsa = new RSA(privateKey, publicKey);
        return rsa.encryptBase64(originPassword, KeyType.PublicKey);
    }

    @Override
    public String decrypt(String encryptedPassword) {
        RSA rsa = new RSA(privateKey, publicKey);
        return rsa.decryptStr(encryptedPassword, KeyType.PrivateKey);
    }

}
