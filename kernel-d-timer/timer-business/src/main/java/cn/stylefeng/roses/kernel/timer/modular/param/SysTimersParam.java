package cn.stylefeng.roses.kernel.timer.modular.param;

import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 定时任务
 *
 * @author stylefeng
 * @date 2020/6/30 18:26
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysTimersParam extends BaseRequest {

    /**
     * 定时器id
     */
    @NotNull(message = "主键timerId不能为空", groups = {edit.class, detail.class, delete.class, startTimer.class, stopTimer.class})
    private Long timerId;

    /**
     * 任务名称
     */
    @NotBlank(message = "任务名称不能为空", groups = {add.class, edit.class})
    private String timerName;

    /**
     * 执行任务的class的类名（实现了TimerTaskRunner接口的类的全称）
     */
    @NotBlank(message = "任务的class的类名不能为空", groups = {add.class, edit.class})
    private String actionClass;

    /**
     * 定时任务表达式
     */
    @NotBlank(message = "定时任务表达式不能为空", groups = {add.class, edit.class})
    private String cron;

    /**
     * 状态（字典 1运行  2停止）
     */
    @NotNull(message = "任务状态不能为空", groups = {edit.class})
    private Integer jobStatus;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 启用定时任务
     */
    public @interface startTimer {
    }

    /**
     * 停止定时任务
     */
    public @interface stopTimer {
    }

}
