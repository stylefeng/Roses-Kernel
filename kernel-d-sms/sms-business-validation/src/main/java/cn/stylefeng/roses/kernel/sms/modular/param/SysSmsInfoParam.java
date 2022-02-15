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
import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 系统短信表
 *
 * @author fengshuonan
 * @date 2020/10/26 22:16
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysSmsInfoParam extends BaseRequest {

    /**
     * 主键
     */
    @ChineseDescription("主键id")
    private Long smsId;

    /**
     * 手机号
     */
    @ChineseDescription("手机号")
    private String phone;

    /**
     * 短信验证码
     */
    @ChineseDescription("短信验证码")
    private String validateCode;

    /**
     * 短信模板编号
     */
    @ChineseDescription("短信模板编号")
    private String templateCode;

    /**
     * 业务id
     */
    @ChineseDescription("业务id")
    private String bizId;

    /**
     * 发送状态：1-未发送，2-发送成功，3-发送失败，4-失效
     */
    @ChineseDescription("发送状态")
    private Integer statusFlag;

    /**
     * 来源：1-app，2-pc，3-其他
     */
    @ChineseDescription("来源")
    private Integer source;

    /**
     * 短信失效截止时间
     */
    @ChineseDescription("短信失效戒截止时间")
    private Date invalidTime;

}
