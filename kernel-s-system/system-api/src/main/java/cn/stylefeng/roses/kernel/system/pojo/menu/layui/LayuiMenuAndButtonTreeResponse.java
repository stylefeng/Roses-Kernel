package cn.stylefeng.roses.kernel.system.pojo.menu.layui;

import lombok.Data;

/**
 * 角色分配资源和菜单的树
 *
 * @author majianguo
 * @date 2021/1/9 16:59
 */
@Data
public class LayuiMenuAndButtonTreeResponse {

    /**
     * 节点ID
     */
    private Long id;

    /**
     * 节点父ID
     */
    private Long pid;

    /**
     * 节点名称
     */
    private String name;

    /**
     * 是否是菜单(如果是false,则pid是菜单的id)
     */
    private Boolean menuFlag;

    /**
     * 是否选择(已拥有的是true)
     */
    private Boolean checked;

    /**
     * 按钮code
     */
    private String buttonCode;

}
