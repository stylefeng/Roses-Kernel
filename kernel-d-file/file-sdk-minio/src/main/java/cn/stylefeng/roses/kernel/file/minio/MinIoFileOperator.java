package cn.stylefeng.roses.kernel.file.minio;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.file.FileOperatorApi;
import cn.stylefeng.roses.kernel.file.constants.FileConstants;
import cn.stylefeng.roses.kernel.file.exception.FileException;
import cn.stylefeng.roses.kernel.file.exception.enums.FileExceptionEnum;
import cn.stylefeng.roses.kernel.file.expander.FileConfigExpander;
import cn.stylefeng.roses.kernel.file.pojo.props.MinIoProperties;
import cn.stylefeng.roses.kernel.file.enums.BucketAuthEnum;
import cn.stylefeng.roses.kernel.jwt.JwtTokenOperator;
import cn.stylefeng.roses.kernel.jwt.api.pojo.config.JwtConfig;
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
            String userTip = FileExceptionEnum.MINIO_FILE_ERROR.getUserTip();
            String finalUserTip = StrUtil.format(userTip, e.getMessage());
            throw new FileException(FileExceptionEnum.MINIO_FILE_ERROR.getErrorCode(), finalUserTip);
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
            String userTip = FileExceptionEnum.MINIO_FILE_ERROR.getUserTip();
            String finalUserTip = StrUtil.format(userTip, e.getMessage());
            throw new FileException(FileExceptionEnum.MINIO_FILE_ERROR.getErrorCode(), finalUserTip);
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
                String userTip = FileExceptionEnum.MINIO_FILE_ERROR.getUserTip();
                String finalUserTip = StrUtil.format(userTip, e.getMessage());
                throw new FileException(FileExceptionEnum.MINIO_FILE_ERROR.getErrorCode(), finalUserTip);
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
            String userTip = FileExceptionEnum.MINIO_FILE_ERROR.getUserTip();
            String finalUserTip = StrUtil.format(userTip, e.getMessage());
            throw new FileException(FileExceptionEnum.MINIO_FILE_ERROR.getErrorCode(), finalUserTip);
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
            String userTip = FileExceptionEnum.MINIO_FILE_ERROR.getUserTip();
            String finalUserTip = StrUtil.format(userTip, e.getMessage());
            throw new FileException(FileExceptionEnum.MINIO_FILE_ERROR.getErrorCode(), finalUserTip);
        }
    }

    @Override
    public void copyFile(String originBucketName, String originFileKey, String newBucketName, String newFileKey) {
        try {
            minioClient.copyObject(originBucketName, originFileKey, newBucketName, newFileKey);
        } catch (Exception e) {
            // 组装提示信息
            String userTip = FileExceptionEnum.MINIO_FILE_ERROR.getUserTip();
            String finalUserTip = StrUtil.format(userTip, e.getMessage());
            throw new FileException(FileExceptionEnum.MINIO_FILE_ERROR.getErrorCode(), finalUserTip);
        }
    }

    @Override
    public String getFileAuthUrl(String bucketName, String key, Long timeoutMillis) {

        // 初始化jwt token的生成工具
        JwtConfig jwtConfig = new JwtConfig();
        jwtConfig.setJwtSecret(FileConfigExpander.getFileAuthJwtSecret());
        jwtConfig.setExpiredSeconds(timeoutMillis / 1000);
        JwtTokenOperator jwtTokenOperator = new JwtTokenOperator(jwtConfig);

        // 生成token
        String token = jwtTokenOperator.generateToken(new HashMap<>());

        // 拼接url = “host” + “预览图片的url” + “?token=xxx”
        return FileConfigExpander.getServerDeployHost() + FileConstants.FILE_PREVIEW_URL + "?token=" + token;

    }

    @Override
    public void deleteFile(String bucketName, String key) {
        try {
            minioClient.removeObject(bucketName, key);
        } catch (Exception e) {
            // 组装提示信息
            String userTip = FileExceptionEnum.MINIO_FILE_ERROR.getUserTip();
            String finalUserTip = StrUtil.format(userTip, e.getMessage());
            throw new FileException(FileExceptionEnum.MINIO_FILE_ERROR.getErrorCode(), finalUserTip);
        }

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
