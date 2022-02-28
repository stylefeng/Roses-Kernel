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
package cn.stylefeng.roses.kernel.sms.modular.param;

import cn.stylefeng.roses.kernel.rule.annotation.ChineseDescription;
import cn.stylefeng.roses.kernel.sms.modular.enums.SmsSendSourceEnum;
import cn.stylefeng.roses.kernel.sms.modular.enums.SmsTypeEnum;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Map;

/**
 * 发送短信的参数
 *
 * @author fengshuonan
 * @date 2020/10/26 22:16
 */
@Data
public class SysSmsSendParam {

    /**
     * 手机号
     */
    @NotBlank(message = "手机号码为空")
    @ChineseDescription("手机号")
    private String phone;

    /**
     * 模板号
     */
    @NotBlank(message = "模板号为空")
    @ChineseDescription("模板号")
    private String templateCode;

    /**
     * 缓存 key
     */
    @ChineseDescription("缓存key")
    private String verKey;

    /**
     * 图形验证码
     */
    @ChineseDescription("图形验证码")
    private String verCode;

    /**
     * 模板中的参数
     */
    @ChineseDescription("模板中的参数")
    private Map<String, Object> params;

    /**
     * 发送源
     */
    @ChineseDescription("发送源")
    private SmsSendSourceEnum smsSendSourceEnum = SmsSendSourceEnum.PC;

    /**
     * 消息类型，1验证码，2消息，默认不传为验证码
     */
    @ChineseDescription("消息类型：1-验证码，2-消息，默认验证码")
    private SmsTypeEnum smsTypeEnum = SmsTypeEnum.SMS;

}
