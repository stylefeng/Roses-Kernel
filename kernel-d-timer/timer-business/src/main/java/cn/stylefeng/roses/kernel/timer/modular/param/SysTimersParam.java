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
package cn.stylefeng.roses.kernel.timer.modular.param;

import cn.stylefeng.roses.kernel.rule.annotation.ChineseDescription;
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
    @ChineseDescription("定时器id")
    private Long timerId;

    /**
     * 任务名称
     */
    @NotBlank(message = "任务名称不能为空", groups = {add.class, edit.class})
    @ChineseDescription("任务名称")
    private String timerName;

    /**
     * 执行任务的class的类名（实现了TimerTaskRunner接口的类的全称）
     */
    @NotBlank(message = "任务的class的类名不能为空", groups = {add.class, edit.class})
    @ChineseDescription("执行任务的class的类名")
    private String actionClass;

    /**
     * 定时任务表达式
     */
    @NotBlank(message = "定时任务表达式不能为空", groups = {add.class, edit.class})
    @ChineseDescription("定时任务表达式")
    private String cron;

    /**
     * 状态（字典 1运行  2停止）
     */
    @NotNull(message = "任务状态不能为空", groups = {edit.class})
    @ChineseDescription("状态（字典 1运行  2停止）")
    private Integer jobStatus;

    /**
     * 参数
     */
    @ChineseDescription("参数")
    private String params;

    /**
     * 备注信息
     */
    @ChineseDescription("备注信息")
    private String remark;

    /**
     * 是否删除标记
     */
    @ChineseDescription("是否删除标记")
    private String delFlag;

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
