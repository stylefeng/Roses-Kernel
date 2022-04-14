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

import cn.stylefeng.roses.kernel.rule.pojo.dict.SimpleDict;
import cn.stylefeng.roses.kernel.system.api.pojo.app.SysAppResult;

import java.util.List;
import java.util.Set;

/**
 * 应用相关api
 *
 * @author fengshuonan
 * @date 2020/11/24 21:37
 */
public interface AppServiceApi {

    /**
     * 通过app编码获取app的详情
     *
     * @param appCodes 应用的编码
     * @return 应用的信息
     * @author fengshuonan
     * @date 2020/11/29 20:06
     */
    Set<SimpleDict> getAppsByAppCodes(Set<String> appCodes);

    /**
     * 通过app编码获取app的中文名
     *
     * @param appCode 应用的编码
     * @return 应用的中文名
     * @author fengshuonan
     * @date 2020/11/29 20:06
     */
    String getAppNameByAppCode(String appCode);

    /**
     * 获取当前激活的应用编码
     *
     * @return 激活的应用编码
     * @author fengshuonan
     * @date 2021/1/8 19:01
     */
    String getActiveAppCode();

    /**
     * 获取应用信息详情
     *
     * @author fengshuonan
     * @date 2021/8/24 20:12
     */
    SysAppResult getAppInfoByAppCode(String appCode);

    /**
     * 按顺序获取app的编码和名称
     *
     * @author fengshuonan
     * @date 2022/4/6 22:34
     */
    List<SysAppResult> getSortedApps();

}
