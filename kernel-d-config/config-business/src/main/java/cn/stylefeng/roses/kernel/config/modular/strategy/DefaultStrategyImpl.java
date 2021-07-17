package cn.stylefeng.roses.kernel.config.modular.strategy;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.stylefeng.roses.kernel.config.api.ConfigInitStrategyApi;
import cn.stylefeng.roses.kernel.config.api.pojo.ConfigInitItem;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 默认的初始化配置策略，初始化系统一些必要的参数
 *
 * @author fengshuonan
 * @date 2021/7/8 17:47
 */
@Component
public class DefaultStrategyImpl implements ConfigInitStrategyApi {

    @Override
    public List<ConfigInitItem> getInitConfigs() {
        ArrayList<ConfigInitItem> configInitItems = new ArrayList<>();
        configInitItems.add(new ConfigInitItem("系统名称", "SYS_SYSTEM_NAME", "Guns快速开发平台", "系统名称"));
        configInitItems.add(new ConfigInitItem("服务部署的访问地址", "SYS_SERVER_DEPLOY_HOST", "http://localhost:8080", "一般用在拼接文件的访问地址"));
        configInitItems.add(new ConfigInitItem("websocket的ws-url", "WEB_SOCKET_WS_URL", "ws://localhost:8080/webSocket/{token}", "websocket模块的连接url，用在消息通知模块"));
        configInitItems.add(new ConfigInitItem("auth认证用的jwt秘钥", "SYS_AUTH_JWT_SECRET", RandomUtil.randomString(30), "用于校验登录token"));
        configInitItems.add(new ConfigInitItem("Druid控制台账号", "SYS_DRUID_ACCOUNT", "admin", "Druid控制台账号"));
        configInitItems.add(new ConfigInitItem("Druid控制台账号密码", "SYS_DRUID_PASSWORD", RandomUtil.randomString(20), "Druid控制台账号密码"));
        configInitItems.add(new ConfigInitItem("是否开启图形验证码", "SYS_CAPTCHA_OPEN", "false", "是否开启图形验证码，用在beetl版本"));
        configInitItems.add(new ConfigInitItem("是否开启拖拽验证码", "SYS_DRAG_CAPTCHA_OPEN", "false", "是否开启拖拽验证码，用在vue版本"));
        configInitItems.add(new ConfigInitItem("JWT安全码", "SYS_JWT_SECRET", RandomUtil.randomString(20), "jwt-spring-boot-starter模块的秘钥，非认证用的jwt秘钥，默认20位随机字符串"));
        configInitItems.add(new ConfigInitItem("JWT过期时间", "SYS_JWT_TIMEOUT_SECONDS", "" + 60 * 60 * 24, "jwt-spring-boot-starter模块的秘钥过期时间，默认1天"));
        configInitItems.add(new ConfigInitItem("Linux本地文件保存路径", "SYS_LOCAL_FILE_SAVE_PATH_LINUX", "/tmp/tempFilePath", "本地文件存储的路径，如果没有用本地文件存储，可忽略此配置"));
        configInitItems.add(new ConfigInitItem("Windows本地文件保存路径", "SYS_LOCAL_FILE_SAVE_PATH_WINDOWS", "D:\\tempFilePath", "本地文件存储的路径，如果没有用本地文件存储，可忽略此配置"));
        configInitItems.add(new ConfigInitItem("session过期时间", "SYS_SESSION_EXPIRED_SECONDS", "3600", "单位：秒，session的过期时间，这个时间段内不操作会自动踢下线"));
        configInitItems.add(new ConfigInitItem("账号单端登录限制", "SYS_SINGLE_ACCOUNT_LOGIN_FLAG", "false", "如果开启，则同一个账号只能一个地方登录"));
        configInitItems.add(new ConfigInitItem("系统默认密码", "SYS_DEFAULT_PASSWORD", "123456", "用在重置密码的默认密码"));
        configInitItems.add(new ConfigInitItem("系统发布版本", "SYS_RELEASE_VERSION", DateUtil.format(new Date(), "yyyyMMdd"), "系统发布的版本号"));
        configInitItems.add(new ConfigInitItem("是否开启demo演示", "SYS_DEMO_ENV_FLAG", "false", "是否开启demo演示环境"));
        configInitItems.add(new ConfigInitItem("数据库加密AES秘钥", "SYS_ENCRYPT_SECRET_KEY", RandomUtil.randomString(32), "对称加密秘钥，用在数据库数据加密"));
        return configInitItems;
    }

}
