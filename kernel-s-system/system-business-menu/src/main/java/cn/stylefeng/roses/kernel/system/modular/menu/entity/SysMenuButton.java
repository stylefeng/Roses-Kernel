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
import cn.stylefeng.roses.kernel.rule.annotation.ChineseDescription;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 菜单下的按钮(SysMenuButton)表实体类
 *
 * @author luojie
 * @since 2021-01-09 10:59:27
 */
@Data
@TableName("sys_menu_button")
@EqualsAndHashCode(callSuper = true)
public class SysMenuButton extends BaseEntity implements Serializable {

    /**
     * 主键
     */
    @TableId(value = "button_id")
    @ChineseDescription("主键")
    private Long buttonId;

    /**
     * 菜单id，按钮需要挂在菜单下
     */
    @TableField(value = "menu_id")
    @ChineseDescription("菜单id，按钮需要挂在菜单下")
    private Long menuId;

    /**
     * 按钮的名称
     */
    @TableField(value = "button_name")
    @ChineseDescription("按钮的名称")
    private String buttonName;

    /**
     * 按钮的编码
     */
    @TableField(value = "button_code")
    @ChineseDescription("按钮的编码")
    private String buttonCode;

    /**
     * 是否删除：Y-被删除，N-未删除
     */
    @TableField(value = "del_flag", fill = FieldFill.INSERT)
    @ChineseDescription("是否删除：Y-被删除，N-未删除")
    private String delFlag;

}
