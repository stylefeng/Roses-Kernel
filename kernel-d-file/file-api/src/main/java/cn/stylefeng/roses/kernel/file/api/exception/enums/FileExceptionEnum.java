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
package cn.stylefeng.roses.kernel.file.api.exception.enums;

import cn.stylefeng.roses.kernel.file.api.constants.FileConstants;
import cn.stylefeng.roses.kernel.rule.constants.RuleConstants;
import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import lombok.Getter;

/**
 * 文件操作相关的异常枚举
 *
 * @author fengshuonan
 * @date 2020/10/26 11:29
 */
@Getter
public enum FileExceptionEnum implements AbstractExceptionEnum {

    /**
     * 附件IDS为空
     */
    FILE_IDS_EMPTY(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + FileConstants.FILE_EXCEPTION_STEP_CODE + "01", "附件IDS为空!"),

    /**
     * 下载的文件中包含私有文件
     */
    SECRET_FLAG_INFO_ERROR(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + FileConstants.FILE_EXCEPTION_STEP_CODE + "02", "下载的文件中包含私有文件，具体文件为：{}"),

    /**
     * 阿里云文件操作异常
     */
    ALIYUN_FILE_ERROR(RuleConstants.THIRD_ERROR_TYPE_CODE + FileConstants.FILE_EXCEPTION_STEP_CODE + "03", "阿里云文件操作异常，具体信息为：{}"),

    /**
     * 腾讯云文件操作异常
     */
    TENCENT_FILE_ERROR(RuleConstants.THIRD_ERROR_TYPE_CODE + FileConstants.FILE_EXCEPTION_STEP_CODE + "04", "腾讯云文件操作异常，具体信息为：{}"),

    /**
     * 文件不存在
     */
    FILE_NOT_FOUND(RuleConstants.BUSINESS_ERROR_TYPE_CODE + FileConstants.FILE_EXCEPTION_STEP_CODE + "05", "本地文件不存在，具体信息为：{}"),

    /**
     * MinIO文件操作异常
     */
    MINIO_FILE_ERROR(RuleConstants.THIRD_ERROR_TYPE_CODE + FileConstants.FILE_EXCEPTION_STEP_CODE + "06", "MinIO文件操作异常，具体信息为：{}"),

    /**
     * 上传文件操作异常
     */
    ERROR_FILE(RuleConstants.BUSINESS_ERROR_TYPE_CODE + FileConstants.FILE_EXCEPTION_STEP_CODE + "07", "上传文件操作异常，具体信息为：{}"),

    /**
     * 该条文件信息记录不存在
     */
    NOT_EXISTED(RuleConstants.BUSINESS_ERROR_TYPE_CODE + FileConstants.FILE_EXCEPTION_STEP_CODE + "08", "该条文件信息记录不存在，文件id为：{}"),

    /**
     * 获取文件流错误
     */
    FILE_STREAM_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + FileConstants.FILE_EXCEPTION_STEP_CODE + "09", "获取文件流异常，具体信息为：{}"),

    /**
     * 下载文件错误
     */
    DOWNLOAD_FILE_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + FileConstants.FILE_EXCEPTION_STEP_CODE + "10", "下载文件错误，具体信息为：{}"),

    /**
     * 预览文件异常
     */
    PREVIEW_ERROR_NOT_SUPPORT(RuleConstants.BUSINESS_ERROR_TYPE_CODE + FileConstants.FILE_EXCEPTION_STEP_CODE + "11", "预览文件异常，您预览的文件类型不支持或文件出现错误"),

    /**
     * 预览文件参数存在空值
     */
    PREVIEW_EMPTY_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + FileConstants.FILE_EXCEPTION_STEP_CODE + "12", "预览文件参数存在空值，请求参数为：{}"),

    /**
     * 渲染文件流字节出错
     */
    WRITE_BYTES_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + FileConstants.FILE_EXCEPTION_STEP_CODE + "13", "渲染文件流字节出错，具体信息为：{}"),

    /**
     * 文件id不能为空
     */
    FILE_ID_NOT_NULL(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + FileConstants.FILE_EXCEPTION_STEP_CODE + "14", "文件ID不能为空!"),

    /**
     * 文件Code不能为空
     */
    FILE_CODE_NOT_NULL(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + FileConstants.FILE_EXCEPTION_STEP_CODE + "15", "文件CODE不能为空!"),

    /**
     * 文件不允许被访问
     */
    FILE_DENIED_ACCESS(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + FileConstants.FILE_EXCEPTION_STEP_CODE + "16", "文件不允许被访问，文件加密等级不符合"),

    /**
     * 文件不允许被访问
     */
    FILE_PERMISSION_DENIED(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + FileConstants.FILE_EXCEPTION_STEP_CODE + "17", "文件不允许被访问，请登录后访问");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    FileExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
