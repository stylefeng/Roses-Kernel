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
package cn.stylefeng.roses.kernel.file.minio;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kernel.auth.api.context.LoginContext;
import cn.stylefeng.roses.kernel.file.api.FileOperatorApi;
import cn.stylefeng.roses.kernel.file.api.constants.FileConstants;
import cn.stylefeng.roses.kernel.file.api.enums.BucketAuthEnum;
import cn.stylefeng.roses.kernel.file.api.enums.FileLocationEnum;
import cn.stylefeng.roses.kernel.file.api.exception.FileException;
import cn.stylefeng.roses.kernel.file.api.exception.enums.FileExceptionEnum;
import cn.stylefeng.roses.kernel.file.api.expander.FileConfigExpander;
import cn.stylefeng.roses.kernel.file.api.pojo.props.MinIoProperties;
import cn.stylefeng.roses.kernel.rule.util.HttpServletUtil;
import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import io.minio.policy.PolicyType;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * MinIo文件操作客户端
 *
 * @author fengshuonan
 * @date 2020/10/31 10:35
 */
public class MinIoFileOperator implements FileOperatorApi {

    private final Object LOCK = new Object();

    /**
     * 文件ContentType对应关系
     */
    Map<String, String> contentType = new HashMap<>();

    /**
     * MinIo文件操作客户端
     */
    private MinioClient minioClient;

    /**
     * MinIo的配置
     */
    private final MinIoProperties minIoProperties;

    public MinIoFileOperator(MinIoProperties minIoProperties) {
        this.minIoProperties = minIoProperties;
        this.initClient();
    }

    @Override
    public void initClient() {
        String endpoint = minIoProperties.getEndpoint();
        String accessKey = minIoProperties.getAccessKey();
        String secretKey = minIoProperties.getSecretKey();

        // 创建minioClient实例
        try {
            minioClient = new MinioClient(endpoint, accessKey, secretKey);
        } catch (InvalidEndpointException | InvalidPortException e) {
            // 组装提示信息
            throw new FileException(FileExceptionEnum.MINIO_FILE_ERROR, e.getMessage());
        }
    }

    @Override
    public void destroyClient() {
        // empty
    }

    @Override
    public Object getClient() {
        return minioClient;
    }

    @Override
    public boolean doesBucketExist(String bucketName) {
        try {
            return minioClient.bucketExists(bucketName);
        } catch (Exception e) {
            // 组装提示信息
            throw new FileException(FileExceptionEnum.MINIO_FILE_ERROR, e.getMessage());
        }
    }

    @Override
    public void setBucketAcl(String bucketName, BucketAuthEnum bucketAuthEnum) {
        setFileAcl(bucketName, "*", bucketAuthEnum);
    }

    @Override
    public boolean isExistingFile(String bucketName, String key) {
        InputStream inputStream = null;
        try {
            inputStream = minioClient.getObject(bucketName, key);
            if (inputStream != null) {
                return true;
            }
        } catch (Exception e) {
            return false;
        } finally {
            IoUtil.close(inputStream);
        }
        return false;
    }

    @Override
    public void storageFile(String bucketName, String key, byte[] bytes) {
        if (bytes != null && bytes.length > 0) {
            // 字节数组转字节数组输入流
            ByteArrayInputStream byteArrayInputStream = IoUtil.toStream(bytes);

            // 获取文件类型
            ByteArrayInputStream tmp = IoUtil.toStream(bytes);
            String type = FileTypeUtil.getType(tmp);
            String fileContentType = getFileContentType(String.format("%s%s", ".", type));

            try {
                minioClient.putObject(bucketName, key, byteArrayInputStream, bytes.length, fileContentType);
            } catch (Exception e) {

                // 组装提示信息
                throw new FileException(FileExceptionEnum.MINIO_FILE_ERROR, e.getMessage());
            }
        }
    }

    @Override
    public void storageFile(String bucketName, String key, InputStream inputStream) {
        if (inputStream != null) {
            byte[] bytes = IoUtil.readBytes(inputStream);
            storageFile(bucketName, key, bytes);
        }
    }

