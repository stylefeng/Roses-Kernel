package cn.stylefeng.roses.kernel.i18n.modular.factory;

import cn.stylefeng.roses.kernel.i18n.api.enums.TranslationEnum;
import cn.stylefeng.roses.kernel.i18n.api.pojo.TranslationDict;
import cn.stylefeng.roses.kernel.i18n.modular.entity.Translation;

/**
 * 创建翻译字典
 *
 * @author fengshuonan
 * @date 2021/1/24 21:50
 */
public class TranslationDictFactory {

    /**
     * 创建翻译字典
     *
     * @author fengshuonan
     * @date 2021/1/24 21:50
     */
    public static TranslationDict createTranslationDict(TranslationEnum translationEnum, Translation translation) {
        TranslationDict translationDict = new TranslationDict();
        translationDict.setTranName(translation.getTranName());
        translationDict.setTranCode(translation.getTranCode());
        translationDict.setTranValue(translation.getTranValue());
        translationDict.setTranslationLanguages(translationEnum);
        return translationDict;
    }

}
