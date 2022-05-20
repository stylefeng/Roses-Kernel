/*
 * Copyright [2020-2030] [https://www.stylefeng.cn]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Guns采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Guns源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/stylefeng/guns
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/stylefeng/guns
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */
package cn.stylefeng.roses.kernel.system.modular.organization.factory;

import cn.stylefeng.roses.kernel.rule.tree.ztree.ZTreeNode;
import cn.stylefeng.roses.kernel.system.api.pojo.organization.OrganizationTreeNode;
import cn.stylefeng.roses.kernel.system.modular.organization.entity.HrOrganization;

import java.util.ArrayList;
import java.util.List;

/**
 * 组织机构实体转化
 *
 * @author chenjinlong
 * @date 2021/1/6 21:03
 */
public class OrganizationFactory {

    /**
     * 实体转换
     *
     * @param hrOrganization 机构信息
     * @return LayuiOrganizationTreeNode layui树实体对象
     * @author chenjinlong
     * @date 2021/1/5 21:07
     */
    public static OrganizationTreeNode parseOrganizationTreeNode(HrOrganization hrOrganization) {
        OrganizationTreeNode treeNode = new OrganizationTreeNode();
        treeNode.setId(hrOrganization.getOrgId());
        treeNode.setParentId(hrOrganization.getOrgParentId());
        treeNode.setTitle(hrOrganization.getOrgName());

        treeNode.setOrgName(hrOrganization.getOrgName());
        treeNode.setOrgCode(hrOrganization.getOrgCode());
        treeNode.setStatusFlag(hrOrganization.getStatusFlag());
        treeNode.setOrgSort(hrOrganization.getOrgSort());
        treeNode.setOrgRemark(hrOrganization.getOrgRemark());
        treeNode.setOrgType(hrOrganization.getOrgType());

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
