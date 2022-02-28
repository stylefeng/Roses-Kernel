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
package cn.stylefeng.roses.kernel.scanner.api;

import cn.stylefeng.roses.kernel.scanner.api.pojo.resource.ReportResourceParam;
import cn.stylefeng.roses.kernel.scanner.api.pojo.resource.SysResourcePersistencePojo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * 资源持久化服务api，将扫描的资源汇报给系统管理用
 *
 * @author fengshuonan
 * @date 2018-02-06 14:30
 */
public interface ResourceReportApi {

    /**
     * 持久化资源集合到某个服务中
     * <p>
     * 如果是单体项目，则吧资源汇报给本服务
     * <p>
     * 如果是微服务项目，则会有个consumer会将本服务的资源发送给资源管理者（一般为system服务）
     *
     * @param reportResourceReq 资源汇报接口
     * @author fengshuonan
     * @date 2020/10/19 22:02
     */
    @RequestMapping(value = "/resourceService/reportResources", method = RequestMethod.POST)
    void reportResources(@RequestBody ReportResourceParam reportResourceReq);

    /**
     * 持久化资源集合到某个服务中
     * <p>
     * 如果是单体项目，则吧资源汇报给本服务
     * <p>
     * 如果是微服务项目，则会有个consumer会将本服务的资源发送给资源管理者（一般为system服务）
     *
     * @param reportResourceReq 资源汇报接口
     * @author fengshuonan
     * @date 2020/10/19 22:02
     */
    @RequestMapping(value = "/resourceService/reportResourcesAndGetResult", method = RequestMethod.POST)
    List<SysResourcePersistencePojo> reportResourcesAndGetResult(@RequestBody ReportResourceParam reportResourceReq);

}
