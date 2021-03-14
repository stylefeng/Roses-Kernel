package cn.stylefeng.roses.kemel.security.captcha.cache;

import cn.hutool.cache.impl.TimedCache;
import cn.stylefeng.roses.kernel.cache.memory.AbstractMemoryCacheOperator;
import cn.stylefeng.roses.kernel.security.api.constants.CaptchaConstants;

/**
 * 图形验证码缓存
 *
 * @author chenjinlong
 * @date 2021/1/15 13:44
 */
public class CaptchaMemoryCache extends AbstractMemoryCacheOperator<String> {

    public CaptchaMemoryCache(TimedCache<String, String> timedCache) {
        super(timedCache);
    }

    @Override
    public String getCommonKeyPrefix() {
        return CaptchaConstants.CAPTCHA_CACHE_KEY_PREFIX;
    }

}
