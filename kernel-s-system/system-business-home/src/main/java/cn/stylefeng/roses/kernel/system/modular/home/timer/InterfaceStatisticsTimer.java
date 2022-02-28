package cn.stylefeng.roses.kernel.system.modular.home.timer;

import cn.stylefeng.roses.kernel.system.modular.home.service.HomePageService;
import cn.stylefeng.roses.kernel.timer.api.TimerAction;

import javax.annotation.Resource;

/**
 * 定时刷新接口访问次数统计
 *
 * @author xixiaowei
 * @date 2022/2/9 16:08
 */
public class InterfaceStatisticsTimer implements TimerAction {

    @Resource
    private HomePageService homePageService;

    @Override
    public void action(String params) {
        homePageService.saveStatisticsCacheToDb();
    }

}
