package cn.stylefeng.roses.kemel.xss;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kemel.xss.prop.XssProperties;
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
                if (antPathMatcher.match(contextPath + exclusion, servletPath)) {
                    chain.doFilter(request, response);
                    return;
                }
            }
        }

        // 对原有request对象进行包装
        chain.doFilter(new XssHttpServletRequestWrapper((HttpServletRequest) request), response);
    }

}
