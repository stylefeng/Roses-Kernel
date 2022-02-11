package cn.stylefeng.roses.kernel.system.modular.home.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kernel.auth.api.context.LoginContext;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.LoginUser;
import cn.stylefeng.roses.kernel.cache.api.CacheOperatorApi;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.log.api.LogManagerApi;
import cn.stylefeng.roses.kernel.log.api.pojo.manage.LogManagerRequest;
import cn.stylefeng.roses.kernel.log.api.pojo.record.LogRecordDTO;
import cn.stylefeng.roses.kernel.system.api.HomePageServiceApi;
import cn.stylefeng.roses.kernel.system.api.PositionServiceApi;
import cn.stylefeng.roses.kernel.system.api.UserServiceApi;
import cn.stylefeng.roses.kernel.system.api.pojo.home.HomeCompanyInfo;
import cn.stylefeng.roses.kernel.system.api.pojo.user.OnlineUserDTO;
import cn.stylefeng.roses.kernel.system.api.pojo.user.request.OnlineUserRequest;
import cn.stylefeng.roses.kernel.system.api.pojo.user.request.SysUserRequest;
import cn.stylefeng.roses.kernel.system.modular.home.entity.SysStatisticsCount;
import cn.stylefeng.roses.kernel.system.modular.home.entity.SysStatisticsUrl;
import cn.stylefeng.roses.kernel.system.modular.home.service.HomePageService;
import cn.stylefeng.roses.kernel.system.modular.home.service.SysStatisticsCountService;
import cn.stylefeng.roses.kernel.system.modular.home.service.SysStatisticsUrlService;
import cn.stylefeng.roses.kernel.system.modular.menu.entity.SysMenu;
import cn.stylefeng.roses.kernel.system.modular.menu.service.SysMenuService;
import cn.stylefeng.roses.kernel.system.modular.organization.entity.HrOrganization;
import cn.stylefeng.roses.kernel.system.modular.organization.service.HrOrganizationService;
import cn.stylefeng.roses.kernel.system.modular.statistic.pojo.OnlineUserStat;
import cn.stylefeng.roses.kernel.system.modular.user.entity.SysUserOrg;
import cn.stylefeng.roses.kernel.system.modular.user.service.SysUserOrgService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static cn.stylefeng.roses.kernel.rule.constants.SymbolConstant.LEFT_SQUARE_BRACKETS;
import static cn.stylefeng.roses.kernel.rule.constants.SymbolConstant.RIGHT_SQUARE_BRACKETS;

/**
 * 首页服务实现类
 *
 * @author fengshuonan
 * @date 2022/2/11 20:41
 */
@Service
public class HomePageServiceImpl implements HomePageService, HomePageServiceApi {

    @Resource
    private LogManagerApi logManagerApi;

    @Resource
    private UserServiceApi userServiceApi;

    @Resource
    private HrOrganizationService hrOrganizationService;

    @Resource
    private PositionServiceApi positionServiceApi;

    @Resource
    private SysUserOrgService sysUserOrgService;

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

        // 同一用户多个会话去重
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

        // 获取组织机构总数量
        homeCompanyInfo.setOrganizationNum(hrOrganizationService.count());

        // 获取企业人员总数量
        SysUserRequest sysUserRequest = new SysUserRequest();
        List<Long> allUserIdList = userServiceApi.queryAllUserIdList(sysUserRequest);
        homeCompanyInfo.setEnterprisePersonNum(allUserIdList.size());

        // 获取所有职位总数
        int positionNum = positionServiceApi.positionNum();
        homeCompanyInfo.setPositionNum(positionNum);

        // 获取当前登录人的组织机构id
        LoginUser loginUser = LoginContext.me().getLoginUser();
        Long organizationId = loginUser.getOrganizationId();

        // 获取当前公司的所有子公司数量(含当前公司)
        LambdaQueryWrapper<HrOrganization> wrapper = Wrappers.lambdaQuery(HrOrganization.class)
                .like(HrOrganization::getOrgPids, LEFT_SQUARE_BRACKETS + organizationId + RIGHT_SQUARE_BRACKETS)
                .or()
                .eq(HrOrganization::getOrgId, organizationId)
                .select(HrOrganization::getOrgId);
        List<HrOrganization> organizations = hrOrganizationService.list(wrapper);
        homeCompanyInfo.setCurrentDeptNum(organizations.size());

        // 设置当前所属机构和所有子机构的人数
        List<Long> orgIds = organizations.stream().map(HrOrganization::getOrgId).collect(Collectors.toList());
        int currentOrgPersonNum = sysUserOrgService.count(Wrappers.lambdaQuery(SysUserOrg.class).in(SysUserOrg::getOrgId, orgIds));
        homeCompanyInfo.setCurrentCompanyPersonNum(currentOrgPersonNum);

        return homeCompanyInfo;
    }

    @Override
    public List<SysMenu> getCommonFunctions() {
        List<SysStatisticsCount> sysStatisticsCounts = sysStatisticsCountService.list(Wrappers.<SysStatisticsCount>lambdaQuery().orderByDesc(SysStatisticsCount::getStatCount));
        List<Long> statUrlIds = sysStatisticsCounts.stream().map(SysStatisticsCount::getStatUrlId).collect(Collectors.toList());

        // 菜单ID集合
        List<String> statMenuIds = new ArrayList<>();
        for (Long statUrlId : statUrlIds) {
            SysStatisticsUrl sysStatisticsUrl = sysStatisticsUrlService.getById(statUrlId);
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

        List<SysStatisticsCount> sysStatisticsCountList = new ArrayList<>();
        // 将数据存入DB
        for (String userId : userRequestStats.keySet()) {
            SysStatisticsCount sysStatisticsCount = new SysStatisticsCount();
            sysStatisticsCount.setUserId(Long.valueOf(userId));
            Map<Long, Integer> map = userRequestStats.get(userId);
            for (Long statUrlId : map.keySet()) {
                SysStatisticsCount statisticsCount = sysStatisticsCountService.getOne(Wrappers.<SysStatisticsCount>lambdaQuery().eq(SysStatisticsCount::getStatUrlId, statUrlId));
                // 判断是否已存在该记录，如果存在更新次数，不存在添加记录
                if (ObjectUtil.isNotNull(statisticsCount)) {
                    sysStatisticsCount.setStatCountId(statisticsCount.getStatCountId());
                }
                sysStatisticsCount.setStatUrlId(statUrlId);
                sysStatisticsCount.setStatCount(map.get(statUrlId));
            }

            // 存放到集合中
            sysStatisticsCountList.add(sysStatisticsCount);
        }

        // 更新DB
        sysStatisticsCountService.saveOrUpdateBatch(sysStatisticsCountList);
    }
}
