package cn.stylefeng.roses.kernel.expand.modular.modular.service;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.expand.modular.modular.entity.SysExpandData;
import cn.stylefeng.roses.kernel.expand.modular.modular.pojo.request.SysExpandDataRequest;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 业务拓展-具体数据 服务类
 *
 * @author fengshuonan
 * @date 2022/03/29 23:47
 */
public interface SysExpandDataService extends IService<SysExpandData> {

    /**
     * 新增
     *
     * @param sysExpandDataRequest 请求参数
     * @author fengshuonan
     * @date 2022/03/29 23:47
     */
    void add(SysExpandDataRequest sysExpandDataRequest);

    /**
     * 删除
     *
     * @param sysExpandDataRequest 请求参数
     * @author fengshuonan
     * @date 2022/03/29 23:47
     */
    void del(SysExpandDataRequest sysExpandDataRequest);

    /**
     * 编辑
     *
     * @param sysExpandDataRequest 请求参数
     * @author fengshuonan
     * @date 2022/03/29 23:47
     */
    void edit(SysExpandDataRequest sysExpandDataRequest);

    /**
     * 查询详情
     *
     * @param sysExpandDataRequest 请求参数
     * @author fengshuonan
     * @date 2022/03/29 23:47
     */
    SysExpandData detail(SysExpandDataRequest sysExpandDataRequest);

    /**
     * 查询详情
     *
     * @author fengshuonan
     * @date 2022/03/29 23:47
     */
    SysExpandData detailByPrimaryFieldValue(Long primaryFieldValue);

    /**
     * 获取列表
     *
     * @param sysExpandDataRequest 请求参数
     * @return List<SysExpandData>   返回结果
     * @author fengshuonan
     * @date 2022/03/29 23:47
     */
    List<SysExpandData> findList(SysExpandDataRequest sysExpandDataRequest);

    /**
     * 获取列表（带分页）
     *
     * @param sysExpandDataRequest 请求参数
     * @return PageResult<SysExpandData>   返回结果
     * @author fengshuonan
     * @date 2022/03/29 23:47
     */
    PageResult<SysExpandData> findPage(SysExpandDataRequest sysExpandDataRequest);

}
