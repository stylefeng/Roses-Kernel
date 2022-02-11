package cn.stylefeng.roses.kernel.system.modular.home.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kernel.auth.api.context.LoginContext;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.LoginUser;
import cn.stylefeng.roses.kernel.cache.api.CacheOperatorApi;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.log.api.LogManagerApi;
import cn.stylefeng.roses.kernel.log.api.pojo.manage.LogManagerRequest;
import cn.stylefeng.roses.kernel.log.api.pojo.record.LogRecordDTO;
import cn.stylefeng.roses.kernel.scanner.api.pojo.resource.ResourceDefinition;
import cn.stylefeng.roses.kernel.scanner.api.pojo.resource.ResourceUrlParam;
import cn.stylefeng.roses.kernel.system.api.*;
import cn.stylefeng.roses.kernel.system.api.pojo.home.HomeCompanyInfo;
import cn.stylefeng.roses.kernel.system.api.pojo.menu.antd.AntdSysMenuDTO;
import cn.stylefeng.roses.kernel.system.api.pojo.organization.HrOrganizationDTO;
import cn.stylefeng.roses.kernel.system.api.pojo.resource.ResourceRequest;
import cn.stylefeng.roses.kernel.system.api.pojo.user.OnlineUserDTO;
import cn.stylefeng.roses.kernel.system.api.pojo.user.request.OnlineUserRequest;
import cn.stylefeng.roses.kernel.system.api.pojo.user.request.SysUserRequest;
import cn.stylefeng.roses.kernel.system.modular.home.entity.SysStatisticsUrl;
import cn.stylefeng.roses.kernel.system.modular.home.service.HomePageService;
import cn.stylefeng.roses.kernel.system.modular.home.entity.SysStatisticsCount;
import cn.stylefeng.roses.kernel.system.modular.home.service.SysStatisticsCountService;
import cn.stylefeng.roses.kernel.system.modular.home.service.SysStatisticsUrlService;
import cn.stylefeng.roses.kernel.system.modular.menu.entity.SysMenu;
import cn.stylefeng.roses.kernel.system.modular.menu.service.SysMenuService;
import cn.stylefeng.roses.kernel.system.modular.statistic.entity.SysStatisticsCount;
import cn.stylefeng.roses.kernel.system.modular.statistic.pojo.OnlineUserStat;
import cn.stylefeng.roses.kernel.system.modular.statistic.service.SysStatisticsCountService;
import cn.stylefeng.roses.kernel.system.modular.user.entity.SysUserOrg;
import cn.stylefeng.roses.kernel.system.modular.user.service.SysUserOrgService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 首页服务实现类
 *
 * @author xixiaowei
 * @date 2022/1/25 9:45
 */
@Service
public class HomePageServiceImpl implements HomePageService, HomePageServiceApi {

    @Resource
    private LogManagerApi logManagerApi;

    @Resource
    private UserServiceApi userServiceApi;

    @Resource
    private OrganizationServiceApi organizationServiceApi;

    @Resource
    private PositionServiceApi positionServiceApi;

    @Resource
    private SysUserOrgService sysUserOrgService;

    @Resource
    private ResourceServiceApi resourceServiceApi;

    @Resource(name = "requestCountCacheApi")
    private CacheOperatorApi<Map<Long, Integer>> requestCountCacheApi;

    @Resource
    private SysStatisticsCountService sysStatisticsCountService;

    @Resource
    private SysStatisticsUrlService sysStatisticsUrlService;

    @Resource
    private SysMenuService sysMenuService;

    @Override
    public List<LogRecordDTO> getRecentLogs() {

        // 只查询当前用户的
        LogManagerRequest logManagerRequest = new LogManagerRequest();
        logManagerRequest.setUserId(LoginContext.me().getLoginUser().getUserId());

        PageResult<LogRecordDTO> page = logManagerApi.findPage(logManagerRequest);
        return page.getRows();
    }

    @Override
    public OnlineUserStat getOnlineUserList(OnlineUserRequest onlineUserRequest) {

        OnlineUserStat onlineUserStat = new OnlineUserStat();

        // 获取在线总人数
        List<OnlineUserDTO> onlineUserDTOS = userServiceApi.onlineUserList(onlineUserRequest);

        // 去重
        HashSet<String> onlineUserList = new HashSet<>();
        for (OnlineUserDTO onlineUserDTO : onlineUserDTOS) {
            if (ObjectUtil.isNotEmpty(onlineUserDTO.getRealName())) {
                onlineUserList.add(onlineUserDTO.getRealName());
            }
        }
        onlineUserStat.setTotalNum(onlineUserList.size());

        // 统计前20个人
        Set<String> newSet = onlineUserList.stream().limit(20).collect(Collectors.toSet());
        onlineUserStat.setTotalUserNames(newSet);

        return onlineUserStat;
    }

