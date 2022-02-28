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

import cn.stylefeng.roses.kernel.rule.annotation.ChineseDescription;
import lombok.Data;

import java.util.List;

/**
 * 封装antd vue需要的dto
 *
 * @author fengshuonan
 * @date 2021/3/23 21:26
 */
@Data
public class AntdSysMenuDTO {

    /**
     * 菜单的名称
     */
    @ChineseDescription("菜单的名称")
    private String title;

    /**
     * 菜单的图标
     */
    @ChineseDescription("菜单的图标")
    private String icon;

    /**
     * 路由地址(要以/开头)，必填
     */
    @ChineseDescription("路由地址(要以/开头)，必填")
    private String path;

    /**
     * 组件地址(组件要放在view目录下)，父级可以省略
     */
    @ChineseDescription("组件地址(组件要放在view目录下)，父级可以省略")
    private String component;

    /**
     * 为true只注册路由不显示在左侧菜单(比如独立的添加页面)
     */
    @ChineseDescription("为true只注册路由不显示在左侧菜单(比如独立的添加页面)")
    private Boolean hide;

    /**
     * 配置选中的path地址，比如修改页面不在侧栏，打开后侧栏就没有选中，这个配置选中地址，非必须
     */
    @ChineseDescription("配置选中的path地址")
    private String active;

    /**
     * 子级
     */
    @ChineseDescription("子级")
    private List<AntdSysMenuDTO> children;

}
