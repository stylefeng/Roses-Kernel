package cn.stylefeng.roses.kernel.rule.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import java.util.List;

/**
 * ant风格资源过滤工具
 *
 * @author fengshuonan
 * @date 2020/12/15 22:31
 */
@Slf4j
public class AntPathMatcherUtil {

    /**
     * 判断某个接口是否在一组ant资源表达式下匹配
     *
     * @param requestURI  请求的url
     * @param antPatterns ant风格资源表达式
     * @author fengshuonan
     * @date 2020/12/15 22:31
     */
    public static Boolean getAntMatchFLag(String requestURI, List<String> antPatterns) {
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        for (String notAuthResourcePattern : antPatterns) {
            if (antPathMatcher.match(notAuthResourcePattern, requestURI)) {
                return true;
            }
        }
        return false;
    }

}
