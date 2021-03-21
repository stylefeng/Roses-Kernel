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
package cn.stylefeng.roses.kernel.security.starter;

import cn.stylefeng.roses.kernel.security.api.expander.SecurityConfigExpander;
import cn.stylefeng.roses.kernel.security.xss.XssFilter;
import cn.stylefeng.roses.kernel.security.xss.XssJacksonDeserializer;
import cn.stylefeng.roses.kernel.security.xss.prop.XssProperties;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

/**
 * XSS安全过滤器相关配置
 *
 * @author fengshuonan
 * @date 2021/1/13 23:05
 */
@Configuration
public class GunsXssAutoConfiguration {

    /**
     * XSS Filter过滤器，用来过滤param之类的传参
     *
     * @author fengshuonan
     * @date 2021/1/13 23:09
     */
    @Bean
    public FilterRegistrationBean<XssFilter> xssFilterFilterRegistrationBean() {
        XssProperties properties = createProperties();

        FilterRegistrationBean<XssFilter> xssFilterFilterRegistrationBean = new FilterRegistrationBean<>();
        xssFilterFilterRegistrationBean.setFilter(new XssFilter(properties));
        xssFilterFilterRegistrationBean.addUrlPatterns(properties.getUrlPatterns());
        xssFilterFilterRegistrationBean.setName(XssFilter.FILTER_NAME);
        xssFilterFilterRegistrationBean.setOrder(HIGHEST_PRECEDENCE);
        return xssFilterFilterRegistrationBean;
    }

    /**
     * XSS的json反序列化器，针对json的传参
     *
     * @author fengshuonan
     * @date 2021/1/13 23:09
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer xssJackson2ObjectMapperBuilderCustomizer() {
        return jacksonObjectMapperBuilder ->
                jacksonObjectMapperBuilder.deserializerByType(String.class, new XssJacksonDeserializer(createProperties()));
    }

    /**
     * 组装xss的配置
     *
     * @author fengshuonan
     * @date 2021/1/13 23:13
     */
    private XssProperties createProperties() {
        XssProperties xssProperties = new XssProperties();
        xssProperties.setUrlPatterns(SecurityConfigExpander.getUrlPatterns());
        xssProperties.setUrlExclusion(SecurityConfigExpander.getUrlExclusion());
        return xssProperties;
    }

}
