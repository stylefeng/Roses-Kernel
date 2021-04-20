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
package cn.stylefeng.roses.kernel.scanner.api.pojo.scanner;

import lombok.Data;

/**
 * 扫描的常量
 *
 * @author fengshuonan
 * @date 2018-01-03 21:39
 */
@Data
public class ScannerProperties {

    /**
     * 资源扫描开关
     */
    private Boolean open;

    /**
     * 扫描到的资源的url是否要带appCode属性，此值默认为false
     * <p>
     * 也就是资源的url上不会带appCode属性，一般在微服务的系统中需要把此值设为true
     */
    private Boolean urlWithAppCode = false;

    /**
     * 项目编码（如果您不设置的话，默认使用spring.application.name填充，一般不用手动设置此值）
     */
    private String appCode;

    /**
     * 扫描到的url是否要带context-path，默认为true也就是会带上
     * <p>
     * 如果设置为true，则资源的url属性会带当前项目的context-path
     * <p>
     * 如果设置为false，则资源url属性不会带context-path
     * <p>
     * 如果urlWithAppCode开关和urlWithContextPath都开了，生成的url会是如下： /appCode/contextPath/xxx
     */
    private Boolean urlWithContextPath = true;

    /**
     * 项目的context-path
     */
    private String contextPath;

    /**
     * 链接符号，一般不要修改此符号
     */
    private String linkSymbol = "$";

}
