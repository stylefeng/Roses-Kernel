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
package cn.stylefeng.roses.kernel.system.api.pojo.menu.antd;

import cn.stylefeng.roses.kernel.auth.api.pojo.login.basic.SimpleRoleInfo;
import lombok.Data;

import java.util.List;

/**
 * 封装antd vue需要的菜单信息，service对外输出的对象
 *
 * @author majianguo
 * @date 2021/1/7 15:18
 */
@Data
public class AntdSysMenuDTO {

    /**
     * 主键
     */
    private Long menuId;

    /**
     * 父id，顶级节点的父id是-1
     */
    private Long menuParentId;

    /**
     * 菜单的名称
     */
    private String menuName;

    /**
     * 路由地址，浏览器显示的URL，例如/menu，适用于antd vue版本
     */
    private String antdvRouter;

    /**
     * 图标，适用于antd vue版本
     */
    private String antdvIcon;

    /**
     * 是否显示，Y-显示，N-不显示
     */
    private String visible;

    /**
     * 子节点（表中不存在，用于构造树）
     */
    private List children;

    /**
     * 菜单可以被那些角色访问
     */
    private List<SimpleRoleInfo> roles;

}
