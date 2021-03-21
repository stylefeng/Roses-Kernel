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
package cn.stylefeng.roses.kernel.i18n.api;

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
     * @param tranLanguageCode 语种字典
     * @return key-翻译项标识，value-翻译的值TranslationApi
     * @author fengshuonan
     * @date 2021/1/24 19:01
     */
    Map<String, String> getTranslationDictByLanguage(String tranLanguageCode);

    /**
     * 添加一个翻译项到context
     *
     * @param translationDict 具体的翻译项
     * @author fengshuonan
     * @date 2021/1/24 21:47
     */
    void addTranslationDict(TranslationDict translationDict);

    /**
     * 删除某条翻译记录
     *
     * @param tranLanguageCode 语种字典
     * @param tranCode         具体翻译项编码
     * @author fengshuonan
     * @date 2021/1/24 21:59
     */
    void deleteTranslationDict(String tranLanguageCode, String tranCode);

}
