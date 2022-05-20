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
package cn.stylefeng.roses.kernel.system.api.pojo.organization;

import cn.stylefeng.roses.kernel.rule.annotation.ChineseDescription;
import cn.stylefeng.roses.kernel.rule.tree.factory.base.AbstractTreeNode;
import cn.stylefeng.roses.kernel.rule.tree.xmtree.base.AbstractXmSelectNode;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 组织机构树节点
 *
 * @author chenjinlong
 * @date 2020/12/27 18:36
 */
@Data
public class OrganizationTreeNode implements AbstractTreeNode<OrganizationTreeNode>, AbstractXmSelectNode {

    /**
     * 父id，一级节点父id是0
     */
    @ChineseDescription("父id，一级节点父id是0")
    private Long parentId;

    /**
     * 节点名称
     */
    @ChineseDescription("节点名称")
    private String title;

    /**
     * 节点值
     */
    @ChineseDescription("节点值")
    private Long id;

    /**
     * 是否展开状态 不展开-false 展开-true
     */
    @ChineseDescription("是否展开状态 不展开-false 展开-true")
    private boolean spread = true;

    /**
     * 是否选中
     */
    @ChineseDescription("是否选中")
    private boolean selected = false;

    /**
     * 组织名称
     */
    @ChineseDescription("组织名称")
    private String orgName;

    /**
     * 组织编码
     */
    @ChineseDescription("组织编码")
    private String orgCode;

    /**
     * 排序
     */
    @ChineseDescription("排序")
    private BigDecimal orgSort;

    /**
     * 状态：1-启用，2-禁用
     */
    @ChineseDescription("状态：1-启用，2-禁用")
    private Integer statusFlag;

    /**
     * 组织机构类型：1-公司，2-部门
     */
    @ChineseDescription("组织机构类型：1-公司，2-部门")
    private Integer orgType;

    /**
     * 组织机构描述
     */
    @ChineseDescription("组织机构描述")
    private String orgRemark;

    /**
     * 子节点的集合
     */
    @ChineseDescription("子节点的集合")
    private List<OrganizationTreeNode> children;

    @Override
    public String getNodeId() {
        return this.id.toString();
    }

    @Override
    public String getNodeParentId() {
        return this.parentId.toString();
    }

    @Override
    public void setChildrenNodes(List<OrganizationTreeNode> childrenNodes) {
        this.children = childrenNodes;
    }

    @Override
    public String getName() {
        return this.title;
    }

    @Override
    public String getValue() {
        return String.valueOf(id);
    }

    @Override
    public Boolean getSelected() {
        return this.selected;
    }

    @Override
    public Boolean getDisabled() {
        return false;
    }

    @Override
    public List getChildren() {
        return this.children;
    }

}
