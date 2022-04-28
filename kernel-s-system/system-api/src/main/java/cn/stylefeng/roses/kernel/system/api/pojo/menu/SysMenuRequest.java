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
package cn.stylefeng.roses.kernel.system.api.pojo.menu;

import cn.stylefeng.roses.kernel.rule.annotation.ChineseDescription;
import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import cn.stylefeng.roses.kernel.validator.api.validators.flag.FlagValue;
import cn.stylefeng.roses.kernel.validator.api.validators.unique.TableUniqueValue;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 系统菜单参数
 *
 * @author fengshuonan
 * @date 2020/3/26 20:42
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysMenuRequest extends BaseRequest {

    /**
     * 主键
     */
    @NotNull(message = "menuId不能为空", groups = {edit.class, delete.class, detail.class})
    @ChineseDescription("主键")
    private Long menuId;

    /**
     * 父id
     */
    @NotNull(message = "menuParentId不能为空", groups = {add.class, edit.class})
    @ChineseDescription("父id")
    private Long menuParentId;

    /**
     * 菜单名称
     */
    @NotBlank(message = "菜单名称不能为空", groups = {add.class, edit.class})
    @TableUniqueValue(
            message = "菜单名称存在重复",
            groups = {add.class, edit.class},
            tableName = "sys_menu",
            columnName = "menu_name",
            idFieldName = "menu_id",
            excludeLogicDeleteItems = true)
    @ChineseDescription("菜单名称")
    private String menuName;

    /**
     * 菜单的编码
     */
    @NotBlank(message = "菜单的编码不能为空", groups = {add.class, edit.class})
    @TableUniqueValue(
            message = "菜单的编码存在重复",
            groups = {add.class, edit.class},
            tableName = "sys_menu",
            columnName = "menu_code",
            idFieldName = "menu_id",
            excludeLogicDeleteItems = true)
    @ChineseDescription("菜单的编码")
    private String menuCode;

    /**
     * 应用分类（应用编码）
     */
    @NotBlank(message = "appCode不能为空", groups = {add.class, edit.class, getAppMenusAntdVue.class})
    @ChineseDescription("应用分类（应用编码）")
    private String appCode;

    /**
     * 是否可见（Y-是，N-否）
     */
    @FlagValue(message = "是否可见格式错误，正确格式应该Y或者N", groups = {add.class, edit.class}, required = false)
    @ChineseDescription("是否可见（Y-是，N-否）")
    private String visible;

    /**
     * 排序
     */
    @NotNull(message = "排序不能为空", groups = {add.class, edit.class})
    @ChineseDescription("排序")
    private BigDecimal menuSort;

    /**
     * 备注
     */
    @ChineseDescription("备注")
    private String remark;

    /**
     * 菜单的路径（适用于layui-beetl版本）
     */
    @ChineseDescription("菜单的路径（适用于layui-beetl版本）")
    private String layuiPath;

    /**
     * 菜单的图标（适用于layui-beetl版本）
     */
    @ChineseDescription("菜单的图标（适用于layui-beetl版本）")
    private String layuiIcon;

    /**
     * 路由地址，浏览器显示的URL，例如/menu（适用于antd vue版本）
     */
    @ChineseDescription("路由地址，浏览器显示的URL，例如/menu（适用于antd vue版本）")
    private String antdvRouter;

    /**
     * 图标（适用于antd vue版本）
     */
    @ChineseDescription("图标（适用于antd vue版本）")
    private String antdvIcon;

    /**
     * 前端组件名（适用于antd vue版本）
     */
    @ChineseDescription("前端组件名（适用于antd vue版本）")
    private String antdvComponent;

    /**
     * 外部链接打开方式：1-内置打开外链，2-新页面外链（适用于antd vue版本）
     */
    @ChineseDescription("外部链接打开方式：1-内置打开外链，2-新页面外链（适用于antd vue版本）")
    private Integer antdvLinkOpenType;

    /**
     * 外部链接地址（适用于antd vue版本）
     */
    @ChineseDescription("外部链接地址（适用于antd vue版本）")
    private String antdvLinkUrl;

    /**
     * 前台还是后台菜单：1-前台，2-后台，3-前后台都显示
     */
    @ChineseDescription("前台还是后台菜单")
    private Integer antdvFrontType;

    /**
     * 获取主页左侧菜单列表（适配Antd Vue的版本）
     */
    public @interface getAppMenusAntdVue {
    }

}