    @Override
    public HomeCompanyInfo getHomeCompanyInfo() {
        HomeCompanyInfo homeCompanyInfo = new HomeCompanyInfo();

        // 获取组织机构数量
        List<HrOrganizationDTO> hrOrganizationDTOS = organizationServiceApi.orgList();
        homeCompanyInfo.setOrganizationNum(hrOrganizationDTOS.size());

        // 获取企业人员总数
        SysUserRequest sysUserRequest = new SysUserRequest();
        List<Long> allUserIdList = userServiceApi.queryAllUserIdList(sysUserRequest);
        homeCompanyInfo.setEnterprisePersonNum(allUserIdList.size());

        // 获取职位总数
        int positionNum = positionServiceApi.PositionNum();
        homeCompanyInfo.setPositionNum(positionNum);

        // 获取当前登录用户
        LoginUser loginUser = LoginContext.me().getLoginUser();

        // 获取组织公司ID
        Long organizationId = loginUser.getOrganizationId();
        List<SysUserOrg> sysUserOrgs = sysUserOrgService.list(Wrappers.<SysUserOrg>lambdaQuery().eq(SysUserOrg::getOrgId, organizationId));
        homeCompanyInfo.setCurrentCompanyPersonNum(sysUserOrgs.size());

        // 设置公司部门数
        int sectionNum = 0;
        for (HrOrganizationDTO hrOrganizationDTO : hrOrganizationDTOS) {
            String[] orgPids = hrOrganizationDTO.getOrgPids().split(",");
            for (String orgPid : orgPids) {
                if (organizationId.toString().equals(orgPid)) {
                    sectionNum++;
                }
            }
        }
        homeCompanyInfo.setCurrentDeptNum(sectionNum);

        return homeCompanyInfo;
    }

    @Override
    public List<SysMenu> getCommonFunctions() {
        List<SysStatisticsCount> sysStatisticsCounts = sysStatisticsCountService.list(Wrappers.<SysStatisticsCount>lambdaQuery().orderByDesc(SysStatisticsCount::getStatCount));
        List<Long> statUrlIds = sysStatisticsCounts.stream().map(SysStatisticsCount::getStatUrlId).collect(Collectors.toList());

        // 菜单ID集合
        List<String> statMenuIds = new ArrayList<>();
        for (Long statUrlId : statUrlIds) {
            SysStatisticsUrl sysStatisticsUrl = sysStatisticsUrlService.getOne(Wrappers.<SysStatisticsUrl>lambdaQuery().eq(SysStatisticsUrl::getStatUrl, statUrlId));
            String statMenuId = sysStatisticsUrl.getStatMenuId();
            statMenuIds.add(statMenuId);
        }

        List<SysMenu> sysMenuList = new ArrayList<>();
        for (String statMenuId : statMenuIds) {
            SysMenu sysMenu = sysMenuService.getOne(Wrappers.<SysMenu>lambdaQuery().eq(SysMenu::getMenuId, statMenuId));
            sysMenuList.add(sysMenu);
        }
        return sysMenuList;
    }

    @Override
    public void saveStatisticsCacheToDb() {
        // key是用户id，value的key是statUrlId，最后的value是次数
        Map<String, Map<Long, Integer>> userRequestStats = requestCountCacheApi.getAllKeyValues();

        ArrayList<SysStatisticsCount> sysStatisticsCounts = new ArrayList<>();
        // 遍历填充数据
        for (String userId : userRequestStats.keySet()) {
            SysStatisticsCount sysStatisticsCount = new SysStatisticsCount();
            sysStatisticsCount.setUserId(Long.valueOf(userId));
            Map<Long, Integer> map = userRequestStats.get(userId);
            for (Long statUrlId : map.keySet()) {
                sysStatisticsCount.setStatUrlId(statUrlId);
                sysStatisticsCount.setStatCount(map.get(statUrlId));
            }

            // 存放到集合中
            sysStatisticsCounts.add(sysStatisticsCount);
        }

        // 批量存入数据库
        this.sysStatisticsCountService.saveBatch(sysStatisticsCounts);
    }

}
