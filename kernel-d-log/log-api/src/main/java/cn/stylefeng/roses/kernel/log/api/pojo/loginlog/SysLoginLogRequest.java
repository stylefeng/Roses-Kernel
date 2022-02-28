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
package cn.stylefeng.roses.kernel.log.api.pojo.loginlog;

import cn.stylefeng.roses.kernel.rule.annotation.ChineseDescription;
import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 登录日志表
 *
 * @author chenjinlong
 * @date 2021/1/13 11:06
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysLoginLogRequest extends BaseRequest {

    /**
     * 主键id
     */
    @NotNull(message = "llgId不能为空", groups = {detail.class})
    @ChineseDescription("主鍵id")
    private Long llgId;

    /**
     * 日志名称
     */
    @ChineseDescription("日志名称")
    private String llgName;

    /**
     * 是否执行成功
     */
    @ChineseDescription("是否执行成功")
    private String llgSucceed;

    /**
     * 具体消息
     */
    @ChineseDescription("具体消息")
    private String llgMessage;

    /**
     * 登录ip
     */
    @ChineseDescription("登录ip")
    private String llgIpAddress;

    /**
     * 用户id
     */
    @ChineseDescription("用户id")
    private Long userId;

    /**
     * 创建时间
     */
    @ChineseDescription("创建时间")
    private Date createTime;

    /**
     * 开始时间
     */
    @ChineseDescription("开始时间")
    private String beginTime;

    /**
     * 结束时间
     */
    @ChineseDescription("结束时间")
    private String endTime;

}
