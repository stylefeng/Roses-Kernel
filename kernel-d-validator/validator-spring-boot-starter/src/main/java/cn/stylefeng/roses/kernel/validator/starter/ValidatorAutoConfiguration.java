package cn.stylefeng.roses.kernel.validator.starter;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.stylefeng.roses.kemel.validator.blackwhite.BlackListService;
import cn.stylefeng.roses.kemel.validator.blackwhite.WhiteListService;
import cn.stylefeng.roses.kemel.validator.blackwhite.cache.BlackListMemoryCache;
import cn.stylefeng.roses.kemel.validator.blackwhite.cache.WhiteListMemoryCache;
import cn.stylefeng.roses.kemel.validator.captcha.CaptchaService;
import cn.stylefeng.roses.kemel.validator.captcha.cache.CaptchaMemoryCache;
import cn.stylefeng.roses.kemel.validator.count.DefaultCountValidator;
import cn.stylefeng.roses.kemel.validator.count.cache.DefaultCountValidateCache;
import cn.stylefeng.roses.kernel.validator.api.BlackListApi;
import cn.stylefeng.roses.kernel.validator.api.CaptchaApi;
import cn.stylefeng.roses.kernel.validator.api.CountValidatorApi;
import cn.stylefeng.roses.kernel.validator.api.WhiteListApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static cn.stylefeng.roses.kernel.cache.api.constants.CacheConstants.NONE_EXPIRED_TIME;

/**
 * 校验器自动配置
 *
 * @author fengshuonan
 * @date 2020/12/1 21:44
 */
@Configuration
public class ValidatorAutoConfiguration {

    /**
     * 黑名单校验
     *
     * @author fengshuonan
     * @date 2020/12/1 21:18
     */
    @Bean
    @ConditionalOnMissingBean(BlackListApi.class)
    public BlackListApi blackListApi() {
        TimedCache<String, String> timedCache = CacheUtil.newTimedCache(NONE_EXPIRED_TIME);
        BlackListMemoryCache blackListMemoryCache = new BlackListMemoryCache(timedCache);
        return new BlackListService(blackListMemoryCache);
    }

    /**
     * 计数校验器
     *
     * @author fengshuonan
     * @date 2020/12/1 21:18
     */
    @Bean
    @ConditionalOnMissingBean(CountValidatorApi.class)
    public CountValidatorApi countValidatorApi() {
        TimedCache<String, Long> timedCache = CacheUtil.newTimedCache(NONE_EXPIRED_TIME);
        DefaultCountValidateCache defaultCountValidateCache = new DefaultCountValidateCache(timedCache);
        return new DefaultCountValidator(defaultCountValidateCache);
    }

    /**
     * 白名单校验
     *
     * @author fengshuonan
     * @date 2020/12/1 21:18
     */
    @Bean
    @ConditionalOnMissingBean(WhiteListApi.class)
    public WhiteListApi whiteListApi() {
        TimedCache<String, String> timedCache = CacheUtil.newTimedCache(NONE_EXPIRED_TIME);
        WhiteListMemoryCache whiteListMemoryCache = new WhiteListMemoryCache(timedCache);
        return new WhiteListService(whiteListMemoryCache);
    }

    /**
     * 图形验证码
     *
     * @author chenjinlong
     * @date 2021/1/15 11:25
     */
    @Bean
    @ConditionalOnMissingBean(CaptchaApi.class)
    public CaptchaApi captchaApi() {
        TimedCache<String, String> timedCache = CacheUtil.newTimedCache(NONE_EXPIRED_TIME);
        CaptchaMemoryCache captchaMemoryCache = new CaptchaMemoryCache(timedCache);
        return new CaptchaService(captchaMemoryCache);
    }

}
