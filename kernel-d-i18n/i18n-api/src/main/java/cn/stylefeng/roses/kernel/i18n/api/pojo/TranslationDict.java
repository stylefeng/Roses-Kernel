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
package cn.stylefeng.roses.kernel.i18n.api.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * 多语言翻译信息
 *
 * @author stylefeng
 * @since 2019-10-17
 */
@Data
public class TranslationDict implements Serializable {

    /**
     * 翻译项的编码，例如：FIELD_ACCOUNT
     */
    private String tranCode;

    /**
     * 多语言翻译项的说明，例如：针对账号输入框的
     */
    private String tranName;

    /**
     * 多语言语种的编码，值是字典的值，字典类型的编码是 languages
     */
    private String tranLanguageCode;

    /**
     * 翻译的值，例如：账号
     */
    private String tranValue;

}
