package cn.stylefeng.roses.kernel.system.modular.organization.service;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.rule.pojo.tree.DefaultTreeNode;
import cn.stylefeng.roses.kernel.system.modular.organization.entity.HrOrganization;
import cn.stylefeng.roses.kernel.system.pojo.organization.SysOrganizationRequest;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
 * 系统组织机构服务
 *
 * @author fengshuonan
 * @date 2020/11/04 11:05
 */
public interface SysOrganizationService extends IService<HrOrganization> {

    /**
     * 添加系统组织机构
     *
     * @param sysOrganizationRequest 组织机构请求参数
     * @author fengshuonan
     * @date 2020/11/04 11:05
     */
    void add(SysOrganizationRequest sysOrganizationRequest);

    /**
     * 编辑系统组织机构
     *
     * @param sysOrganizationRequest 组织机构请求参数
     * @author fengshuonan
     * @date 2020/11/04 11:05
     */
    void edit(SysOrganizationRequest sysOrganizationRequest);

    /**
     * 删除系统组织机构
     *
     * @param sysOrganizationRequest 组织机构请求参数
     * @author fengshuonan
     * @date 2020/11/04 11:05
     */
    void delete(SysOrganizationRequest sysOrganizationRequest);

    /**
     * 修改组织机构状态
     *
     * @param sysOrganizationRequest 请求参数
     * @author fengshuonan
     * @date 2020/11/18 22:38
     */
    void updateStatus(SysOrganizationRequest sysOrganizationRequest);

    /**
     * 查看详情系统组织机构
     *
     * @param sysOrganizationRequest 组织机构请求参数
     * @return 组织机构详情
     * @author fengshuonan
     * @date 2020/11/04 11:05
     */
    HrOrganization detail(SysOrganizationRequest sysOrganizationRequest);

    /**
     * 分页查询系统组织机构
     *
     * @param sysOrganizationRequest 组织机构请求参数
     * @return 组织机构详情分页列表
     * @author fengshuonan
     * @date 2020/11/04 11:05
     */
    PageResult<HrOrganization> page(SysOrganizationRequest sysOrganizationRequest);

    /**
     * 查询所有系统组织机构
     *
     * @param sysOrganizationRequest 组织机构请求参数
     * @return 组织机构详情列表
     * @author fengshuonan
     * @date 2020/11/04 11:05
     */
    List<HrOrganization> list(SysOrganizationRequest sysOrganizationRequest);

    /**
     * 获取组织架构树
     *
     * @param sysOrganizationRequest 查询参数
     * @return 系统组织机构树
     * @author fengshuonan
     * @date 2020/11/6 13:41
     */
    List<DefaultTreeNode> tree(SysOrganizationRequest sysOrganizationRequest);

    /**
     * 查询所有参数组织架构id集合的所有层级的父id，包含父级的父级等
     *
     * @param organizationIds 组织架构id集合
     * @return 被查询参数id集合的所有层级父级id，包含他们本身
     * @author fengshuonan
     * @date 2020/11/6 14:24
     */
    Set<Long> findAllLevelParentIdsByOrganizations(Set<Long> organizationIds);

}