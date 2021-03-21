/*
 * Copyright [2020-2030] [https://www.stylefeng.cn]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Guns采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Guns源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/stylefeng/guns
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/stylefeng/guns
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */
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
