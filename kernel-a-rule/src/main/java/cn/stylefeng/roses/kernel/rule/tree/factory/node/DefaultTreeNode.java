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
package cn.stylefeng.roses.kernel.rule.tree.factory.node;

import cn.stylefeng.roses.kernel.rule.tree.factory.base.AbstractTreeNode;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 默认树节点的封装
 * <p>
 * 默认的根节点id是-1，名称是根节点
 *
 * @author fengshuonan
 * @date 2020/10/15 14:39
 */
@Data
public class DefaultTreeNode implements AbstractTreeNode<DefaultTreeNode> {

    /**
     * 节点id
     */
    private String id;

    /**
     * 父节点id
     */
    private String pId;

    /**
     * 节点名称
     */
    private String name;

    /**
     * 是否打开节点
     */
    private Boolean open;

    /**
     * 是否被选中
     */
    private Boolean checked = false;

    /**
     * 排序，越小越靠前
     */
    private BigDecimal sort;

    /**
     * 子节点
     */
    private List<DefaultTreeNode> children;

    @Override
    public String getNodeId() {
        return id;
    }

    @Override
    public String getNodeParentId() {
        return pId;
    }

    @Override
    public void setChildrenNodes(List<DefaultTreeNode> childrenNodes) {
        this.children = childrenNodes;
    }

}
