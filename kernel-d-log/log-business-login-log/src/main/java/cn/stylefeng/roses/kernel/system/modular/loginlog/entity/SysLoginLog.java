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
package cn.stylefeng.roses.kernel.system.modular.loginlog.entity;

import cn.stylefeng.roses.kernel.rule.annotation.ChineseDescription;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 登录日志表
 *
 * @author chenjinlong
 * @date 2021/1/13 11:05
 */
@Data
@TableName("sys_login_log")
public class SysLoginLog {

    /**
     * 主键id
     */
    @TableId("llg_id")
    @ChineseDescription("主键id")
    private Long llgId;

    /**
     * 日志名称
     */
    @TableField("llg_name")
    @ChineseDescription("日志名称")
    private String llgName;

    /**
     * 是否执行成功
     */
    @TableField("llg_succeed")
    @ChineseDescription("是否执行成功")
    private String llgSucceed;

    /**
     * 具体消息
     */
    @TableField("llg_message")
    @ChineseDescription("具体消息")
    private String llgMessage;

    /**
     * 登录ip
     */
    @TableField("llg_ip_address")
    @ChineseDescription("登录ip")
    private String llgIpAddress;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    @ChineseDescription("用户id")
    private Long userId;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @ChineseDescription("创建时间")
    private Date createTime;

}
