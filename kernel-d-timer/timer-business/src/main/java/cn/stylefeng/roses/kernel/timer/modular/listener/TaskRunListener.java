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
package cn.stylefeng.roses.kernel.timer.modular.listener;

import cn.hutool.extra.spring.SpringUtil;
import cn.stylefeng.roses.kernel.rule.listener.ApplicationStartedListener;
import cn.stylefeng.roses.kernel.timer.api.TimerExeService;
import cn.stylefeng.roses.kernel.timer.api.enums.TimerJobStatusEnum;
import cn.stylefeng.roses.kernel.timer.modular.entity.SysTimers;
import cn.stylefeng.roses.kernel.timer.modular.param.SysTimersParam;
import cn.stylefeng.roses.kernel.timer.modular.service.SysTimersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.core.Ordered;

import java.util.List;

/**
 * 项目启动，将数据库所有定时任务开启
 *
 * @author fengshuonan
 * @date 2021/1/12 20:40
 */
@Slf4j
public class TaskRunListener extends ApplicationStartedListener implements Ordered {

    @Override
    public void eventCallback(ApplicationStartedEvent event) {

        SysTimersService sysTimersService = SpringUtil.getBean(SysTimersService.class);
        TimerExeService timerExeService = SpringUtil.getBean(TimerExeService.class);

        // 开启任务调度
        timerExeService.start();

        // 获取数据库所有开启状态的任务
        SysTimersParam sysTimersParam = new SysTimersParam();
        sysTimersParam.setDelFlag("N");
        sysTimersParam.setJobStatus(TimerJobStatusEnum.RUNNING.getCode());
        List<SysTimers> list = sysTimersService.findList(sysTimersParam);

        // 添加定时任务到调度器
        for (SysTimers sysTimers : list) {
            try {
                timerExeService.startTimer(String.valueOf(sysTimers.getTimerId()), sysTimers.getCron(), sysTimers.getActionClass(), sysTimers.getParams());
            } catch (Exception exception) {
                // 遇到错误直接略过这个定时器（可能多个项目公用库）
                log.error("定时器初始化遇到错误，略过该定时器！", exception);
            }
        }

    }

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE - 300;
    }

}
