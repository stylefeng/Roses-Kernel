package cn.stylefeng.roses.kernel.system.pojo.menu;

import lombok.Data;

import java.io.Serializable;

/**
 * 系统菜单按钮响应实体
 *
 * @author luojie
 * @date 2021/1/9 11:19
 */
@Data
public class SysMenuButtonResponse implements Serializable {

    private static final long serialVersionUID = 6909784065876106042L;

    /**
     * 主键
     */
    private Long buttonId;

    /**
     * 菜单id，按钮需要挂在菜单下
     */
    private Long menuId;

    /**
     * 按钮的名称
     */
    private String buttonName;

    /**
     * 按钮的编码
     */
    private String buttonCode;

}
