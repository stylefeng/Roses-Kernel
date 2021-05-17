package cn.stylefeng.roses.kernel.system.integration.config;

import cn.stylefeng.roses.kernel.system.integration.CustomErrorView;
import cn.stylefeng.roses.kernel.system.integration.core.CustomBeetlGroupUtilConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 错误界面自动配置，一般用于404响应
 *
 * @author fengshuonan
 * @date 2021/5/17 11:16
 */
@Configuration
@AutoConfigureBefore(ErrorMvcAutoConfiguration.class)
public class BeetlErrorViewAutoConfiguration {

    /**
     * 默认错误页面，返回json
     *
     * @author fengshuonan
     * @date 2020/12/16 15:47
     */
    @Bean("error")
    public CustomErrorView error(CustomBeetlGroupUtilConfiguration customBeetlGroupUtilConfiguration) {
        CustomErrorView customErrorView = new CustomErrorView();
        customErrorView.setUrl("/404.html");
        customErrorView.setGroupTemplate(customBeetlGroupUtilConfiguration.getGroupTemplate());
        return customErrorView;
    }

}
