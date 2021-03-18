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
package cn.stylefeng.roses.kernel.rule.tree.ztree;

import cn.stylefeng.roses.kernel.rule.constants.TreeConstants;
import cn.stylefeng.roses.kernel.rule.tree.factory.base.AbstractTreeNode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * jquery zTree 插件的节点封装
 *
 * @author fengshuonan
 * @date 2021/1/6 21:47
 */
@ToString
@EqualsAndHashCode
public class ZTreeNode implements AbstractTreeNode {

    /**
     * 节点id
     */
    @Getter
    @Setter
    private Long id;

    /**
     * 父节点id
     */
    private Long pId;

    /**
     * 节点名称
     */
    @Getter
    @Setter
    private String name;

    /**
     * 是否打开节点
     */
    @Getter
    @Setter
    private Boolean open;

    /**
     * 是否被选中
     */
    @Getter
    @Setter
    private Boolean checked;

    /**
     * 节点图标  single or group
     */
    @Getter
    @Setter
    private String iconSkin;

    /**
     * 子节点集合
     */
    @Getter
    @Setter
    private List children;

    /**
     * 创建ztree的父级节点
     *
     * @author fengshuonan
     * @date 2021/1/6 21:47
     */
    public static ZTreeNode createParent() {
        ZTreeNode zTreeNode = new ZTreeNode();
        zTreeNode.setChecked(true);
        zTreeNode.setId(TreeConstants.DEFAULT_PARENT_ID);
        zTreeNode.setName("顶级");
        zTreeNode.setOpen(true);
        zTreeNode.setpId(TreeConstants.VIRTUAL_ROOT_PARENT_ID);
        return zTreeNode;
    }


    @Override
    public String getNodeId() {
        return id.toString();
    }

    @Override
    public String getNodeParentId() {
        return pId.toString();
    }

    @Override
    public void setChildrenNodes(List childrenNodes) {
        this.children = childrenNodes;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public Long getpId() {
        return pId;
    }
}
