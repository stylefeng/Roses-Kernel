package cn.stylefeng.roses.kernel.auth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 控制帐号错误检测开关
 *
 * @author xixiaowei
 * @date 2022/1/23 23:42
 */
@Data
@Component
@ConfigurationProperties(prefix = "login")
public class AccountErrorDetectionConfig {

    /**
     * 开关：true-开启错误检测，false-关闭错误检测
     */
    private Boolean enable;
}
