package cn.stylefeng.roses.kernel.security.request.encrypt.holder;

import cn.hutool.crypto.asymmetric.RSA;
import cn.stylefeng.roses.kernel.security.request.encrypt.constants.EncryptionConstants;

/**
 * 用于存储RSA实例
 *
 * @author luojie
 * @date 2021/6/4 08:58
 */
public class EncryptionRsaHolder {

    public static RSA STATIC_RSA = new RSA(EncryptionConstants.PRIVATE_KEY, EncryptionConstants.PUBLIC_KEY);

}
