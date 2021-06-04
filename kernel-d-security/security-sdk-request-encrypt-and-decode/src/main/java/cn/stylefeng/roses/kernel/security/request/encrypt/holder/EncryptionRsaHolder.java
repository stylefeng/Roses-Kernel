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

    private static final ThreadLocal<RSA> RSA_HOLDER = new ThreadLocal<RSA>() {
        @Override
        protected RSA initialValue() {
            return new RSA(EncryptionConstants.PRIVATE_KEY, EncryptionConstants.PUBLIC_KEY);
        }
    };

    /**
     * 获取 RSA 实例
     *
     * @author luojie
     * @date 2021/6/4 08:59
     */
    public static RSA getRsa() {
        return RSA_HOLDER.get();
    }

}
