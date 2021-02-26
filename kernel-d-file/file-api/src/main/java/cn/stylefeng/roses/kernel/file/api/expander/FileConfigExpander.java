package cn.stylefeng.roses.kernel.file.api.expander;

import cn.hutool.core.util.RandomUtil;
import cn.stylefeng.roses.kernel.config.api.context.ConfigContext;
import cn.stylefeng.roses.kernel.file.api.constants.FileConstants;
import cn.stylefeng.roses.kernel.file.api.pojo.props.LocalFileProperties;

/**
 * 文件相关的配置获取
 *
 * @author fengshuonan
 * @date 2020/11/29 14:47
 */
public class FileConfigExpander {

    /**
     * 获取服务部署的机器host，例如http://localhost
     * <p>
     * 这个配置为了用在文件url的拼接上
     *
     * @author fengshuonan
     * @date 2020/11/29 16:13
     */
    public static String getServerDeployHost() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_SERVER_DEPLOY_HOST", String.class, FileConstants.DEFAULT_SERVER_DEPLOY_HOST);
    }

    /**
     * 获取文件生成auth url的失效时间
     *
     * @author fengshuonan
     * @date 2020/11/29 16:13
     */
    public static Long getDefaultFileTimeoutSeconds() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_DEFAULT_FILE_TIMEOUT_SECONDS", Long.class, FileConstants.DEFAULT_FILE_TIMEOUT_SECONDS);
    }

    /**
     * 用于专门给文件鉴权用的jwt的密钥，没配置的话会自动随机生成
     * <p>
     * 默认不写死，防止漏洞
     *
     * @author fengshuonan
     * @date 2020/11/29 16:13
     */
    public static String getFileAuthJwtSecret() {
        String defaultFileTimeoutSeconds = ConfigContext.me().getConfigValueNullable("SYS_DEFAULT_FILE_TIMEOUT_SECONDS", String.class);
        if (defaultFileTimeoutSeconds == null) {
            return RandomUtil.randomString(20);
        } else {
            return defaultFileTimeoutSeconds;
        }
    }

    /**
     * 本地文件存储位置（linux）
     *
     * @author fengshuonan
     * @date 2020/12/1 14:44
     */
    public static String getLocalFileSavePathLinux() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_LOCAL_FILE_SAVE_PATH_LINUX", String.class, new LocalFileProperties().getLocalFileSavePathLinux());
    }

    /**
     * 本地文件存储位置（windows）
     *
     * @author fengshuonan
     * @date 2020/12/1 14:44
     */
    public static String getLocalFileSavePathWindows() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_LOCAL_FILE_SAVE_PATH_WINDOWS", String.class, new LocalFileProperties().getLocalFileSavePathWin());
    }

    /**
     * 默认的系统头像，base64编码的
     *
     * @author fengshuonan
     * @date 2020/12/29 20:10
     */
    public static String getDefaultAvatarBase64() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_DEFAULT_AVATAR_BASE64", String.class, FileConstants.DEFAULT_BASE_64_AVATAR);
    }

}
