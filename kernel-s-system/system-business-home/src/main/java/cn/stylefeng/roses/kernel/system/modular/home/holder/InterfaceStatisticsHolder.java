package cn.stylefeng.roses.kernel.system.modular.home.holder;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kernel.auth.api.context.LoginContext;
import cn.stylefeng.roses.kernel.cache.api.CacheOperatorApi;
import cn.stylefeng.roses.kernel.system.modular.home.entity.InterfaceStatistics;
import cn.stylefeng.roses.kernel.system.modular.home.mapper.InterfaceStatisticsMapper;
import cn.stylefeng.roses.kernel.system.modular.home.service.HomePageService;
import cn.stylefeng.roses.kernel.timer.api.TimerAction;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 定时刷新接口访问次数统计
 *
 * @author xixiaowei
 * @date 2022/2/9 16:08
 */
public class InterfaceStatisticsHolder implements TimerAction {

    @Resource
    private HomePageService homePageService;

    @Override
    public void action(String params) {
        homePageService.interfaceStatistics();
    }
}