    @Override
    public byte[] getFileBytes(String bucketName, String key) {
        try {
            InputStream inputStream = minioClient.getObject(bucketName, key);
            return IoUtil.readBytes(inputStream);
        } catch (Exception e) {
            // 组装提示信息
            throw new FileException(FileExceptionEnum.MINIO_FILE_ERROR, e.getMessage());
        }
    }

    @Override
    public void setFileAcl(String bucketName, String key, BucketAuthEnum bucketAuthEnum) {
        try {
            if (bucketAuthEnum.equals(BucketAuthEnum.PRIVATE)) {
                minioClient.setBucketPolicy(bucketName, key, PolicyType.NONE);
            } else if (bucketAuthEnum.equals(BucketAuthEnum.PUBLIC_READ)) {
                minioClient.setBucketPolicy(bucketName, key, PolicyType.READ_ONLY);
            } else if (bucketAuthEnum.equals(BucketAuthEnum.PUBLIC_READ_WRITE)) {
                minioClient.setBucketPolicy(bucketName, key, PolicyType.READ_WRITE);
            } else if (bucketAuthEnum.equals(BucketAuthEnum.MINIO_WRITE_ONLY)) {
                minioClient.setBucketPolicy(bucketName, key, PolicyType.WRITE_ONLY);
            }
        } catch (Exception e) {

            // 组装提示信息
            throw new FileException(FileExceptionEnum.MINIO_FILE_ERROR, e.getMessage());
        }
    }

    @Override
    public void copyFile(String originBucketName, String originFileKey, String newBucketName, String newFileKey) {
        try {
            minioClient.copyObject(originBucketName, originFileKey, newBucketName, newFileKey);
        } catch (Exception e) {
            // 组装提示信息
            throw new FileException(FileExceptionEnum.MINIO_FILE_ERROR, e.getMessage());
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
        try {
            minioClient.removeObject(bucketName, key);
        } catch (Exception e) {
            // 组装提示信息
            throw new FileException(FileExceptionEnum.MINIO_FILE_ERROR, e.getMessage());
        }

    }

    @Override
    public FileLocationEnum getFileLocationEnum() {
        return FileLocationEnum.MINIO;
    }

    /**
     * 获取文件后缀对应的contentType
     *
     * @author fengshuonan
     * @date 2020/11/2 18:08
     */
    private Map<String, String> getFileContentType() {
        synchronized (LOCK) {
            if (contentType.size() == 0) {
                contentType.put(".bmp", "application/x-bmp");
                contentType.put(".gif", "image/gif");
                contentType.put(".fax", "image/fax");
                contentType.put(".ico", "image/x-icon");
                contentType.put(".jfif", "image/jpeg");
                contentType.put(".jpe", "image/jpeg");
                contentType.put(".jpeg", "image/jpeg");
                contentType.put(".jpg", "image/jpeg");
                contentType.put(".png", "image/png");
                contentType.put(".rp", "image/vnd.rn-realpix");
                contentType.put(".tif", "image/tiff");
                contentType.put(".tiff", "image/tiff");
                contentType.put(".doc", "application/msword");
                contentType.put(".ppt", "application/x-ppt");
                contentType.put(".pdf", "application/pdf");
                contentType.put(".xls", "application/vnd.ms-excel");
                contentType.put(".txt", "text/plain");
                contentType.put(".java", "java/*");
                contentType.put(".html", "text/html");
                contentType.put(".avi", "video/avi");
                contentType.put(".movie", "video/x-sgi-movie");
                contentType.put(".mp4", "video/mpeg4");
                contentType.put(".mp3", "audio/mp3");
            }
        }
        return contentType;
    }

    /**
     * 获取文件后缀对应的contentType
     *
     * @author fengshuonan
     * @date 2020/11/2 18:05
     */
    private String getFileContentType(String fileSuffix) {
        String contentType = getFileContentType().get(fileSuffix);
        if (ObjectUtil.isEmpty(contentType)) {
            return "application/octet-stream";
        } else {
            return contentType;
        }
    }

}
