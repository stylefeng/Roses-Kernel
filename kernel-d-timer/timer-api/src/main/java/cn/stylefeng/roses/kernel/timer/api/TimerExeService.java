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
package cn.stylefeng.roses.kernel.timer.api;

/**
 * 本接口用来，屏蔽定时任务的多样性
 *
 * @author fengshuonan
 * @date 2020/10/27 13:18
 */
public interface TimerExeService {

    /**
     * 开启定时器调度
     *
     * @author fengshuonan
     * @date 2021/1/12 20:33
     */
    void start();

    /**
     * 关闭定时器调度
     *
     * @author fengshuonan
     * @date 2021/1/12 20:33
     */
    void stop();

    /**
     * 启动一个定时器
     * <p>
     * 定时任务表达式书写规范：0/2 * * * * *
     * <p>
     * 六位数，分别是：秒 分 小时 日 月 年
     *
     * @param taskId    任务id
     * @param cron      cron表达式
     * @param className 类的全名，必须是TimerAction的子类
     * @param params    自定义参数
     * @author stylefeng
     * @date 2020/7/1 13:51
     */
    void startTimer(String taskId, String cron, String className, String params);

    /**
     * 停止一个定时器
     *
     * @param taskId 定时任务Id
     * @author stylefeng
     * @date 2020/7/1 14:08
     */
    void stopTimer(String taskId);

}
