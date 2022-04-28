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
package cn.stylefeng.roses.kernel.system.modular.menu.service;

import cn.stylefeng.roses.kernel.rule.tree.ztree.ZTreeNode;
import cn.stylefeng.roses.kernel.system.api.pojo.menu.MenuAndButtonTreeResponse;
import cn.stylefeng.roses.kernel.system.api.pojo.menu.SysMenuRequest;
import cn.stylefeng.roses.kernel.system.api.pojo.menu.antd.AntdMenuSelectTreeNode;
import cn.stylefeng.roses.kernel.system.api.pojo.menu.antd.AntdSysMenuDTO;
import cn.stylefeng.roses.kernel.system.api.pojo.menu.layui.LayuiAppIndexMenusVO;
import cn.stylefeng.roses.kernel.system.api.pojo.menu.layui.LayuiMenuAndButtonTreeResponse;
import cn.stylefeng.roses.kernel.system.api.pojo.role.request.SysRoleRequest;
import cn.stylefeng.roses.kernel.system.modular.menu.entity.SysMenu;
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
    List<AntdSysMenuDTO> getLeftMenusAntdv(SysMenuRequest sysMenuRequest);

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
     * 获取角色绑定菜单和按钮权限的树
     *
     * @author fengshuonan
     * @date 2021/8/10 22:23
     */
    List<MenuAndButtonTreeResponse> getRoleMenuAndButtons(SysRoleRequest sysRoleRequest);

    /**
     * 获取当前用户的某个应用下的菜单
     *
     * @param appCodeList      应用编码集合
     * @param layuiVisibleFlag layui版本查询查询条件，如果穿true，则会带上layui_visible为Y的查询条件
     * @param antdvFrontType   前台菜单或者后台菜单的类型
     * @author fengshuonan
     * @date 2020/12/27 18:11
     */
    List<SysMenu> getCurrentUserMenus(List<String> appCodeList, Boolean layuiVisibleFlag, Integer antdvFrontType);

}
