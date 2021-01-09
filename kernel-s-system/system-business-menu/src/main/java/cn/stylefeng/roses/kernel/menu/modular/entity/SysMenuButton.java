package cn.stylefeng.roses.kernel.menu.modular.entity;

import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
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
    private Long buttonId;

    /**
     * 菜单id，按钮需要挂在菜单下
     */
    @TableField(value = "menu_id")
    private Long menuId;

    /**
     * 按钮的名称
     */
    @TableField(value = "button_name")
    private String buttonName;

    /**
     * 按钮的编码
     */
    @TableField(value = "button_code")
    private String buttonCode;

    /**
     * 是否删除：Y-被删除，N-未删除
     */
    @TableField(value = "del_flag")
    private String delFlag;

}
