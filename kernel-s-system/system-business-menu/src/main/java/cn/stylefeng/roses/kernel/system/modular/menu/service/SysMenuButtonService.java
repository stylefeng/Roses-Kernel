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

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.system.modular.menu.entity.SysMenuButton;
import cn.stylefeng.roses.kernel.system.api.pojo.menu.SysMenuButtonRequest;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 系统菜单按钮service接口
 *
 * @author luojie
 * @date 2021/1/9 11:04
 */
public interface SysMenuButtonService extends IService<SysMenuButton> {

    /**
     * 添加系统菜单按钮
     *
     * @param sysMenuButtonRequest 菜单按钮请求参数
     * @author luojie
     * @date 2021/1/9 11:28
     */
    void add(SysMenuButtonRequest sysMenuButtonRequest);

    /**
     * 添加系统菜单按钮
     *
     * @param sysMenuButtonRequest 菜单按钮请求参数
     * @author chenjinlong
     * @date 2021/1/9 11:28
     */
    void addDefaultButtons(SysMenuButtonRequest sysMenuButtonRequest);

    /**
     * 删除单个系统菜单按钮
     *
     * @param sysMenuButtonRequest 菜单按钮id
     * @author luojie
     * @date 2021/1/9 12:14
     */
    void del(SysMenuButtonRequest sysMenuButtonRequest);

    /**
     * 批量删除多个系统菜单按钮
     *
     * @param sysMenuButtonRequest 菜单按钮id集合
     * @author luojie
     * @date 2021/1/9 12:27
     */
    void delBatch(SysMenuButtonRequest sysMenuButtonRequest);

    /**
     * 编辑系统菜单按钮
     *
     * @param sysMenuButtonRequest 菜单按钮请求参数
     * @author luojie
     * @date 2021/1/9 12:00
     */
    void edit(SysMenuButtonRequest sysMenuButtonRequest);

    /**
     * 获取菜单按钮详情
     *
     * @param sysMenuButtonRequest 菜单按钮id
     * @return 菜单按钮详情
     * @author luojie
     * @date 2021/1/9 11:53
     */
    SysMenuButton detail(SysMenuButtonRequest sysMenuButtonRequest);

    /**
     * 分页获取菜单按钮列表
     *
     * @param sysMenuButtonRequest 菜单id
     * @return 菜单按钮列表
     * @author luojie
     * @date 2021/1/9 12:53
     */
    PageResult<SysMenuButton> findPage(SysMenuButtonRequest sysMenuButtonRequest);

    /**
     * 根据菜单id删除该菜单下的所有按钮
     *
     * @param menuId 菜单id
     * @author luojie
     * @date 2021/1/9 14:45
     */
    void deleteMenuButtonByMenuId(Long menuId);

}
