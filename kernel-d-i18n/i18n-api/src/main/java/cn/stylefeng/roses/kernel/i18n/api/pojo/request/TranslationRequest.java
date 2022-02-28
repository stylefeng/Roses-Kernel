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
package cn.stylefeng.roses.kernel.i18n.api.pojo.request;

import cn.stylefeng.roses.kernel.rule.annotation.ChineseDescription;
import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 多语言请求信息
 *
 * @author stylefeng
 * @since 2019-10-17
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TranslationRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @NotNull(message = "tranId不能为空", groups = {edit.class, detail.class, delete.class})
    @ChineseDescription("主键id")
    private Long tranId;

    /**
     * 编码
     */
    @NotBlank(message = "tranCode不能为空", groups = {add.class, edit.class})
    @ChineseDescription("编码")
    private String tranCode;

    /**
     * 多语言条例名称
     */
    @NotBlank(message = "tranName不能为空", groups = {add.class, edit.class})
    @ChineseDescription("多语言条例名称")
    private String tranName;

    /**
     * 语种字典
     */
    @NotBlank(message = "tranLanguageCode不能为空", groups = {add.class, edit.class, changeUserLanguage.class, deleteTranLanguage.class})
    @ChineseDescription("语种字典")
    private String tranLanguageCode;

    /**
     * 翻译的值
     */
    @NotBlank(message = "tranValue不能为空", groups = {add.class, edit.class})
    @ChineseDescription("翻译的值")
    private String tranValue;

    /**
     * 字典id，用在删除语种
     */
    @NotNull(message = "字典id", groups = {deleteTranLanguage.class})
    @ChineseDescription("字典id，用在删除语种")
    private Long dictId;

    /**
     * 改变当前用户多语言
     */
    public @interface changeUserLanguage {
    }

    /**
     * 删除语种
     */
    public @interface deleteTranLanguage {
    }

}
