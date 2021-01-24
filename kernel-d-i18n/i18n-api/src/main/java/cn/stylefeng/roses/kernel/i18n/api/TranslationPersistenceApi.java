package cn.stylefeng.roses.kernel.i18n.api;

import cn.stylefeng.roses.kernel.i18n.api.pojo.TranslationDict;

import java.util.List;

/**
 * 多语言字典持久化api
 *
 * @author fengshuonan
 * @date 2021/1/24 19:32
 */
public interface TranslationPersistenceApi {

    /**
     * 获取所有的翻译项
     *
     * @author fengshuonan
     * @date 2021/1/24 19:33
     */
    List<TranslationDict> getAllTranslationDict();

}
