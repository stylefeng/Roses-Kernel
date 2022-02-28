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
import cn.stylefeng.roses.kernel.validator.api.validators.unique.TableUniqueValue;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * 系统菜单按钮请求参数
 *
 * @author luojie
 * @date 2021/1/9 11:19
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysMenuButtonRequest extends BaseRequest {

    /**
     * 主键
     */
    @NotNull(message = "按钮id不能为空", groups = {edit.class, detail.class, delete.class})
    @ChineseDescription("主键")
    private Long buttonId;

    /**
     * 菜单按钮主键集合
     */
    @NotEmpty(message = "菜单按钮主键集合不能为空", groups = {batchDelete.class})
    @ChineseDescription("菜单按钮主键集合")
    private Set<@NotNull(message = "菜单按钮主键集合不能为空", groups = {batchDelete.class}) Long> buttonIds;

    /**
     * 菜单id，按钮需要挂在菜单下
     */
    @NotNull(message = "菜单id不能为空", groups = {add.class, edit.class, list.class, def.class})
    @ChineseDescription("菜单id，按钮需要挂在菜单下")
    private Long menuId;

    /**
     * 按钮的名称
     */
    @NotBlank(message = "按钮名称不能为空", groups = {add.class, edit.class})
    @ChineseDescription("按钮的名称")
    private String buttonName;

    /**
     * 按钮的编码
     */
    @NotBlank(message = "按钮编码不能为空", groups = {add.class, edit.class})
    @TableUniqueValue(
            message = "按钮编码存在重复",
            groups = {add.class, edit.class},
            tableName = "sys_menu_button",
            columnName = "button_code",
            idFieldName = "button_id",
            excludeLogicDeleteItems = true)
    @ChineseDescription("按钮的编码")
    private String buttonCode;

    /**
     * 批量删除验证分组
     */
    public @interface batchDelete {

    }

    /**
     * 一键添加系统默认按钮
     */
    public @interface def {

    }

}
