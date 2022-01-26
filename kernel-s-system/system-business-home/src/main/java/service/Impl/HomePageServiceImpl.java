package service.Impl;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.log.api.LogManagerApi;
import cn.stylefeng.roses.kernel.log.api.pojo.manage.LogManagerRequest;
import cn.stylefeng.roses.kernel.log.api.pojo.record.LogRecordDTO;
import cn.stylefeng.roses.kernel.system.api.HomePageServiceApi;
import cn.stylefeng.roses.kernel.system.api.UserServiceApi;
import cn.stylefeng.roses.kernel.system.api.pojo.home.HomeCompanyInfo;
import cn.stylefeng.roses.kernel.system.api.pojo.user.OnlineUserDTO;
import cn.stylefeng.roses.kernel.system.api.pojo.user.request.OnlineUserRequest;
import org.springframework.stereotype.Service;
import service.HomePageService;

import javax.annotation.Resource;
import java.util.List;

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

    @Override
    public List<LogRecordDTO> getDynamicList(LogManagerRequest logManagerRequest) {
        return logManagerApi.findList(logManagerRequest);
    }

    @Override
    public PageResult<LogRecordDTO> getDynamicPage(LogManagerRequest logManagerRequest) {
        return logManagerApi.findPage(logManagerRequest);
    }

    @Override
    public List<OnlineUserDTO> getOnlineUserList(OnlineUserRequest onlineUserRequest) {
        return userServiceApi.onlineUserList(onlineUserRequest);
    }

    @Override
    public HomeCompanyInfo getHomeCompanyInfo() {
        // TODO 未完成
        return null;
    }
}
