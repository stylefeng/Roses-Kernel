package cn.stylefeng.roses.kemel.validator.captcha.cache;

import cn.hutool.cache.impl.TimedCache;
import cn.stylefeng.roses.kernel.cache.memory.AbstractMemoryCacheOperator;

import static cn.stylefeng.roses.kernel.validator.api.constants.CaptchaConstants.CAPTCHA_CACHE_KEY_PREFIX;

/**
 * 图形验证码缓存
 *
 * @author chenjinlong
 * @date 2021/1/15 13:44
 */
public class CaptchaRedisCache extends AbstractMemoryCacheOperator<Long> {

    public CaptchaRedisCache(TimedCache<String, Long> timedCache) {
        super(timedCache);
    }

    @Override
    public String getCommonKeyPrefix() {
        return CAPTCHA_CACHE_KEY_PREFIX;
    }

}
