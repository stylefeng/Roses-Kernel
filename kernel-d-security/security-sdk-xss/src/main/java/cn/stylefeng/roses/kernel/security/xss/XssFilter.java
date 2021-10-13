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
package cn.stylefeng.roses.kernel.security.xss;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kernel.security.xss.prop.XssProperties;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


/**
 * XSS过滤器，使用 XssHttpServletRequestWrapper 将 HttpServletRequest 对象进行包装
 * <p>
 * 用于进行param传参方式的参数过滤掉危险标志
 *
 * @author fengshuonan
 * @date 2021/1/13 22:45
 */
public class XssFilter implements Filter {

    public static final String FILTER_NAME = "GUNS_XSS_FILTER";

    private final XssProperties xssProperties;

    public XssFilter(XssProperties xssProperties) {
        this.xssProperties = xssProperties;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String servletPath = httpServletRequest.getServletPath();
        String contextPath = httpServletRequest.getContextPath();
        AntPathMatcher antPathMatcher = new AntPathMatcher();

        // 如果当前servlet path排除在外，则放行
        if (xssProperties != null &&
                ObjectUtil.isNotEmpty(xssProperties.getUrlExclusion())) {
            for (String exclusion : xssProperties.getUrlExclusion()) {
                if (antPathMatcher.match(contextPath + exclusion, contextPath + servletPath)) {
                    chain.doFilter(request, response);
                    return;
                }
            }
        }

        // 对原有request对象进行包装
        chain.doFilter(new XssHttpServletRequestWrapper((HttpServletRequest) request), response);
    }

}
