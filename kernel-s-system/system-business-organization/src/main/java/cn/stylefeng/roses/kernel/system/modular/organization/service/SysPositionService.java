package cn.stylefeng.roses.kernel.system.modular.organization.service;

import cn.stylefeng.roses.kernel.system.pojo.organization.SysPositionRequest;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.system.modular.organization.entity.SysPosition;

import java.util.List;

/**
 * 职位信息服务
 *
 * @author fengshuonan
 * @date 2020/11/04 11:07
 */
public interface SysPositionService extends IService<SysPosition> {

    /**
     * 添加职位
     *
     * @param sysPositionRequest 请求参数
     * @author fengshuonan
     * @date 2020/11/04 11:07
     */
    void add(SysPositionRequest sysPositionRequest);

    /**
     * 编辑职位
     *
     * @param sysPositionRequest 请求参数
     * @author fengshuonan
     * @date 2020/11/04 11:07
     */
    void edit(SysPositionRequest sysPositionRequest);

    /**
     * 删除职位
     *
     * @param sysPositionRequest 请求参数
     * @author fengshuonan
     * @date 2020/11/04 11:07
     */
    void delete(SysPositionRequest sysPositionRequest);

    /**
     * 更新装填
     *
     * @param sysPositionRequest 请求参数
     * @author fengshuonan
     * @date 2020/11/18 23:00
     */
    void updateStatus(SysPositionRequest sysPositionRequest);

    /**
     * 查看详情职位
     *
     * @param sysPositionRequest 请求参数
     * @return 职位详情
     * @author fengshuonan
     * @date 2020/11/04 11:07
     */
    SysPosition detail(SysPositionRequest sysPositionRequest);

    /**
     * 分页查询职位
     *
     * @param sysPositionRequest 请求参数
     * @return 职位详情分页列表
     * @author fengshuonan
     * @date 2020/11/04 11:07
     */
    PageResult<SysPosition> page(SysPositionRequest sysPositionRequest);

    /**
     * 查询所有职位
     *
     * @param sysPositionRequest 请求参数
     * @return 职位详情列表
     * @author fengshuonan
     * @date 2020/11/04 11:07
     */
    List<SysPosition> list(SysPositionRequest sysPositionRequest);

    /**
     * 通过职位id列表，获取对应的名称列表
     *
     * @param positionIds 职位id列表
     * @return 职位名称列表
     * @author fengshuonan
     * @date 2020/11/19 23:22
     */
    List<String> getPositionNamesByPositionIds(List<Long> positionIds);

}