package cn.stylefeng.roses.kernel.i18n;

import cn.stylefeng.roses.kernel.i18n.api.TranslationApi;
import cn.stylefeng.roses.kernel.i18n.api.enums.TranslationEnum;
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
     * key是语种，value是对应语种下的所有key value翻译值（第二个key是具体翻译项的编码）
     */
    private static final Map<TranslationEnum, Map<String, String>> TRAN_DICT_CONTAINER = new ConcurrentHashMap<>();

    @Override
    public void init(List<TranslationDict> translationDict) {

        // 根据语种数量，创建多个语种的翻译Map
        for (TranslationEnum type : TranslationEnum.values()) {
            HashMap<String, String> typeMap = new HashMap<>();
            TRAN_DICT_CONTAINER.put(type, typeMap);
        }

        // 整理数据库中的字典
        for (TranslationDict translationItem : translationDict) {
            TranslationEnum translationLanguages = translationItem.getTranslationLanguages();
            TRAN_DICT_CONTAINER.get(translationLanguages).put(translationItem.getTranCode(), translationItem.getTranValue());
        }

    }

    @Override
    public Map<String, String> getTranslationDictByLanguage(TranslationEnum translationLanguages) {
        return TRAN_DICT_CONTAINER.get(translationLanguages);
    }

}
