package cn.stylefeng.roses.kernel.i18n.api;

import cn.stylefeng.roses.kernel.i18n.api.enums.TranslationEnum;
import cn.stylefeng.roses.kernel.i18n.api.pojo.TranslationDict;

import java.util.List;
import java.util.Map;

/**
 * 多语言翻译服务api
 *
 * @author fengshuonan
 * @date 2021/1/24 18:50
 */
public interface TranslationApi {

    /**
     * 初始化多语言翻译字典
     *
     * @param translationDict 所有翻译的值
     * @author fengshuonan
     * @date 2021/1/24 19:00
     */
    void init(List<TranslationDict> translationDict);

    /**
     * 获取某个语种下的所有多语言字典
     *
     * @param translationLanguages 语种枚举
     * @return key-翻译项标识，value-翻译的值TranslationApi
     * @author fengshuonan
     * @date 2021/1/24 19:01
     */
    Map<String, String> getTranslationDictByLanguage(TranslationEnum translationLanguages);

}
