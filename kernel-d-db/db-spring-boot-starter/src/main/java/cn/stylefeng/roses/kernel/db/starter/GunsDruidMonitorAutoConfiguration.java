package cn.stylefeng.roses.kernel.db.starter;

import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.db.api.expander.DruidConfigExpander;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * druid监控的自动配置类
 *
 * @author fengshuonan
 * @date 2021/1/24 11:27
 */
@Configuration
public class GunsDruidMonitorAutoConfiguration {

    /**
     * Druid监控界面的配置
     *
     * @author fengshuonan
     * @date 2021/1/10 11:29
     */
    @Bean
    public ServletRegistrationBean<StatViewServlet> statViewServletRegistrationBean() {
        ServletRegistrationBean<StatViewServlet> registrationBean = new ServletRegistrationBean<>();
        registrationBean.setServlet(new StatViewServlet());
        registrationBean.addUrlMappings(DruidConfigExpander.getDruidUrlMappings());
        registrationBean.addInitParameter("loginUsername", DruidConfigExpander.getDruidAdminAccount());
        registrationBean.addInitParameter("loginPassword", DruidConfigExpander.getDruidAdminPassword());
        registrationBean.addInitParameter("resetEnable", DruidConfigExpander.getDruidAdminResetFlag());
        return registrationBean;
    }

    /**
     * 用于配置Druid监控url统计
     *
     * @author fengshuonan
     * @date 2021/1/10 11:45
     */
    @Bean
    public FilterRegistrationBean<WebStatFilter> webStatFilterRegistrationBean() {
        FilterRegistrationBean<WebStatFilter> registrationBean = new FilterRegistrationBean<>();
        WebStatFilter filter = new WebStatFilter();
        registrationBean.setFilter(filter);
        registrationBean.addUrlPatterns(DruidConfigExpander.getDruidAdminWebStatFilterUrlPattern());
        registrationBean.addInitParameter("exclusions", DruidConfigExpander.getDruidAdminWebStatFilterExclusions());
        registrationBean.addInitParameter("sessionStatEnable", DruidConfigExpander.getDruidAdminWebStatFilterSessionStatEnable());
        registrationBean.addInitParameter("sessionStatMaxCount", DruidConfigExpander.getDruidAdminWebStatFilterSessionStatMaxCount());
        if (StrUtil.isNotBlank(DruidConfigExpander.getDruidAdminWebStatFilterSessionName())) {
            registrationBean.addInitParameter("principalSessionName", DruidConfigExpander.getDruidAdminWebStatFilterSessionName());
        }
        if (StrUtil.isNotBlank(DruidConfigExpander.getDruidAdminWebStatFilterPrincipalCookieName())) {
            registrationBean.addInitParameter("principalCookieName", DruidConfigExpander.getDruidAdminWebStatFilterPrincipalCookieName());
        }
        registrationBean.addInitParameter("profileEnable", DruidConfigExpander.getDruidAdminWebStatFilterProfileEnable());
        return registrationBean;
    }

}
