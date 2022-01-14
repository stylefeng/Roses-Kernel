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
package cn.stylefeng.roses.kernel.system.api.pojo.menu;

import cn.stylefeng.roses.kernel.rule.annotation.ChineseDescription;
import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 菜单资源的请求
 *
 * @author fengshuonan
 * @date 2021/8/8 22:40
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysMenuResourceRequest extends BaseRequest {

    /**
     * 业务id不能为空
     */
    @NotNull(message = "业务id不能为空", groups = {SysMenuResourceRequest.list.class, SysMenuResourceRequest.add.class})
    @ChineseDescription("业务id")
    private Long businessId;

    /**
     * 绑定资源的类型，1：菜单，2：菜单下按钮
     */
    @NotNull(message = "绑定的资源类型不能为空", groups = {SysMenuResourceRequest.add.class})
    @ChineseDescription("绑定资源的类型")
    private Integer businessType;

    /**
     * 模块下所有的资源
     */
    @ChineseDescription("模块下所有的资源")
    private List<String> modularTotalResource;

    /**
     * 模块下选中的资源
     */
    @ChineseDescription("模块下选中的资源")
    private List<String> selectedResource;

}
