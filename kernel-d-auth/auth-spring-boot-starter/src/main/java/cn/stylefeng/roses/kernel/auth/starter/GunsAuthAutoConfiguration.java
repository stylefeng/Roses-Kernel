/*
 * Copyright [2020-2030] [https://www.stylefeng.cn]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Guns采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Guns源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/stylefeng/guns
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/stylefeng/guns
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */
package cn.stylefeng.roses.kernel.auth.starter;

import cn.stylefeng.roses.kernel.auth.api.SessionManagerApi;
import cn.stylefeng.roses.kernel.auth.api.cookie.SessionCookieCreator;
import cn.stylefeng.roses.kernel.auth.api.expander.AuthConfigExpander;
import cn.stylefeng.roses.kernel.auth.api.password.PasswordStoredEncryptApi;
import cn.stylefeng.roses.kernel.auth.api.password.PasswordTransferEncryptApi;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.LoginUser;
import cn.stylefeng.roses.kernel.auth.password.BcryptPasswordStoredEncrypt;
import cn.stylefeng.roses.kernel.auth.password.RsaPasswordTransferEncrypt;
import cn.stylefeng.roses.kernel.auth.session.DefaultSessionManager;
import cn.stylefeng.roses.kernel.auth.session.cookie.DefaultSessionCookieCreator;
import cn.stylefeng.roses.kernel.auth.session.timer.ClearInvalidLoginUserCacheTimer;
import cn.stylefeng.roses.kernel.cache.api.CacheOperatorApi;
import cn.stylefeng.roses.kernel.jwt.JwtTokenOperator;
import cn.stylefeng.roses.kernel.jwt.api.JwtApi;
import cn.stylefeng.roses.kernel.jwt.api.pojo.config.JwtConfig;
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
     * jwt操作工具类的配置
     *
     * @author fengshuonan
     * @date 2020/12/1 14:40
     */
    @Bean
    @ConditionalOnMissingBean(JwtApi.class)
    public JwtApi jwtApi() {

        JwtConfig jwtConfig = new JwtConfig();

        // 从系统配置表中读取配置
        jwtConfig.setJwtSecret(AuthConfigExpander.getAuthJwtSecret());
        jwtConfig.setExpiredSeconds(AuthConfigExpander.getAuthJwtTimeoutSeconds());

        return new JwtTokenOperator(jwtConfig);
    }

    /**
     * Bcrypt方式的密码加密
     *
     * @author fengshuonan
     * @date 2020/12/21 17:45
     */
    @Bean
    @ConditionalOnMissingBean(PasswordStoredEncryptApi.class)
    public PasswordStoredEncryptApi passwordStoredEncryptApi() {
        return new BcryptPasswordStoredEncrypt();
    }

    /**
     * RSA方式密码加密传输
     *
     * @author fengshuonan
     * @date 2020/12/21 17:45
     */
    @Bean
    @ConditionalOnMissingBean(PasswordTransferEncryptApi.class)
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
    @ConditionalOnMissingBean(SessionCookieCreator.class)
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
    public SessionManagerApi sessionManagerApi(CacheOperatorApi<LoginUser> loginUserCache, CacheOperatorApi<Set<String>> allPlaceLoginTokenCache) {
        Long sessionExpiredSeconds = AuthConfigExpander.getSessionExpiredSeconds();
        return new DefaultSessionManager(loginUserCache, allPlaceLoginTokenCache, sessionExpiredSeconds, sessionCookieCreator());
    }

    /**
     * 清空无用登录用户缓存的定时任务
     *
     * @author fengshuonan
     * @date 2021/3/30 11:32
     */
    @Bean
    @ConditionalOnMissingBean(ClearInvalidLoginUserCacheTimer.class)
    public ClearInvalidLoginUserCacheTimer clearInvalidLoginUserCacheTimer(CacheOperatorApi<LoginUser> loginUserCache, CacheOperatorApi<Set<String>> allPlaceLoginTokenCache) {
        return new ClearInvalidLoginUserCacheTimer(loginUserCache, allPlaceLoginTokenCache);
    }

}
