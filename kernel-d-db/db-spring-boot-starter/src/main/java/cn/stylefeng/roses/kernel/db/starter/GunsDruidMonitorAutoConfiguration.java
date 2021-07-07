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
package cn.stylefeng.roses.kernel.db.starter;

import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.db.api.expander.DruidConfigExpander;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

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

    /**
     * 解决druid discard long time none received connection问题
     *
     * @author fengshuonan
     * @date 2021/7/7 14:15
     */
    @PostConstruct
    public void setProperties() {
        System.setProperty("druid.mysql.usePingMethod", "false");
    }

}
