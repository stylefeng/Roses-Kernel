package cn.stylefeng.roses.kernel.system.modular.organization.factory;

import cn.stylefeng.roses.kernel.rule.tree.factory.node.DefaultTreeNode;
import cn.stylefeng.roses.kernel.system.modular.organization.entity.HrOrganization;

/**
 * Antdv的组织机构树组装
 *
 * @author fengshuonan
 * @date 2021/2/8 20:32
 */
public class AntdvOrganizationFactory {

    /**
     * 实体转化为树节点
     *
     * @author fengshuonan
     * @date 2021/2/8 20:35
     */
    public static DefaultTreeNode parseTreeNode(HrOrganization organizationList) {
        DefaultTreeNode orgTreeNode = new DefaultTreeNode();
        orgTreeNode.setId(String.valueOf(organizationList.getOrgId()));
        orgTreeNode.setPId(String.valueOf(organizationList.getOrgParentId()));
        orgTreeNode.setName(organizationList.getOrgName());
        orgTreeNode.setSort(organizationList.getOrgSort());
        return orgTreeNode;
    }

}
