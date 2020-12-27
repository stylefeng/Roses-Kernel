/*
Copyright [2020] [https://www.stylefeng.cn]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

Guns采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：

1.请不要删除和修改根目录下的LICENSE文件。
2.请不要删除和修改Guns源码头部的版权声明。
3.请保留源码和相关描述文件的项目出处，作者声明等。
4.分发源码时候，请注明软件出处 https://gitee.com/stylefeng/guns-separation
5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/stylefeng/guns-separation
6.若您的项目无法满足以上几点，可申请商业授权，获取Guns商业授权许可，请在官网购买授权，地址为 https://www.stylefeng.cn
 */
package cn.stylefeng.roses.kernel.menu.modular.service;

import cn.stylefeng.roses.kernel.menu.modular.entity.SysMenu;
import cn.stylefeng.roses.kernel.system.pojo.menu.SysMenuRequest;
import cn.stylefeng.roses.kernel.system.pojo.menu.tree.LoginMenuTreeNode;
import cn.stylefeng.roses.kernel.system.pojo.menu.tree.MenuBaseTreeNode;
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
     * 编辑系统菜单
     *
     * @param sysMenuRequest 编辑参数
     * @author fengshuonan
     * @date 2020/3/27 9:03
     */
    void edit(SysMenuRequest sysMenuRequest);

    /**
     * 删除系统菜单
     *
     * @param sysMenuRequest 删除参数
     * @author fengshuonan
     * @date 2020/3/27 9:03
     */
    void delete(SysMenuRequest sysMenuRequest);

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
    List<SysMenu> list(SysMenuRequest sysMenuRequest);

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

    /**
     * 获取某个应用的菜单，用于系统顶部切换菜单（AntDesign前端框架）
     *
     * @param appCode 应用编码
     * @return AntDesign菜单信息结果集
     * @author fengshuonan
     * @date 2020/4/17 17:48
     */
    List<LoginMenuTreeNode> getAppMenusAntDesign(String appCode);

    /**
     * 获取系统菜单树，用于新增，编辑时选择上级节点
     *
     * @param sysMenuRequest 查询参数
     * @return 菜单树列表
     * @author fengshuonan
     * @date 2020/3/27 15:56
     */
    List<MenuBaseTreeNode> tree(SysMenuRequest sysMenuRequest);

    /**
     * 获取系统菜单树，用于给角色授权时选择
     *
     * @param sysMenuRequest 查询参数
     * @return 菜单树列表
     * @author fengshuonan
     * @date 2020/4/5 15:01
     */
    List<MenuBaseTreeNode> treeForGrant(SysMenuRequest sysMenuRequest);

}
