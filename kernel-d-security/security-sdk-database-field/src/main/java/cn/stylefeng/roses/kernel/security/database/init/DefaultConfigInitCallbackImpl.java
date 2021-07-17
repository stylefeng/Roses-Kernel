package cn.stylefeng.roses.kernel.security.database.init;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import cn.hutool.extra.spring.SpringUtil;
import cn.stylefeng.roses.kernel.config.api.ConfigInitCallbackApi;
import cn.stylefeng.roses.kernel.security.api.expander.SecurityConfigExpander;
import cn.stylefeng.roses.kernel.security.database.algorithm.EncryptAlgorithmApi;
import org.springframework.stereotype.Component;

import javax.security.auth.login.LoginContext;
import java.nio.charset.StandardCharsets;

/**
 * 数据库加密配置初始化回调
 *
 * @author majianguo
 * @date 2021/7/17 16:01
 */
@Component
public class DefaultConfigInitCallbackImpl implements ConfigInitCallbackApi {

    @Override
    public void initBefore() {

    }

    @Override
    public void initAfter() {
        // 修改数据库秘钥AES实例
        EncryptAlgorithmApi encryptAlgorithmApi = SpringUtil.getBean(EncryptAlgorithmApi.class);
        SymmetricCrypto symmetricCrypto = new SymmetricCrypto(SymmetricAlgorithm.AES, SecurityConfigExpander.getEncryptSecretKey().getBytes(StandardCharsets.UTF_8));
        ReflectUtil.setFieldValue(encryptAlgorithmApi, "symmetricCrypto", symmetricCrypto);
    }
}
