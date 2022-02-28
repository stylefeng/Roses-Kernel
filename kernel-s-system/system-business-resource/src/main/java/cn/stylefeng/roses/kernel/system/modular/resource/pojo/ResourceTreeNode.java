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
package cn.stylefeng.roses.kernel.system.modular.resource.pojo;

import cn.stylefeng.roses.kernel.rule.annotation.ChineseDescription;
import cn.stylefeng.roses.kernel.rule.tree.factory.base.AbstractTreeNode;
import lombok.Data;

import java.util.List;

/**
 * 资源树节点的描述
 *
 * @author fengshuonan
 * @date 2020/3/26 14:29
 */
@Data
public class ResourceTreeNode implements AbstractTreeNode<ResourceTreeNode> {

    /**
     * 资源id
     */
    @ChineseDescription("资源id")
    private String code;

    /**
     * 父级资源id
     */
    @ChineseDescription("父级资源id")
    private String parentCode;

    /**
     * 资源名称
     */
    @ChineseDescription("资源名称")
    private String nodeName;

    /**
     * 是否是资源标识
     * <p>
     * true-是资源标识
     * false-虚拟节点，不是一个具体资源
     */
    @ChineseDescription("是否是资源标识")
    private Boolean resourceFlag;

    /**
     * 能否选择
     */
    @ChineseDescription("能否选择")
    private Boolean checked;

    /**
     * 是否是半开状态（一部分选中）
     */
    @ChineseDescription("是否是半开状态（一部分选中）")
    private Boolean indeterminate;

    /**
     * 子节点集合
     */
    @ChineseDescription("子节点集合")
    private List<ResourceTreeNode> children;

    @Override
    public String getNodeId() {
        return this.code;
    }

    @Override
    public String getNodeParentId() {
        return this.parentCode;
    }

    @Override
    public void setChildrenNodes(List<ResourceTreeNode> childrenNodes) {
        this.children = childrenNodes;
    }
}
