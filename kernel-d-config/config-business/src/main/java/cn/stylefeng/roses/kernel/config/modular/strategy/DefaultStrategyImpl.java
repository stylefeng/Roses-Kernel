package cn.stylefeng.roses.kernel.config.modular.strategy;

import cn.hutool.core.util.RandomUtil;
import cn.stylefeng.roses.kernel.config.api.ConfigInitStrategyApi;
import cn.stylefeng.roses.kernel.config.api.pojo.ConfigInitItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 默认的初始化配置策略，初始化系统一些必要的参数
 *
 * @author fengshuonan
 * @date 2021/7/8 17:47
 */
public class DefaultStrategyImpl implements ConfigInitStrategyApi {

    @Override
    public List<ConfigInitItem> getInitConfigs() {
        ArrayList<ConfigInitItem> configInitItems = new ArrayList<>();
        configInitItems.add(new ConfigInitItem("JWT安全码", "SYS_JWT_SECRET", RandomUtil.randomString(20), "jwt-spring-boot-starter模块的秘钥，非认证用的jwt秘钥，默认20位随机字符串"));
        configInitItems.add(new ConfigInitItem("JWT过期时间", "SYS_JWT_TIMEOUT_SECONDS", "" + 60 * 60 * 24, "jwt-spring-boot-starter模块的秘钥过期时间，默认1天"));
        configInitItems.add(new ConfigInitItem("Linux本地文件保存路径", "SYS_LOCAL_FILE_SAVE_PATH_LINUX", "/tmp/tempFilePath", "本地文件存储的路径，如果没有用本地文件存储，可忽略此配置"));
        configInitItems.add(new ConfigInitItem("Windows本地文件保存路径", "SYS_JWT_SECRET", "D:\\tempFilePath", "本地文件存储的路径，如果没有用本地文件存储，可忽略此配置"));
        return configInitItems;
    }

}
