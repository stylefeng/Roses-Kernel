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
package cn.stylefeng.roses.kernel.system.api.pojo.resource;

import cn.stylefeng.roses.kernel.rule.annotation.ChineseDescription;
import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * 资源请求封装
 *
 * @author fengshuonan
 * @since 2019-09-10
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ResourceRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 资源id
     */
    @ChineseDescription("资源id")
    private Long resourceId;

    /**
     * 应用编码
     */
    @ChineseDescription("应用编码")
    private String appCode;

    /**
     * 资源编码
     */
    @ChineseDescription("资源编码")
    @NotBlank(message = "资源编码为空", groups = detail.class)
    private String resourceCode;

    /**
     * 资源名称
     */
    @ChineseDescription("资源名称")
    private String resourceName;

    /**
     * 项目编码
     */
    @ChineseDescription("项目编码")
    private String projectCode;

    /**
     * 类名称
     */
    @ChineseDescription("类名称")
    private String className;

    /**
     * 方法名称
     */
    @ChineseDescription("方法名称")
    private String methodName;

    /**
     * 资源模块编码
     */
    @ChineseDescription("资源模块编码")
    private String modularCode;

    /**
     * 资源模块名称
     */
    @ChineseDescription("资源模块名称")
    private String modularName;

    /**
     * 资源初始化的服务器ip地址
     */
    @ChineseDescription("资源初始化的服务器ip地址")
    private String ipAddress;

    /**
     * 是否是视图类型：Y-是，N-否
     * 如果是视图类型，url需要以 '/view' 开头，
     * 视图类型的接口会渲染出html界面，而不是json数据，
     * 视图层一般会在前后端不分离项目出现
     */
    @ChineseDescription("是否是视图类型")
    private String viewFlag;

    /**
     * 资源url
     */
    @ChineseDescription("资源url")
    private String url;

    /**
     * http请求方法
     */
    @ChineseDescription("http请求方法")
    private String httpMethod;

    /**
     * 是否需要登录：Y-是，N-否
     */
    @ChineseDescription("是否需要登录：Y-是，N-否")
    private String requiredLoginFlag;

    /**
     * 是否需要鉴权：Y-是，N-否
     */
    @ChineseDescription("是否需要鉴权：Y-是，N-否")
    private String requiredPermissionFlag;

    /**
     * 需要进行参数校验的分组
     * <p>
     * json形式存储
     */
    @ChineseDescription("需要进行参数校验的分组")
    private String validateGroups;

    /**
     * 接口参数的字段描述
     * <p>
     * json形式存储
     */
    @ChineseDescription("接口参数的字段描述")
    private String paramFieldDescriptions;

    /**
     * 接口返回结果的字段描述
     * <p>
     * json形式存储
     */
    @ChineseDescription("接口返回结果的字段描述")
    private String responseFieldDescriptions;

    /**
     * 是否是菜单（Y-是，N-否）
     */
    @ChineseDescription("是否是菜单（Y-是，N-否）")
    private String menuFlag;

}
