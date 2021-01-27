package cn.stylefeng.roses.kernel.i18n.util;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kernel.auth.api.context.LoginContext;
import cn.stylefeng.roses.kernel.i18n.api.context.TranslationContext;

import java.util.Map;

/**
 * 针对某个翻译项快速得出翻译结果
 *
 * @author fengshuonan
 * @date 2021/1/27 22:36
 */
public class QuickTranslateUtil {

    /**
     * 根据当前登陆用户选择的语言，翻译出指定项的译文值
     * <p>
     * 若查找不到code对应的值，则以第二个参数为准
     *
     * @author fengshuonan
     * @date 2021/1/27 22:41
     */
    public static String get(String tranCode, String defaultName) {
        String tranLanguageCode = LoginContext.me().getLoginUser().getTranLanguageCode();
        Map<String, String> dict = TranslationContext.me().getTranslationDictByLanguage(tranLanguageCode);
        if (ObjectUtil.hasEmpty(dict, dict.get(tranCode))) {
            return defaultName;
        } else {
            return dict.get(tranCode);
        }
    }

}
