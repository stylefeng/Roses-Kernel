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
package cn.stylefeng.roses.kernel.file.aliyun;

import cn.hutool.core.io.IoUtil;
import cn.stylefeng.roses.kernel.file.api.FileOperatorApi;
import cn.stylefeng.roses.kernel.file.api.enums.BucketAuthEnum;
import cn.stylefeng.roses.kernel.file.api.enums.FileLocationEnum;
import cn.stylefeng.roses.kernel.file.api.exception.FileException;
import cn.stylefeng.roses.kernel.file.api.exception.enums.FileExceptionEnum;
import cn.stylefeng.roses.kernel.file.api.expander.FileConfigExpander;
import cn.stylefeng.roses.kernel.file.api.pojo.props.AliyunOssProperties;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectRequest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

/**
 * 阿里云文件操作的实现
 *
 * @author fengshuonan
 * @date 2020/10/26 10:59
 */
public class AliyunFileOperator implements FileOperatorApi {

    /**
     * 阿里云文件操作客户端
     */
    private OSS ossClient;

    /**
     * 阿里云oss的配置
     */
    private final AliyunOssProperties aliyunOssProperties;

    public AliyunFileOperator(AliyunOssProperties aliyunOssProperties) {
        this.aliyunOssProperties = aliyunOssProperties;
        this.initClient();
    }

    @Override
    public void initClient() {
        String endpoint = aliyunOssProperties.getEndPoint();
        String accessKeyId = aliyunOssProperties.getAccessKeyId();
        String accessKeySecret = aliyunOssProperties.getAccessKeySecret();

        // 创建OSSClient实例。
        ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }

    @Override
    public void destroyClient() {
        ossClient.shutdown();
    }

    @Override
    public Object getClient() {
        return ossClient;
    }

    @Override
    public boolean doesBucketExist(String bucketName) {
        try {
            return ossClient.doesBucketExist(bucketName);
        } catch (OSSException | ClientException e) {
            // 组装提示信息
            throw new FileException(FileExceptionEnum.ALIYUN_FILE_ERROR, e.getMessage());
        }
    }

    @Override
    public void setBucketAcl(String bucketName, BucketAuthEnum bucketAuthEnum) {
        try {
            if (bucketAuthEnum.equals(BucketAuthEnum.PRIVATE)) {
                ossClient.setBucketAcl(bucketName, CannedAccessControlList.Private);
            } else if (bucketAuthEnum.equals(BucketAuthEnum.PUBLIC_READ)) {
                ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
            } else if (bucketAuthEnum.equals(BucketAuthEnum.PUBLIC_READ_WRITE)) {
                ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicReadWrite);
            }
        } catch (OSSException | ClientException e) {
            // 组装提示信息
            throw new FileException(FileExceptionEnum.ALIYUN_FILE_ERROR, e.getMessage());
        }
    }

    @Override
    public boolean isExistingFile(String bucketName, String key) {
        try {
            return ossClient.doesObjectExist(bucketName, key);
        } catch (OSSException | ClientException e) {
            // 组装提示信息
            throw new FileException(FileExceptionEnum.ALIYUN_FILE_ERROR, e.getMessage());
        }
    }

    @Override
    public void storageFile(String bucketName, String key, byte[] bytes) {
        try {
            ossClient.putObject(bucketName, key, new ByteArrayInputStream(bytes));
        } catch (OSSException | ClientException e) {
            // 组装提示信息
            throw new FileException(FileExceptionEnum.ALIYUN_FILE_ERROR, e.getMessage());
        }
    }

    @Override
    public void storageFile(String bucketName, String key, InputStream inputStream) {
        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, inputStream);
            ossClient.putObject(putObjectRequest);
        } catch (OSSException | ClientException e) {
            // 组装提示信息
            throw new FileException(FileExceptionEnum.ALIYUN_FILE_ERROR, e.getMessage());
        }
    }

    @Override
    public byte[] getFileBytes(String bucketName, String key) {
        InputStream objectContent = null;
        try {
            OSSObject ossObject = ossClient.getObject(bucketName, key);
            objectContent = ossObject.getObjectContent();
            return IoUtil.readBytes(objectContent);
        } catch (OSSException | ClientException e) {
            // 组装提示信息
            throw new FileException(FileExceptionEnum.ALIYUN_FILE_ERROR, e.getMessage());
        } finally {
            IoUtil.close(objectContent);
        }

    }

    @Override
    public void setFileAcl(String bucketName, String key, BucketAuthEnum bucketAuthEnum) {
        try {
            if (bucketAuthEnum.equals(BucketAuthEnum.PRIVATE)) {
                ossClient.setObjectAcl(bucketName, key, CannedAccessControlList.Private);
            } else if (bucketAuthEnum.equals(BucketAuthEnum.PUBLIC_READ)) {
                ossClient.setObjectAcl(bucketName, key, CannedAccessControlList.PublicRead);
            } else if (bucketAuthEnum.equals(BucketAuthEnum.PUBLIC_READ_WRITE)) {
                ossClient.setObjectAcl(bucketName, key, CannedAccessControlList.PublicReadWrite);
            }
        } catch (OSSException | ClientException e) {
            // 组装提示信息
            throw new FileException(FileExceptionEnum.ALIYUN_FILE_ERROR, e.getMessage());
        }
    }

    @Override
    public void copyFile(String originBucketName, String originFileKey, String newBucketName, String newFileKey) {
        try {
            ossClient.copyObject(originBucketName, originFileKey, newBucketName, newFileKey);
        } catch (OSSException | ClientException e) {
            // 组装提示信息
            throw new FileException(FileExceptionEnum.ALIYUN_FILE_ERROR, e.getMessage());
        }
    }

    @Override
    public String getFileAuthUrl(String bucketName, String key, Long timeoutMillis) {
        try {
            Date expiration = new Date(System.currentTimeMillis() + timeoutMillis);
            URL url = ossClient.generatePresignedUrl(bucketName, key, expiration);
            return url.toString();
        } catch (OSSException | ClientException e) {
            // 组装提示信息
            throw new FileException(FileExceptionEnum.ALIYUN_FILE_ERROR, e.getMessage());
        }
    }

    @Override
    public String getFileUnAuthUrl(String bucketName, String key) {
        return this.getFileAuthUrl(bucketName, key, FileConfigExpander.getDefaultFileTimeoutSeconds() * 1000);
    }

    @Override
    public void deleteFile(String bucketName, String key) {
        ossClient.deleteObject(bucketName, key);
    }

    @Override
    public FileLocationEnum getFileLocationEnum() {
        return FileLocationEnum.ALIYUN;
    }

}
