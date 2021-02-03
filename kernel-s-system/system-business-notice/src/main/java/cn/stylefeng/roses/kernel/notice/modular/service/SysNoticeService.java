package cn.stylefeng.roses.kernel.notice.modular.service;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.notice.modular.entity.SysNotice;
import cn.stylefeng.roses.kernel.system.NoticeServiceApi;
import cn.stylefeng.roses.kernel.system.pojo.SysNoticeRequest;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 通知服务类
 *
 * @author liuhanqing
 * @date 2021/1/8 19:56
 */
public interface SysNoticeService extends IService<SysNotice>, NoticeServiceApi {

    /**
     * 添加系统应用
     *
     * @param sysNoticeRequest 添加参数
     * @author liuhanqing
     * @date 2021/1/9 14:57
     */
    void add(SysNoticeRequest sysNoticeRequest);

    /**
     * 编辑系统应用
     *
     * @param sysNoticeRequest 编辑参数
     * @author liuhanqing
     * @date 2021/1/9 14:58
     */
    void edit(SysNoticeRequest sysNoticeRequest);


    /**
     * 删除系统应用
     *
     * @param sysNoticeRequest 删除参数
     * @author liuhanqing
     * @date 2021/1/9 14:57
     */
    void del(SysNoticeRequest sysNoticeRequest);

    /**
     * 查看系统应用
     *
     * @param sysNoticeRequest 查看参数
     * @return 系统应用
     * @author liuhanqing
     * @date 2021/1/9 14:56
     */
    SysNotice detail(SysNoticeRequest sysNoticeRequest);

    /**
     * 查询系统应用
     *
     * @param sysNoticeRequest 查询参数
     * @return 查询分页结果
     * @author liuhanqing
     * @date 2021/1/9 14:56
     */
    PageResult<SysNotice> findPage(SysNoticeRequest sysNoticeRequest);

    /**
     * 系统应用列表
     *
     * @param sysNoticeRequest 查询参数
     * @return 系统应用列表
     * @author liuhanqing
     * @date 2021/1/9 14:56
     */
    List<SysNotice> findList(SysNoticeRequest sysNoticeRequest);


}