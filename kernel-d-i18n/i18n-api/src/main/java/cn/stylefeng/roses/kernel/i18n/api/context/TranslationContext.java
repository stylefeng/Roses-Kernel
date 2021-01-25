package cn.stylefeng.roses.kernel.i18n.api.context;

import cn.hutool.extra.spring.SpringUtil;
import cn.stylefeng.roses.kernel.i18n.api.TranslationApi;

/**
 * 翻译上下文获取
 *
 * @author fengshuonan
 * @date 2021/1/24 19:06
 */
public class TranslationContext {

    /**
     * 获取翻译接口
     *
     * @author fengshuonan
     * @date 2021/1/24 19:06
     */
    public static TranslationApi me() {
        return SpringUtil.getBean(TranslationApi.class);
    }

}
