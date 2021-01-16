package cn.stylefeng.roses.kernel.notice.modular.entity;

import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
import cn.stylefeng.roses.kernel.dict.api.serializer.DictValueSerializer;
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
    private Long noticeId;

    /**
     * 通知标题
     */
    @TableField("notice_title")
    private String noticeTitle;
    /**
     * 通知摘要
     */
    @TableField("notice_summary")
    private String noticeSummary;

    /**
     * 通知优先级
     */
    @TableField(value = "priority_level")
    private String priorityLevel;


    /**
     * 通知开始时间
     */
    @TableField(value = "notice_begin_time")
    private Date noticeBeginTime;


    /**
     * 通知结束时间
     */
    @TableField(value = "notice_end_time")
    private Date noticeEndTime;

    /**
     * 通知内容
     */
    @TableField("notice_content")
    private String noticeContent;

    /**
     * 通知范围
     */
    @TableField("notice_scope")
    private String noticeScope;

    /**
     * 是否删除：Y-已删除，N-未删除
     */
    @TableField(value = "del_flag", fill = FieldFill.INSERT)
    private String delFlag;

    /*public String getPriorityLevelValue(){
        AtomicReference<String> value = new AtomicReference<>("");
        Optional.ofNullable(this.priorityLevel).ifPresent(val ->{
            value.set(MessagePriorityLevelEnum.getName(this.priorityLevel));
        });
        return value.get();
    }*/
    @JsonSerialize(using = DictValueSerializer.class)
    public String getPriorityLevelValue() {
        return "priority_level|" + this.priorityLevel;
    }

}
