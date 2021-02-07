package cn.stylefeng.roses.kernel.system.modular.organization.service;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.rule.tree.factory.node.DefaultTreeNode;
import cn.stylefeng.roses.kernel.rule.tree.ztree.ZTreeNode;
import cn.stylefeng.roses.kernel.system.OrganizationServiceApi;
import cn.stylefeng.roses.kernel.system.modular.organization.entity.HrOrganization;
import cn.stylefeng.roses.kernel.system.pojo.organization.HrOrganizationRequest;
import cn.stylefeng.roses.kernel.system.pojo.organization.layui.LayuiOrganizationTreeNode;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
 * 系统组织机构服务
 *
 * @author fengshuonan
 * @date 2020/11/04 11:05
 */
public interface HrOrganizationService extends IService<HrOrganization>, OrganizationServiceApi {

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
    void del(HrOrganizationRequest hrOrganizationRequest);

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
    PageResult<HrOrganization> findPage(HrOrganizationRequest hrOrganizationRequest);

    /**
     * 查询所有系统组织机构
     *
     * @param hrOrganizationRequest 组织机构请求参数
     * @return 组织机构详情列表
     * @author fengshuonan
     * @date 2020/11/04 11:05
     */
    List<HrOrganization> findList(HrOrganizationRequest hrOrganizationRequest);


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
     * 获取组织架构树
     *
     * @param hrOrganizationRequest 查询参数
     * @return 系统组织机构树
     * @author chenjinlong
     * @date 2020/11/6 13:41
     */
    List<LayuiOrganizationTreeNode> treeLayui(HrOrganizationRequest hrOrganizationRequest);

    /**
     * 查询所有参数组织架构id集合的所有层级的父id，包含父级的父级等
     *
     * @param organizationIds 组织架构id集合
     * @return 被查询参数id集合的所有层级父级id，包含他们本身
     * @author fengshuonan
     * @date 2020/11/6 14:24
     */
    Set<Long> findAllLevelParentIdsByOrganizations(Set<Long> organizationIds);

    /**
     * 获取ztree形式的组织机构树（用于角色配置数据范围类型，并且数据范围类型是指定组织机构时）（layui版本）
     *
     * @param hrOrganizationRequest 请求参数
     * @param buildTree             是否构建成树结构的节点，true-带树结构，false-不带
     * @return ztree形式的组织机构树
     * @author fengshuonan
     * @date 2021/1/9 18:40
     */
    List<ZTreeNode> orgZTree(HrOrganizationRequest hrOrganizationRequest, boolean buildTree);

}
