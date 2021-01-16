package cn.stylefeng.roses.kemel.captcha;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.cache.api.CacheOperatorApi;
import cn.stylefeng.roses.kernel.validator.CaptchaApi;
import cn.stylefeng.roses.kernel.validator.pojo.EasyCaptcha;
import com.wf.captcha.SpecCaptcha;

/**
 * 图形验证码实现
 *
 * @author chenjinlong
 * @date 2021/1/15 13:44
 */
public class CaptchaService implements CaptchaApi {

    private final CacheOperatorApi<String> cacheOperatorApi;

    public CaptchaService(CacheOperatorApi<String> cacheOperatorApi) {
        this.cacheOperatorApi = cacheOperatorApi;
    }

    @Override
    public EasyCaptcha captcha() {
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
        String verCode = specCaptcha.text().toLowerCase();
        String verKey = IdUtil.simpleUUID();
        cacheOperatorApi.put(verKey, verCode);
        return EasyCaptcha.builder().verImage(specCaptcha.toBase64()).verKey(verKey).build();
    }

    @Override
    public boolean validateCaptcha(String verKey, String verCode) {
        if (StrUtil.isAllEmpty(verKey, verCode)) {
            return false;
        }
        if (!verCode.trim().toLowerCase().equals(cacheOperatorApi.get(verKey))) {
            return false;
        }
        //删除缓存中验证码
        cacheOperatorApi.remove(verKey);
        return true;
    }

    @Override
    public String getVerCode(String verKey) {
        return cacheOperatorApi.get(verKey);
    }

}
