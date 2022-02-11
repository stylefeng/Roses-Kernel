package cn.stylefeng.roses.kernel.system.modular.home.service;

import cn.stylefeng.roses.kernel.log.api.pojo.record.LogRecordDTO;
import cn.stylefeng.roses.kernel.system.api.pojo.home.HomeCompanyInfo;
import cn.stylefeng.roses.kernel.system.api.pojo.menu.antd.AntdSysMenuDTO;
import cn.stylefeng.roses.kernel.system.api.pojo.resource.ResourceRequest;
import cn.stylefeng.roses.kernel.system.api.pojo.user.OnlineUserDTO;
import cn.stylefeng.roses.kernel.system.api.pojo.user.request.OnlineUserRequest;
import cn.stylefeng.roses.kernel.system.modular.home.entity.InterfaceStatistics;
import cn.stylefeng.roses.kernel.system.modular.statistic.pojo.OnlineUserStat;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.stylefeng.roses.kernel.system.modular.menu.entity.SysMenu;

import java.util.List;

/**
 * 首页服务接口
 *
 * @author xixiaowei
 * @date 2022/1/25 9:43
 */
public interface HomePageService {

    /**
     * 查询动态列表
     *
     * @author xixiaowei
     * @date 2022/1/25 14:48
     */
    List<LogRecordDTO> getRecentLogs();

    /**
     * 获取在线用户列表
     *
     * @author xixiaowei
     * @date 2022/1/25 14:06
     */
    OnlineUserStat getOnlineUserList(OnlineUserRequest onlineUserRequest);

    /**
     * 获取首页企业和公司信息
     *
     * @author xixiaowei
     * @date 2022/1/25 15:31
     */
    HomeCompanyInfo getHomeCompanyInfo();

    /**
     * 获取常用功能
     *
     * @author xixiaowei
     * @date 2022/2/10 11:19
     */
    List<SysMenu> getCommonFunctions();

    /**
     * 数据统计
     *
     * @author xixiaowei
     * @date 2022/2/10 12:07
     */
    void saveStatisticsCacheToDb();
}
