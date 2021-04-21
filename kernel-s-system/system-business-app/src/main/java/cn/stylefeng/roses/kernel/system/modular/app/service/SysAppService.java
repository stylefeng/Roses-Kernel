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
package cn.stylefeng.roses.kernel.system.modular.app.service;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.system.api.pojo.app.SysAppRequest;
import cn.stylefeng.roses.kernel.system.modular.app.entity.SysApp;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 系统应用service接口
 *
 * @author fengshuonan
 * @date 2020/3/13 16:14
 */
public interface SysAppService extends IService<SysApp> {

    /**
     * 添加系统应用
     *
     * @param sysAppParam 添加参数
     * @author fengshuonan
     * @date 2020/3/25 14:57
     */
    void add(SysAppRequest sysAppParam);

    /**
     * 删除系统应用
     *
     * @param sysAppParam 删除参数
     * @author fengshuonan
     * @date 2020/3/25 14:57
     */
    void del(SysAppRequest sysAppParam);

    /**
     * 编辑系统应用
     *
     * @param sysAppParam 编辑参数
     * @author fengshuonan
     * @date 2020/3/25 14:58
     */
    void edit(SysAppRequest sysAppParam);

    /**
     * 更新状态
     *
     * @param sysAppParam 请求参数
     * @author fengshuonan
     * @date 2021/1/6 14:30
     */
    void editStatus(SysAppRequest sysAppParam);

    /**
     * 查看系统应用
     *
     * @param sysAppParam 查看参数
     * @return 系统应用
     * @author fengshuonan
     * @date 2020/3/26 9:50
     */
    SysApp detail(SysAppRequest sysAppParam);

    /**
     * 系统应用列表
     *
     * @param sysAppParam 查询参数
     * @return 系统应用列表
     * @author fengshuonan
     * @date 2020/4/19 14:56
     */
    List<SysApp> findList(SysAppRequest sysAppParam);

    /**
     * 查询系统应用
     *
     * @param sysAppParam 查询参数
     * @return 查询分页结果
     * @author fengshuonan
     * @date 2020/3/24 20:55
     */
    PageResult<SysApp> findPage(SysAppRequest sysAppParam);

    /**
     * 将应用设为默认应用，用户进入系统会默认进这个应用的菜单
     *
     * @param sysAppParam 设为默认应用参数
     * @author fengshuonan
     * @date 2020/6/29 16:49
     */
    void updateActiveFlag(SysAppRequest sysAppParam);

    /**
     * 获取用户的顶部app导航列表
     *
     * @author fengshuonan
     * @date 2021/4/21 15:34
     */
    List<SysApp> getUserTopAppList();

}
