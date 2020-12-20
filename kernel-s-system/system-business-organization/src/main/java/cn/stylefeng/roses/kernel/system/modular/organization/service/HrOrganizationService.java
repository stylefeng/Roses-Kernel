package cn.stylefeng.roses.kernel.system.modular.organization.service;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.rule.pojo.tree.DefaultTreeNode;
import cn.stylefeng.roses.kernel.system.modular.organization.entity.HrOrganization;
import cn.stylefeng.roses.kernel.system.pojo.organization.HrOrganizationRequest;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
 * 系统组织机构服务
 *
 * @author fengshuonan
 * @date 2020/11/04 11:05
 */
public interface HrOrganizationService extends IService<HrOrganization> {

    /**
     * 添加系统组织机构
     *
     * @param hrOrganizationRequest 组织机构请求参数
     * @author fengshuonan
     * @date 2020/11/04 11:05
     */
    void add(HrOrganizationRequest hrOrganizationRequest);

    /**
     * 编辑系统组织机构
     *
     * @param hrOrganizationRequest 组织机构请求参数
     * @author fengshuonan
     * @date 2020/11/04 11:05
     */
    void edit(HrOrganizationRequest hrOrganizationRequest);

    /**
     * 删除系统组织机构
     *
     * @param hrOrganizationRequest 组织机构请求参数
     * @author fengshuonan
     * @date 2020/11/04 11:05
     */
    void delete(HrOrganizationRequest hrOrganizationRequest);

    /**
     * 修改组织机构状态
     *
     * @param hrOrganizationRequest 请求参数
     * @author fengshuonan
     * @date 2020/11/18 22:38
     */
    void updateStatus(HrOrganizationRequest hrOrganizationRequest);

    /**
     * 查看详情系统组织机构
     *
     * @param hrOrganizationRequest 组织机构请求参数
     * @return 组织机构详情
     * @author fengshuonan
     * @date 2020/11/04 11:05
     */
    HrOrganization detail(HrOrganizationRequest hrOrganizationRequest);

    /**
     * 分页查询系统组织机构
     *
     * @param hrOrganizationRequest 组织机构请求参数
     * @return 组织机构详情分页列表
     * @author fengshuonan
     * @date 2020/11/04 11:05
     */
    PageResult<HrOrganization> page(HrOrganizationRequest hrOrganizationRequest);

    /**
     * 查询所有系统组织机构
     *
     * @param hrOrganizationRequest 组织机构请求参数
     * @return 组织机构详情列表
     * @author fengshuonan
     * @date 2020/11/04 11:05
     */
    List<HrOrganization> list(HrOrganizationRequest hrOrganizationRequest);

    /**
     * 获取组织架构树
     *
     * @param hrOrganizationRequest 查询参数
     * @return 系统组织机构树
     * @author fengshuonan
     * @date 2020/11/6 13:41
     */
    List<DefaultTreeNode> tree(HrOrganizationRequest hrOrganizationRequest);

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