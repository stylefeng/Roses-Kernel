package cn.stylefeng.roses.kernel.system.modular.menu.service;

import cn.stylefeng.roses.kernel.rule.tree.ztree.ZTreeNode;
import cn.stylefeng.roses.kernel.system.modular.menu.entity.SysMenu;
import cn.stylefeng.roses.kernel.system.pojo.menu.SysMenuRequest;
import cn.stylefeng.roses.kernel.system.pojo.menu.antd.AntdMenuSelectTreeNode;
import cn.stylefeng.roses.kernel.system.pojo.menu.antd.AntdSysMenuDTO;
import cn.stylefeng.roses.kernel.system.pojo.menu.layui.LayuiAppIndexMenusVO;
import cn.stylefeng.roses.kernel.system.pojo.menu.layui.LayuiMenuAndButtonTreeResponse;
import cn.stylefeng.roses.kernel.system.pojo.role.request.SysRoleRequest;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 系统菜单service接口
 *
 * @author fengshuonan
 * @date 2020/3/13 16:05
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 添加系统菜单
     *
     * @param sysMenuRequest 添加参数
     * @author fengshuonan
     * @date 2020/3/27 9:03
     */
    void add(SysMenuRequest sysMenuRequest);

    /**
     * 删除系统菜单
     *
     * @param sysMenuRequest 删除参数
     * @author fengshuonan
     * @date 2020/3/27 9:03
     */
    void del(SysMenuRequest sysMenuRequest);

    /**
     * 编辑系统菜单
     *
     * @param sysMenuRequest 编辑参数
     * @author fengshuonan
     * @date 2020/3/27 9:03
     */
    void edit(SysMenuRequest sysMenuRequest);

    /**
     * 查看系统菜单
     *
     * @param sysMenuRequest 查看参数
     * @return 系统菜单
     * @author fengshuonan
     * @date 2020/3/27 9:03
     */
    SysMenu detail(SysMenuRequest sysMenuRequest);

    /**
     * 系统菜单列表，树形结构，用于菜单管理界面的列表展示
     *
     * @param sysMenuRequest 查询参数
     * @return 菜单树表列表
     * @author fengshuonan
     * @date 2020/3/26 10:19
     */
    List<SysMenu> findList(SysMenuRequest sysMenuRequest);

    /**
     * 获取菜单列表（layui版本）
     *
     * @author fengshuonan
     * @date 2021/1/6 17:10
     */
    List<SysMenu> findListWithTreeStructure(SysMenuRequest sysMenuRequest);

    /**
     * 获取菜单的树形列表（用于选择上级菜单）（layui版本）
     *
     * @return 菜单树
     * @author fengshuonan
     * @date 2021/1/6 21:47
     */
    List<ZTreeNode> layuiSelectParentMenuTreeList();

    /**
     * 获取系统菜单树，用于新增，编辑时选择上级节点（antd vue版本，用在新增和编辑菜单选择上级菜单）
     *
     * @param sysMenuRequest 查询参数
     * @return 菜单树列表
     * @author fengshuonan
     * @date 2020/3/27 15:56
     */
    List<AntdMenuSelectTreeNode> tree(SysMenuRequest sysMenuRequest);

    /**
     * 获取当前用户首页所有菜单（对应Layui前端的）
     *
     * @author fengshuonan
     * @date 2020/12/27 18:48
     */
    List<LayuiAppIndexMenusVO> getLayuiIndexMenus();

    /**
     * 获取系统所有菜单（适用于登录后获取左侧菜单）（适配antd vue版本）
     *
     * @return 系统所有菜单信息
     * @author majianguo
     * @date 2021/1/7 15:24
     */
    List<AntdSysMenuDTO> getSystemAllMenusAntdv();

    /**
     * 获取包含按钮的系统菜单
     *
     * @param sysRoleRequest 请求参数
     * @param lateralFlag    true-不带树形结构，false-返回带树形结构的
     * @author majianguo
     * @date 2021/1/9 17:11
     */
    List<LayuiMenuAndButtonTreeResponse> getMenuAndButtonTree(SysRoleRequest sysRoleRequest, Boolean lateralFlag);

    /**
     * 获取当前用户的所有菜单
     *
     * @author fengshuonan
     * @date 2020/12/27 18:11
     */
    List<SysMenu> getCurrentUserMenus();

    /**
     * 获取当前用户的某个应用下的菜单
     *
     * @param appCode 应用编码
     * @author fengshuonan
     * @date 2020/12/27 18:11
     */
    List<SysMenu> getCurrentUserMenus(String appCode);

}
