package cn.stylefeng.roses.kernel.expand.modular.modular.service;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.expand.modular.api.ExpandApi;
import cn.stylefeng.roses.kernel.expand.modular.modular.entity.SysExpand;
import cn.stylefeng.roses.kernel.expand.modular.modular.entity.SysExpandData;
import cn.stylefeng.roses.kernel.expand.modular.modular.pojo.request.SysExpandRequest;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 业务拓展 服务类
 *
 * @author fengshuonan
 * @date 2022/03/29 23:47
 */
public interface SysExpandService extends IService<SysExpand>, ExpandApi {

    /**
     * 新增
     *
     * @param sysExpandRequest 请求参数
     * @author fengshuonan
     * @date 2022/03/29 23:47
     */
    void add(SysExpandRequest sysExpandRequest);

    /**
     * 删除
     *
     * @param sysExpandRequest 请求参数
     * @author fengshuonan
     * @date 2022/03/29 23:47
     */
    void del(SysExpandRequest sysExpandRequest);

    /**
     * 编辑
     *
     * @param sysExpandRequest 请求参数
     * @author fengshuonan
     * @date 2022/03/29 23:47
     */
    void edit(SysExpandRequest sysExpandRequest);

    /**
     * 查询详情
     *
     * @param sysExpandRequest 请求参数
     * @author fengshuonan
     * @date 2022/03/29 23:47
     */
    SysExpand detail(SysExpandRequest sysExpandRequest);

    /**
     * 获取列表
     *
     * @param sysExpandRequest 请求参数
     * @return List<SysExpand>   返回结果
     * @author fengshuonan
     * @date 2022/03/29 23:47
     */
    List<SysExpand> findList(SysExpandRequest sysExpandRequest);

    /**
     * 获取列表（带分页）
     *
     * @param sysExpandRequest 请求参数
     * @return PageResult<SysExpand>   返回结果
     * @author fengshuonan
     * @date 2022/03/29 23:47
     */
    PageResult<SysExpand> findPage(SysExpandRequest sysExpandRequest);

    /**
     * 修改业务状态
     *
     * @author fengshuonan
     * @date 2022/3/30 10:37
     */
    void updateStatus(SysExpandRequest sysExpandRequest);

    /**
     * 获取业务元数据信息
     *
     * @author fengshuonan
     * @date 2022/3/31 15:26
     */
    SysExpandData getByExpandCode(SysExpandRequest sysExpandRequest);
}
