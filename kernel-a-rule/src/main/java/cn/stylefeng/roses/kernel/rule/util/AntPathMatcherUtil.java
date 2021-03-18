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
    public static Boolean getAntMatchFLag(String requestURI, String contextPath, List<String> antPatterns) {
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        for (String notAuthResourcePattern : antPatterns) {
            if (antPathMatcher.match(contextPath + notAuthResourcePattern, requestURI)) {
                return true;
            }
        }
        return false;
    }

}
