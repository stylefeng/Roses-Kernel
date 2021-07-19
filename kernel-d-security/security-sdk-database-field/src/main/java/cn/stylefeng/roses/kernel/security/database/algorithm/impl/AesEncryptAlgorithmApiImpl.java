package cn.stylefeng.roses.kernel.security.database.algorithm.impl;

import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import cn.stylefeng.roses.kernel.security.database.algorithm.EncryptAlgorithmApi;

/**
 * AES 加密解密实现
 *
 * @author majianguo
 * @date 2021/7/3 11:43
 */
public class AesEncryptAlgorithmApiImpl implements EncryptAlgorithmApi {

    /**
     * AES加密实体类
     */
    public SymmetricCrypto symmetricCrypto;

    public AesEncryptAlgorithmApiImpl(byte[] key) {
        symmetricCrypto = new SymmetricCrypto(SymmetricAlgorithm.AES, key);
    }

    @Override
    public void setInstance(SymmetricCrypto instance) {
        this.symmetricCrypto = instance;
    }

    @Override
    public String encrypt(String encryptedData) {
        return symmetricCrypto.encryptHex(encryptedData);
    }

    @Override
    public String decrypt(String cipher) {
        return symmetricCrypto.decryptStr(cipher);
    }
}
