package service;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.log.api.pojo.manage.LogManagerRequest;
import cn.stylefeng.roses.kernel.log.api.pojo.record.LogRecordDTO;
import cn.stylefeng.roses.kernel.system.api.pojo.home.HomeCompanyInfo;
import cn.stylefeng.roses.kernel.system.api.pojo.user.OnlineUserDTO;
import cn.stylefeng.roses.kernel.system.api.pojo.user.request.OnlineUserRequest;

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
    List<LogRecordDTO> getDynamicList(LogManagerRequest logManagerRequest);

    /**
     * 查询动态列表(分页)
     *
     * @author xixiaowei
     * @date 2022/1/25 9:43
     */
    PageResult<LogRecordDTO> getDynamicPage(LogManagerRequest logManagerRequest);

    /**
     * 获取在线用户列表
     *
     * @author xixiaowei
     * @date 2022/1/25 14:06
     */
    List<OnlineUserDTO> getOnlineUserList(OnlineUserRequest onlineUserRequest);

    /**
     * 获取首页企业和公司信息
     *
     * @author xixiaowei
     * @date 2022/1/25 15:31
     */
    HomeCompanyInfo getHomeCompanyInfo();
}
