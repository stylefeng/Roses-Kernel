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
package cn.stylefeng.roses.kernel.system.modular.notice.entity;

import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
import cn.stylefeng.roses.kernel.dict.api.serializer.DictValueSerializer;
import cn.stylefeng.roses.kernel.rule.annotation.ChineseDescription;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 通知表
 *
 * @author liuhanqing
 * @date 2021/1/8 22:45
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_notice")
public class SysNotice extends BaseEntity {

    /**
     * 通知id
     */
    @TableId("notice_id")
    @ChineseDescription("通知id")
    private Long noticeId;

    /**
     * 通知标题
     */
    @TableField("notice_title")
    @ChineseDescription("通知标题")
    private String noticeTitle;

    /**
     * 通知摘要
     */
    @TableField("notice_summary")
    @ChineseDescription("通知摘要")
    private String noticeSummary;

    /**
     * 通知优先级
     */
    @TableField(value = "priority_level")
    @ChineseDescription("通知优先级")
    private String priorityLevel;

    /**
     * 通知开始时间
     */
    @TableField(value = "notice_begin_time")
    @ChineseDescription("通知开始时间")
    private Date noticeBeginTime;

    /**
     * 通知结束时间
     */
    @TableField(value = "notice_end_time")
    @ChineseDescription("通知结束时间")
    private Date noticeEndTime;

    /**
     * 通知内容
     */
    @TableField("notice_content")
    @ChineseDescription("通知内容")
    private String noticeContent;

    /**
     * 通知范围
     */
    @TableField("notice_scope")
    @ChineseDescription("通知范围")
    private String noticeScope;

    /**
     * 是否删除：Y-已删除，N-未删除
     */
    @TableField(value = "del_flag", fill = FieldFill.INSERT)
    @ChineseDescription("是否删除：Y-已删除，N-未删除")
    private String delFlag;

    /**
     * 通知优先级的名称
     */
    @JsonSerialize(using = DictValueSerializer.class)
    public String getPriorityLevelValue() {
        return "priority_level|" + this.priorityLevel;
    }

}
