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
package cn.stylefeng.roses.kernel.system.api.pojo.menu.antd;

import cn.stylefeng.roses.kernel.rule.annotation.ChineseDescription;
import cn.stylefeng.roses.kernel.rule.tree.factory.base.AbstractTreeNode;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 菜单树节点，用在新增和修改菜单，下拉选父级时候
 *
 * @author fengshuonan
 * @date 2020/4/5 12:03
 */
@Data
public class AntdMenuSelectTreeNode implements AbstractTreeNode<AntdMenuSelectTreeNode> {

    /**
     * 主键
     */
    @ChineseDescription("主键")
    private Long id;

    /**
     * 父id
     */
    @ChineseDescription("父id")
    private Long parentId;

    /**
     * 名称
     */
    @ChineseDescription("名称")
    private String title;

    /**
     * 值
     */
    @ChineseDescription("值")
    private String value;

    /**
     * 排序，越小优先级越高
     */
    @ChineseDescription("排序，越小优先级越高")
    private BigDecimal weight;

    /**
     * 子节点
     */
    @ChineseDescription("子节点")
    private List<AntdMenuSelectTreeNode> children;

    @Override
    public String getNodeId() {
        return id.toString();
    }

    @Override
    public String getNodeParentId() {
        return this.parentId.toString();
    }

    @Override
    public void setChildrenNodes(List<AntdMenuSelectTreeNode> childrenNodes) {
        this.children = childrenNodes;
    }
}
