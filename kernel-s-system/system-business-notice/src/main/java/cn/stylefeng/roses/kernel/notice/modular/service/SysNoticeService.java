package cn.stylefeng.roses.kernel.notice.modular.service;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.notice.modular.entity.SysNotice;
import cn.stylefeng.roses.kernel.system.NoticeServiceApi;
import cn.stylefeng.roses.kernel.system.pojo.notice.SysNoticeRequest;
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
     * @param SysNoticeParam 添加参数
     * @author liuhanqing
     * @date 2021/1/9 14:57
     */
    void add(SysNoticeRequest SysNoticeParam);

    /**
     * 编辑系统应用
     *
     * @param SysNoticeParam 编辑参数
     * @author liuhanqing
     * @date 2021/1/9 14:58
     */
    void edit(SysNoticeRequest SysNoticeParam);


    /**
     * 删除系统应用
     *
     * @param SysNoticeParam 删除参数
     * @author liuhanqing
     * @date 2021/1/9 14:57
     */
    void delete(SysNoticeRequest SysNoticeParam);

    /**
     * 查看系统应用
     *
     * @param SysNoticeParam 查看参数
     * @return 系统应用
     * @author liuhanqing
     * @date 2021/1/9 14:56
     */
    SysNotice detail(SysNoticeRequest SysNoticeParam);

    /**
     * 查询系统应用
     *
     * @param SysNoticeParam 查询参数
     * @return 查询分页结果
     * @author liuhanqing
     * @date 2021/1/9 14:56
     */
    PageResult<SysNotice> page(SysNoticeRequest SysNoticeParam);

    /**
     * 系统应用列表
     *
     * @param SysNoticeParam 查询参数
     * @return 系统应用列表
     * @author liuhanqing
     * @date 2021/1/9 14:56
     */
    List<SysNotice> list(SysNoticeRequest SysNoticeParam);


}