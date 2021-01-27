package cn.stylefeng.roses.kernel.i18n;

import cn.stylefeng.roses.kernel.i18n.api.TranslationApi;
import cn.stylefeng.roses.kernel.i18n.api.pojo.TranslationDict;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 翻译字典的容器
 *
 * @author fengshuonan
 * @date 2021/1/24 19:08
 */
public class TranslationContainer implements TranslationApi {

    /**
     * 所有翻译的条目的字典项
     * <p>
     * key是语种（字典），value是对应语种下的所有key value翻译值（第二个key是具体翻译项的编码）
     */
    private static final Map<String, Map<String, String>> TRAN_DICT_CONTAINER = new ConcurrentHashMap<>();

    @Override
    public void init(List<TranslationDict> translationDict) {
        for (TranslationDict translationItem : translationDict) {
            this.addTranslationDict(translationItem);
        }
    }

    @Override
    public Map<String, String> getTranslationDictByLanguage(String tranLanguageCode) {
        return TRAN_DICT_CONTAINER.get(tranLanguageCode);
    }

    @Override
    public void addTranslationDict(TranslationDict translationDict) {
        String tranLanguageCode = translationDict.getTranLanguageCode();

        Map<String, String> languageDict = TRAN_DICT_CONTAINER.get(tranLanguageCode);
        if (languageDict == null) {
            languageDict = new HashMap<>();
        }
        languageDict.put(translationDict.getTranCode(), translationDict.getTranValue());

        TRAN_DICT_CONTAINER.put(tranLanguageCode, languageDict);
    }

    @Override
    public void deleteTranslationDict(String tranLanguageCode, String tranCode) {
        Map<String, String> languageDict = TRAN_DICT_CONTAINER.get(tranLanguageCode);

        if (languageDict == null) {
            return;
        }
        languageDict.remove(tranCode);

        TRAN_DICT_CONTAINER.put(tranLanguageCode, languageDict);
    }

}
