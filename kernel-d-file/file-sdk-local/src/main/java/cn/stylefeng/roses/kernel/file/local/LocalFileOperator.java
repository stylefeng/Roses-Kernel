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
package cn.stylefeng.roses.kernel.file.local;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.system.SystemUtil;
import cn.stylefeng.roses.kernel.auth.api.context.LoginContext;
import cn.stylefeng.roses.kernel.file.api.FileOperatorApi;
import cn.stylefeng.roses.kernel.file.api.constants.FileConstants;
import cn.stylefeng.roses.kernel.file.api.enums.BucketAuthEnum;
import cn.stylefeng.roses.kernel.file.api.enums.FileLocationEnum;
import cn.stylefeng.roses.kernel.file.api.exception.FileException;
import cn.stylefeng.roses.kernel.file.api.exception.enums.FileExceptionEnum;
import cn.stylefeng.roses.kernel.file.api.expander.FileConfigExpander;
import cn.stylefeng.roses.kernel.file.api.pojo.props.LocalFileProperties;
import cn.stylefeng.roses.kernel.rule.util.HttpServletUtil;

import java.io.File;
import java.io.InputStream;

/**
 * 本地文件的操作
 *
 * @author fengshuonan
 * @date 2020/10/26 13:38
 */
public class LocalFileOperator implements FileOperatorApi {

    private final LocalFileProperties localFileProperties;

    private String currentSavePath = "";

    public LocalFileOperator(LocalFileProperties localFileProperties) {
        this.localFileProperties = localFileProperties;
        initClient();
    }

    @Override
    public void initClient() {
        if (SystemUtil.getOsInfo().isWindows()) {
            String savePathWindows = localFileProperties.getLocalFileSavePathWin();
            if (!FileUtil.exist(savePathWindows)) {
                FileUtil.mkdir(savePathWindows);
            }
            currentSavePath = savePathWindows;
        } else {
            String savePathLinux = localFileProperties.getLocalFileSavePathLinux();
            if (!FileUtil.exist(savePathLinux)) {
                FileUtil.mkdir(savePathLinux);
            }
            currentSavePath = savePathLinux;
        }
    }

    @Override
    public void destroyClient() {
        // empty
    }

    @Override
    public Object getClient() {
        // empty
        return null;
    }

    @Override
    public boolean doesBucketExist(String bucketName) {
        String absolutePath = currentSavePath + File.separator + bucketName;
        return FileUtil.exist(absolutePath);
    }

    @Override
    public void setBucketAcl(String bucketName, BucketAuthEnum bucketAuthEnum) {
        // empty
    }

    @Override
    public boolean isExistingFile(String bucketName, String key) {
        String absoluteFile = currentSavePath + File.separator + bucketName + File.separator + key;
        return FileUtil.exist(absoluteFile);
    }

    @Override
    public void storageFile(String bucketName, String key, byte[] bytes) {

        // 判断bucket存在不存在
        String bucketPath = currentSavePath + File.separator + bucketName;
        if (!FileUtil.exist(bucketPath)) {
            FileUtil.mkdir(bucketPath);
        }

        // 存储文件
        String absoluteFile = currentSavePath + File.separator + bucketName + File.separator + key;
        FileUtil.writeBytes(bytes, absoluteFile);
    }

    @Override
    public void storageFile(String bucketName, String key, InputStream inputStream) {

        // 判断bucket存在不存在
        String bucketPath = currentSavePath + File.separator + bucketName;
        if (!FileUtil.exist(bucketPath)) {
            FileUtil.mkdir(bucketPath);
        }

        // 存储文件
        String absoluteFile = currentSavePath + File.separator + bucketName + File.separator + key;
        FileUtil.writeFromStream(inputStream, absoluteFile);
    }

    @Override
    public byte[] getFileBytes(String bucketName, String key) {

        // 判断文件存在不存在
        String absoluteFile = currentSavePath + File.separator + bucketName + File.separator + key;
        if (!FileUtil.exist(absoluteFile)) {
            // 组装返回信息
            String errorMessage = StrUtil.format("bucket={},key={}", bucketName, key);
            throw new FileException(FileExceptionEnum.FILE_NOT_FOUND, errorMessage);
        } else {
            return FileUtil.readBytes(absoluteFile);
        }
    }

    @Override
    public void setFileAcl(String bucketName, String key, BucketAuthEnum bucketAuthEnum) {
        // empty
    }

    @Override
    public void copyFile(String originBucketName, String originFileKey, String newBucketName, String newFileKey) {

        // 判断文件存在不存在
        String originFile = currentSavePath + File.separator + originBucketName + File.separator + originFileKey;
        if (!FileUtil.exist(originFile)) {
            // 组装返回信息
            String errorMessage = StrUtil.format("bucket={},key={}", originBucketName, originFileKey);
            throw new FileException(FileExceptionEnum.FILE_NOT_FOUND, errorMessage);
        } else {

            // 拷贝文件
            String destFile = currentSavePath + File.separator + newBucketName + File.separator + newFileKey;
            FileUtil.copy(originFile, destFile, true);
        }
    }

    @Override
    public String getFileAuthUrl(String bucketName, String key, Long timeoutMillis) {

        // 获取登录用户的token
        String token = LoginContext.me().getToken();

        // 获取context-path
        String contextPath = HttpServletUtil.getRequest().getContextPath();

        return FileConfigExpander.getServerDeployHost() + contextPath + FileConstants.FILE_PREVIEW_BY_OBJECT_NAME + "?fileBucket=" + bucketName + "&fileObjectName=" + key + "&token=" + token;
    }

    @Override
    public String getFileUnAuthUrl(String bucketName, String key) {
        // 获取context-path
        String contextPath = HttpServletUtil.getRequest().getContextPath();

        return FileConfigExpander.getServerDeployHost() + contextPath + FileConstants.FILE_PREVIEW_BY_OBJECT_NAME + "?fileBucket=" + bucketName + "&fileObjectName=" + key;
    }

    @Override
    public void deleteFile(String bucketName, String key) {

        // 判断文件存在不存在
        String file = currentSavePath + File.separator + bucketName + File.separator + key;
        if (!FileUtil.exist(file)) {
            return;
        }

        // 删除文件
        FileUtil.del(file);

    }

    @Override
    public FileLocationEnum getFileLocationEnum() {
        return FileLocationEnum.LOCAL;
    }
    
}
