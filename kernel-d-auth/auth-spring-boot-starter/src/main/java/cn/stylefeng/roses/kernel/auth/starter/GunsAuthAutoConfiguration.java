package cn.stylefeng.roses.kernel.auth.starter;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.stylefeng.roses.kernel.auth.api.SessionManagerApi;
import cn.stylefeng.roses.kernel.auth.api.cookie.SessionCookieCreator;
import cn.stylefeng.roses.kernel.auth.api.expander.AuthConfigExpander;
import cn.stylefeng.roses.kernel.auth.api.password.PasswordStoredEncryptApi;
import cn.stylefeng.roses.kernel.auth.api.password.PasswordTransferEncryptApi;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.LoginUser;
import cn.stylefeng.roses.kernel.auth.password.BcryptPasswordStoredEncrypt;
import cn.stylefeng.roses.kernel.auth.password.RsaPasswordTransferEncrypt;
import cn.stylefeng.roses.kernel.auth.session.DefaultSessionManager;
import cn.stylefeng.roses.kernel.auth.session.cache.logintoken.MemoryLoginTokenCache;
import cn.stylefeng.roses.kernel.auth.session.cache.loginuser.MemoryLoginUserCache;
import cn.stylefeng.roses.kernel.auth.session.cookie.DefaultSessionCookieCreator;
import cn.stylefeng.roses.kernel.cache.api.constants.CacheConstants;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;


/**
 * 认证和鉴权模块的自动配置
 *
 * @author fengshuonan
 * @date 2020/11/30 22:16
 */
@Configuration
public class GunsAuthAutoConfiguration {

    /**
     * Bcrypt方式的密码加密
     *
     * @author fengshuonan
     * @date 2020/12/21 17:45
     */
    @Bean
    public PasswordStoredEncryptApi passwordStoredEncryptApi() {
        return new BcryptPasswordStoredEncrypt();
    }

    /**
     * Bcrypt方式的密码加密
     *
     * @author fengshuonan
     * @date 2020/12/21 17:45
     */
    @Bean
    public PasswordTransferEncryptApi passwordTransferEncryptApi() {
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCytSVn3ff7eBJckAFYwgJjqE9Zq2uAL4g+hkfQqGALdT8NJKALFxNzeSD/xTBLAJrtALWbN1dvyktoVNPAuuzCZO1BxYZNaAU3IKFaj73OSPzca5SGY0ibMw0KvEPkC3sZQeqBqx+VqYAqan90BeG/r9p36Eb0wrshj5XmsFeo6QIDAQAB";
        String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALK1JWfd9/t4ElyQAVjCAmOoT1mra4AviD6GR9CoYAt1Pw0koAsXE3N5IP/FMEsAmu0AtZs3V2/KS2hU08C67MJk7UHFhk1oBTcgoVqPvc5I/NxrlIZjSJszDQq8Q+QLexlB6oGrH5WpgCpqf3QF4b+v2nfoRvTCuyGPleawV6jpAgMBAAECgYBS9fUfetQcUWl0vwVhBu/FA+WSYxnMsEQ3gm7kVsX/i7Zxi4cgnt3QxXKkSg5ZQzaov6OPIuncY7UOAhMrbZtq/Hh8atdTVC/Ng/X9Bomodplq/+KTe/vIfWW5rlQAnMNFVaidxhCVRlRHNusapmj2vYwsiyI9kXUJNHryxtFC4QJBANtQuh3dtd79t3MVaC3TUD/EsGBe9TB3Eykbgv0muannC2Oq8Ci4vIp0NSA+FNCoB8ctgfKJUdBS8RLVnYyu3RcCQQDQmY+AuAXEpO9SgcYeRnQSOU2OSuC1wLt1MRVpPTdvE3bfRnkVxMrK0n3YilQWkQzfkERSG4kRFLIw605xPWn/AkEAiw3vQ9p8Yyu5MiXDjTKrchMyxZfPnHATXQANmJcCJ0DQDtymMxuWp66wtIXIStgPPnGTMAVzM0Qzh/6bS0Tf9wJAWj+1yFjVlghNyoJ+9qZAnYnRNhjLM5dZAxDjVI65pwLi0SKqTHLB0hJThBYE32aODUNba7KiEJPFrEiBvZh2fQJARbboHuHT0PqD1UTJGgodHlaw48kreBU+twext/9/jIqvwmFF88BmQgssHGW/tn4E6Qy3+rCCNWreEReY0gATYw==";
        return new RsaPasswordTransferEncrypt(publicKey, privateKey);
    }

    /**
     * session cookie的创建
     *
     * @author fengshuonan
     * @date 2020/12/27 15:48
     */
    @Bean
    public SessionCookieCreator sessionCookieCreator() {
        return new DefaultSessionCookieCreator();
    }

    /**
     * 默认的session缓存为内存缓存，方便启动
     * <p>
     * 如需替换请在项目中增加一个SessionManagerApi Bean即可
     *
     * @author fengshuonan
     * @date 2020/11/30 22:17
     */
    @Bean
    @ConditionalOnMissingBean(SessionManagerApi.class)
    public SessionManagerApi sessionManagerApi() {
        Long sessionExpiredSeconds = AuthConfigExpander.getSessionExpiredSeconds();

        TimedCache<String, LoginUser> loginUsers = CacheUtil.newTimedCache(1000L * sessionExpiredSeconds);
        TimedCache<String, Set<String>> loginTokens = CacheUtil.newTimedCache(CacheConstants.NONE_EXPIRED_TIME);

        MemoryLoginUserCache memoryLoginUserCache = new MemoryLoginUserCache(loginUsers);
        MemoryLoginTokenCache memoryLoginTokenCache = new MemoryLoginTokenCache(loginTokens);

        return new DefaultSessionManager(memoryLoginUserCache, memoryLoginTokenCache, sessionExpiredSeconds, sessionCookieCreator());
    }

}
