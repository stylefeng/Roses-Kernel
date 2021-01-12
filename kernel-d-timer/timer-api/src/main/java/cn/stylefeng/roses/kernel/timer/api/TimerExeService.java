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
     * @author stylefeng
     * @date 2020/7/1 13:51
     */
    void startTimer(String taskId, String cron, String className);

    /**
     * 停止一个定时器
     *
     * @param taskId 定时任务Id
     * @author stylefeng
     * @date 2020/7/1 14:08
     */
    void stopTimer(String taskId);

}
