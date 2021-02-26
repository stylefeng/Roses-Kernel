package cn.stylefeng.roses.kernel.security.xss.prop;

import lombok.Data;

import java.util.List;

/**
 * Xss的相关配置
 *
 * @author fengshuonan
 * @date 2021/1/13 22:46
 */
@Data
public class XssProperties {

    /**
     * xss过滤的servlet范围，用在设置filter的urlPattern
     */
    private String[] urlPatterns;

    /**
     * 不被xss过滤的url（ANT风格表达式）
     */
    private List<String> urlExclusion;

}
