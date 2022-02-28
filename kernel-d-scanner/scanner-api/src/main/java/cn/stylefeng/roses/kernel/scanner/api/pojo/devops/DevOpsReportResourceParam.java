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
package cn.stylefeng.roses.kernel.scanner.api.pojo.devops;

import cn.stylefeng.roses.kernel.rule.annotation.ChineseDescription;
import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import cn.stylefeng.roses.kernel.scanner.api.pojo.resource.SysResourcePersistencePojo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 资源发送到DevOps一体化平台的参数
 *
 * @author fengshuonan
 * @date 2022/1/11 14:05
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DevOpsReportResourceParam extends BaseRequest {

    /**
     * 项目唯一编码，在DevOps平台创建项目后会颁发
     */
    @ChineseDescription("项目唯一编码，在DevOps平台创建项目后会颁发")
    private String projectUniqueCode;

    /**
     * 向DevOps平台发送资源时候的令牌（通过jwt工具生成）
     */
    @ChineseDescription("向DevOps平台发送资源时候的令牌（通过jwt工具生成）")
    private String interactionToken;

    /**
     * 第一个key是模块名称，是下划线分割的控制器名称，不带Controller结尾
     * <p>
     * 第二个key是资源的编码
     */
    @ChineseDescription("第一个key是模块名称，是下划线分割的控制器名称，不带Controller结尾。第二个key是资源的编码")
    private List<SysResourcePersistencePojo> sysResourcePersistencePojoList;

    /**
     * FieldMetadata类的全路径
     */
    @ChineseDescription("FieldMetadata类的全路径")
    private String fieldMetadataClassPath;

    public DevOpsReportResourceParam(String projectUniqueCode, String interactionToken, List<SysResourcePersistencePojo> sysResourcePersistencePojoList, String fieldMetadataClassPath) {
        this.projectUniqueCode = projectUniqueCode;
        this.interactionToken = interactionToken;
        this.sysResourcePersistencePojoList = sysResourcePersistencePojoList;
        this.fieldMetadataClassPath = fieldMetadataClassPath;
    }
}
