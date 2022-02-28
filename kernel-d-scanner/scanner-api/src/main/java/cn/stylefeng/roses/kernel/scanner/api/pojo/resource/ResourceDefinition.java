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
package cn.stylefeng.roses.kernel.scanner.api.pojo.resource;

import cn.stylefeng.roses.kernel.rule.annotation.ChineseDescription;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * API资源的包装类
 *
 * @author fengshuonan
 * @date 2018-01-03-下午3:27
 */
@Data
public class ResourceDefinition implements Serializable {

    /**
     * 应用的标识
     */
    @ChineseDescription("应用的标识")
    private String appCode;

    /**
     * 资源的标识
     */
    @ChineseDescription("资源的标识")
    private String resourceCode;

    /**
     * 资源名称
     */
    @ChineseDescription("资源名称")
    private String resourceName;

    /**
     * 项目编码（如果您不设置的话，默认使用spring.application.name填充）
     * <p>
     * 修复一个项目启动的时候会误删别的项目资源的问题
     *
     * @since 2.2.12
     */
    @ChineseDescription("项目编码")
    private String projectCode;

    /**
     * 控制器类名称
     */
    @ChineseDescription("控制器类名称")
    private String className;

    /**
     * 控制器中的方法名称
     */
    @ChineseDescription("控制器中的方法名称")
    private String methodName;

    /**
     * 资源所属模块
     */
    @ChineseDescription("资源所属模块")
    private String modularCode;

    /**
     * 模块中文名称
     */
    @ChineseDescription("模块中文名称")
    private String modularName;

    /**
     * 初始化资源的机器的ip地址
     */
    @ChineseDescription("初始化资源的机器的ip地址")
    private String ipAddress;

    /**
     * 是否是视图类型：true-是，false-否
     * 如果是视图类型，url需要以 '/view' 开头，
     * 视图类型的接口会渲染出html界面，而不是json数据，
     * 视图层一般会在前后端不分离项目出现
     */
    @ChineseDescription("是否是视图类型")
    private Boolean viewFlag;

    /**
     * 资源的请求路径
     */
    @ChineseDescription("资源的请求路径")
    private String url;

    /**
     * http请求方法
     */
    @ChineseDescription("http请求方法")
    private String httpMethod;

    /**
     * 是否需要登录
     */
    @ChineseDescription("是否需要登录")
    private Boolean requiredLoginFlag;

    /**
     * 是否需要鉴权
     */
    @ChineseDescription("是否需要鉴权")
    private Boolean requiredPermissionFlag;

    /**
     * 需要进行参数校验的分组
     */
    @ChineseDescription("需要进行参数校验的分组")
    private Set<String> validateGroups;

    /**
     * 接口参数的字段描述
     */
    @ChineseDescription("接口参数的字段描述")
    private Set<FieldMetadata> paramFieldDescriptions;

    /**
     * 接口返回结果的字段描述
     */
    @ChineseDescription("接口返回结果的字段描述")
    private FieldMetadata responseFieldDescriptions;

    /**
     * 资源添加日期
     */
    @ChineseDescription("资源添加日期")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 创建人
     */
    @ChineseDescription("创建人")
    private String createUser;

}
