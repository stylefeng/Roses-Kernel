package cn.stylefeng.roses.kernel.system.modular.title.service;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.system.api.TitleServiceApi;
import cn.stylefeng.roses.kernel.system.api.pojo.title.SysTitleRequest;
import cn.stylefeng.roses.kernel.system.modular.title.entity.SysTitle;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 标题图片配置服务类
 *
 * @author xixiaowei
 * @date 2021/12/13 17:22
 */
public interface SysTitleService extends IService<SysTitle>, TitleServiceApi {

    /**
     * 添加标题图片配置
     *
     * @param sysTitleRequest 添加参数
     * @author xixiaowei
     * @date 2021/12/13 17:45
     */
    void add(SysTitleRequest sysTitleRequest);

    /**
     * 删除标题图片配置
     *
     * @param sysTitleRequest 删除参数
     * @author xixiaowei
     * @date 2021/12/13 18:39
     */
    void del(SysTitleRequest sysTitleRequest);

    /**
     * 修改标题图片配置
     *
     * @param sysTitleRequest 修改参数
     * @author xixiaowei
     * @date 2021/12/14 9:32
     */
    void edit(SysTitleRequest sysTitleRequest);

    /**
     * 查询单个标题配置信息
     *
     * @param sysTitleRequest 查询参数
     * @author xixiaowei
     * @date 2021/12/14 11:38
     */
    SysTitle detail(SysTitleRequest sysTitleRequest);

    /**
     * 查询标题列表
     * 
     * @param sysTitleRequest 查询参数
     * @return 查询分页结果
     * @author xixiaowei
     * @date 2021/12/14 10:00
     */
    PageResult<SysTitle> findPage(SysTitleRequest sysTitleRequest);

    /**
     * 复制标题
     *
     * @param sysTitleRequest 复制参数
     * @author xixiaowei
     * @date 2021/12/14 10:47
     */
    void copyTitle(SysTitleRequest sysTitleRequest);

    /**
     * 修改启用状态
     *
     * @param sysTitleRequest 修改参数
     * @author xixiaowei
     * @date 2021/12/14 11:32
     */
    void updateStatus(SysTitleRequest sysTitleRequest);
}
