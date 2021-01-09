package cn.stylefeng.roses.kernel.system.pojo.notice;

import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 系统通知参数
 *
 * @author liuhanqing
 * @date 2021/1/8 21:53
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysNoticeRequest extends BaseRequest {


    /**
     * 通知id
     */
    @NotNull(message = "noticeId不能为空", groups = {edit.class, delete.class, detail.class})
    private Long noticeId;

    /**
     * 通知标题
     */
    @NotBlank(message = "通知标题不能为空", groups = {add.class, edit.class})
    private String noticeTitle;
    /**
     * 通知摘要
     */
    private String noticeSummary;

    /**
     * 通知优先级
     */
    @NotBlank(message = "通知优先级不能为空", groups = {add.class, edit.class})
    private String priorityLevel;


    /**
     * 通知开始时间
     */
    @NotNull(message = "通知开始时间不能为空", groups = {add.class, edit.class})
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date noticeBeginTime;


    /**
     * 通知结束时间
     */
    @NotNull(message = "通知开始时间不能为空", groups = {add.class, edit.class})
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date noticeEndTime;

    /**
     * 通知内容
     */
    private String noticeContent;

    /**
     * 通知范围
     */
    private String noticeScope;

}
