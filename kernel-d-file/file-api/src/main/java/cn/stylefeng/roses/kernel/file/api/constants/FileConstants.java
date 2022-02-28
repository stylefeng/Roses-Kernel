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
package cn.stylefeng.roses.kernel.file.api.constants;

/**
 * 文件模块的常量
 *
 * @author fengshuonan
 * @date 2020/10/26 10:28
 */
public interface FileConstants {

    /**
     * 文件模块的名称
     */
    String FILE_MODULE_NAME = "kernel-d-file";

    /**
     * 异常枚举的步进值
     */
    String FILE_EXCEPTION_STEP_CODE = "09";

    /**
     * 文件分割符
     */
    String FILE_POSTFIX_SEPARATOR = ".";

    /**
     * 默认文件存储的位置
     */
    String DEFAULT_BUCKET_NAME = "defaultBucket";

    /**
     * 服务默认部署的环境地址，不要改这个常量，改系统配置表中的配置 SYS_SERVER_DEPLOY_HOST
     */
    String DEFAULT_SERVER_DEPLOY_HOST = "http://localhost:8080";

    /**
     * 文件鉴权的时间，默认两小时
     */
    Long DEFAULT_FILE_TIMEOUT_SECONDS = 7200L;

    /**
     * 文件预览的接口（需要带token，一般用在机密文件）
     */
    String FILE_PRIVATE_PREVIEW_URL = "/sysFileInfo/private/preview";

    /**
     * Guns中公共文件预览的接口（不用带token，一般用在首页背景，首页banner等地方）
     */
    String FILE_PUBLIC_PREVIEW_URL = "/sysFileInfo/public/preview";

    /**
     * 通用文件预览，通过object名称和bucket名称
     */
    String FILE_PREVIEW_BY_OBJECT_NAME = "/sysFileInfo/previewByObjectName";

    /**
     * 系统默认头像的文件id
     */
    Long DEFAULT_AVATAR_FILE_ID = 10000L;

    /**
     * 默认头像的文件文件名
     */
    String DEFAULT_AVATAR_FILE_OBJ_NAME = "defaultAvatar.png";

}
