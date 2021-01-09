package cn.stylefeng.roses.kernel.menu.modular.entity;

import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 菜单下的按钮实例类
 *
 * @author majianguo
 * @date 2021/01/09 14:44
 */
@TableName("sys_menu_button")
@Data
@EqualsAndHashCode(callSuper = true)
public class SysMenuButton extends BaseEntity implements Serializable {

    /**
     * 主键
     */
    @TableId("button_id")
    private Long buttonId;

    /**
     * 菜单id，按钮需要挂在菜单下
     */
    @TableField("menu_id")
    private Long menuId;

    /**
     * 按钮的名称
     */
    @TableField("button_name")
    private String buttonName;

    /**
     * 按钮的编码
     */
    @TableField("button_code")
    private String buttonCode;

    /**
     * 是否删除：Y-被删除，N-未删除
     */
    @TableField("del_flag")
    private String delFlag;

}
