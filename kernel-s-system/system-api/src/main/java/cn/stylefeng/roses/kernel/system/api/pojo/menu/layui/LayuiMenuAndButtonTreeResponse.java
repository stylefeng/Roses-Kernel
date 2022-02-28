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
package cn.stylefeng.roses.kernel.system.api.pojo.menu.layui;

import cn.stylefeng.roses.kernel.rule.annotation.ChineseDescription;
import cn.stylefeng.roses.kernel.rule.tree.factory.base.AbstractTreeNode;
import lombok.Data;

import java.util.List;

/**
 * 角色分配资源和菜单的树
 *
 * @author majianguo
 * @date 2021/1/9 16:59
 */
@Data
public class LayuiMenuAndButtonTreeResponse implements AbstractTreeNode<LayuiMenuAndButtonTreeResponse> {

    /**
     * 节点ID
     */
    @ChineseDescription("节点ID")
    private Long id;

    /**
     * 节点父ID
     */
    @ChineseDescription("节点父ID")
    private Long pid;

    /**
     * 节点名称
     */
    @ChineseDescription("节点名称")
    private String name;

    /**
     * 是否是菜单(如果是false,则pid是菜单的id)
     */
    @ChineseDescription("是否是菜单(如果是false,则pid是菜单的id)")
    private Boolean menuFlag;

    /**
     * 是否选择(已拥有的是true)
     */
    @ChineseDescription("是否选择(已拥有的是true)")
    private Boolean checked;

    /**
     * 按钮code
     */
    @ChineseDescription("按钮code")
    private String buttonCode;

    /**
     * 子节点集合
     */
    @ChineseDescription("子节点集合")
    private List<LayuiMenuAndButtonTreeResponse> children;

    @Override
    public String getNodeId() {
        return this.id.toString();
    }

    @Override
    public String getNodeParentId() {
        return this.pid.toString();
    }

    @Override
    public void setChildrenNodes(List<LayuiMenuAndButtonTreeResponse> childrenNodes) {
        this.children = childrenNodes;
    }
}
