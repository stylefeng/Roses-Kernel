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
package cn.stylefeng.roses.kernel.dict.modular.pojo;

import cn.stylefeng.roses.kernel.rule.annotation.ChineseDescription;
import cn.stylefeng.roses.kernel.rule.tree.factory.base.AbstractTreeNode;
import lombok.Data;

import java.util.List;

/**
 * 字典详细信息
 * <p>
 * 字典表的实现可以用内存，数据库或redis(具体实现可以根据业务需求自定义)
 *
 * @author fengshuonan
 * @date 2020/10/30 11:05
 */
@Data
public class TreeDictInfo implements AbstractTreeNode<TreeDictInfo> {

    /**
     * 字典id
     */
    @ChineseDescription("字典id")
    private Long dictId;

    /**
     * 字典编码
     */
    @ChineseDescription("字典编码")
    private String dictCode;

    /**
     * 字典名称
     */
    @ChineseDescription("字典名称")
    private String dictName;

    /**
     * 上级字典id
     */
    @ChineseDescription("上级字典id")
    private Long dictParentId;

    /**
     * tree子节点
     */
    @ChineseDescription("tree子节点")
    private List<TreeDictInfo> children;

    @Override
    public String getNodeId() {
        if (this.dictId == null) {
            return null;
        } else {
            return this.dictId.toString();
        }
    }

    @Override
    public String getNodeParentId() {
        if (this.dictParentId == null) {
            return null;
        } else {
            return this.dictParentId.toString();
        }
    }

    @Override
    public void setChildrenNodes(List<TreeDictInfo> linkedList) {
        this.children = linkedList;
    }

}
