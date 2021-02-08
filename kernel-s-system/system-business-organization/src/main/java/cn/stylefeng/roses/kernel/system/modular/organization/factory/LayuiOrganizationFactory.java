package cn.stylefeng.roses.kernel.system.modular.organization.factory;

import cn.stylefeng.roses.kernel.rule.tree.ztree.ZTreeNode;
import cn.stylefeng.roses.kernel.system.modular.organization.entity.HrOrganization;
import cn.stylefeng.roses.kernel.system.pojo.organization.layui.LayuiOrganizationTreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * 组织机构实体转化
 *
 * @author chenjinlong
 * @date 2021/1/6 21:03
 */
public class LayuiOrganizationFactory {

    /**
     * 实体转换
     *
     * @param hrOrganization 机构信息
     * @return LayuiOrganizationTreeNode layui树实体对象
     * @author chenjinlong
     * @date 2021/1/5 21:07
     */
    public static LayuiOrganizationTreeNode parseOrganizationTreeNode(HrOrganization hrOrganization) {
        LayuiOrganizationTreeNode treeNode = new LayuiOrganizationTreeNode();
        treeNode.setId(hrOrganization.getOrgId());
        treeNode.setParentId(hrOrganization.getOrgParentId());
        treeNode.setTitle(hrOrganization.getOrgName());
        return treeNode;
    }

    /**
     * 实体转zTree形式
     *
     * @author fengshuonan
     * @date 2021/1/9 18:43
     */
    public static List<ZTreeNode> parseZTree(List<HrOrganization> organizationList) {
        ArrayList<ZTreeNode> zTreeNodes = new ArrayList<>();
        for (HrOrganization hrOrganization : organizationList) {
            ZTreeNode zTreeNode = new ZTreeNode();
            zTreeNode.setId(hrOrganization.getOrgId());
            zTreeNode.setpId(hrOrganization.getOrgParentId());
            zTreeNode.setName(hrOrganization.getOrgName());
            zTreeNode.setOpen(true);
            zTreeNodes.add(zTreeNode);
        }
        return zTreeNodes;
    }

}
