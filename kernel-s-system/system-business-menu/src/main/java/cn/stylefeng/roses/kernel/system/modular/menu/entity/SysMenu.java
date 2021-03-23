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
package cn.stylefeng.roses.kernel.system.modular.menu.entity;

import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
import cn.stylefeng.roses.kernel.rule.tree.factory.base.AbstractTreeNode;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

/**
 * 系统菜单
 *
 * @author stylefeng
 * @date 2020/11/22 21:16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_menu")
public class SysMenu extends BaseEntity implements AbstractTreeNode {

    /**
     * 主键
     */
    @TableId("menu_id")
    private Long menuId;

    /**
     * 父id，顶级节点的父id是-1
     */
    @TableField("menu_parent_id")
    private Long menuParentId;

    /**
     * 父id集合，中括号包住，逗号分隔
     */
    @TableField("menu_pids")
    private String menuPids;

    /**
     * 菜单的名称
     */
    @TableField("menu_name")
    private String menuName;

    /**
     * 菜单的编码
     */
    @TableField("menu_code")
    private String menuCode;

    /**
     * 应用编码
     */
    @TableField("app_code")
    private String appCode;

    /**
     * 是否可见：Y-是，N-否
     */
    @TableField("visible")
    private String visible;

    /**
     * 排序
     */
    @TableField("menu_sort")
    private BigDecimal menuSort;

    /**
     * 状态：1-启用，2-禁用
     */
    @TableField("status_flag")
    private Integer statusFlag;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 菜单的路径，适用于layui-beetl版本
     */
    @TableField("layui_path")
    private String layuiPath;

    /**
     * 菜单的图标，适用于layui-beetl版本
     */
    @TableField("layui_icon")
    private String layuiIcon;

    /**
     * 路由地址，浏览器显示的URL，例如/menu，适用于antd vue版本
     */
    @TableField("antdv_router")
    private String antdvRouter;

    /**
     * 图标，适用于antd vue版本
     */
    @TableField("antdv_icon")
    private String antdvIcon;

    /**
     * 前端组件名，适用于antd vue版本
     */
    @TableField("antdv_component")
    private String antdvComponent;

    /**
     * 外部链接打开方式：1-内置打开外链，2-新页面外链，适用于antd vue版本
     */
    @TableField("antdv_link_open_type")
    private Integer antdvLinkOpenType;

    /**
     * 外部链接地址
     */
    @TableField("antdv_link_url")
    private String antdvLinkUrl;

    /**
     * 用于非菜单显示页面的重定向url设置
     */
    @TableField("antdv_uid_url")
    private String antdvUidUrl;

    /**
     * 是否删除：Y-被删除，N-未删除
     */
    @TableField(value = "del_flag", fill = FieldFill.INSERT)
    private String delFlag;

    /**
     * 子节点（表中不存在，用于构造树）
     */
    @TableField(exist = false)
    private List<SysMenu> children;

    /**
     * 应用名称
     */
    @TableField(exist = false)
    private String appName;

    /**
     * 父级菜单的名称
     */
    @TableField(exist = false)
    private String menuParentName;

    @Override
    public String getNodeId() {
        return menuId.toString();
    }

    @Override
    public String getNodeParentId() {
        return menuParentId.toString();
    }

    @Override
    public void setChildrenNodes(List childrenNodes) {
        this.children = childrenNodes;
    }

}
