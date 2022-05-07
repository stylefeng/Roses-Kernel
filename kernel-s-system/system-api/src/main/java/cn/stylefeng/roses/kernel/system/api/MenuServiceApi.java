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
package cn.stylefeng.roses.kernel.system.api;

import cn.stylefeng.roses.kernel.system.api.pojo.login.v3.IndexMenuInfo;

import java.util.List;
import java.util.Set;

/**
 * 菜单api
 *
 * @author fengshuonan
 * @date 2020/11/24 21:37
 */
public interface MenuServiceApi {

    /**
     * 根据应用编码判断该机构下是否有状态为正常的菜单
     *
     * @param appCode 应用编码
     * @return 该应用下是否有正常菜单，true是，false否
     * @author fengshuonan
     * @date 2020/11/24 21:37
     */
    boolean hasMenu(String appCode);

    /**
     * 获取当前用户所拥有菜单对应的appCode列表
     *
     * @author fengshuonan
     * @date 2021/4/21 15:40
     */
    List<String> getUserAppCodeList();

    /**
     * 获取菜单所有的父级菜单ID
     *
     * @param menuIds 菜单列表
     * @return {@link java.util.Set<java.lang.Long>}
     * @author majianguo
     * @date 2021/6/22 上午10:11
     **/
    Set<Long> getMenuAllParentMenuId(Set<Long> menuIds);

    /**
     * 通过按钮id获取按钮code
     *
     * @author fengshuonan
     * @date 2021/8/11 10:40
     */
    String getMenuButtonCodeByButtonId(Long buttonId);

    /**
     * 通过菜单或按钮id的集合，获取拥有资源编码的集合
     *
     * @author fengshuonan
     * @date 2021/8/11 14:25
     */
    List<String> getResourceCodesByBusinessId(List<Long> businessIds);

    /**
     * 构建Antdv3版本的菜单和权限信息
     *
     * @param menuFrontType 菜单的前后台类型，如果没传递，默认查前台菜单
     * @author fengshuonan
     * @date 2022/4/8 15:59
     */
    List<IndexMenuInfo> buildAuthorities(Integer menuFrontType);

}
