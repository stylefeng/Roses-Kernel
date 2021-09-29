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

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.ContentType;
import cn.stylefeng.roses.kernel.rule.pojo.response.ErrorResponseData;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * http响应信息的直接渲染工具
 *
 * @author fengshuonan
 * @date 2020/12/15 21:39
 */
@Slf4j
public class ResponseRenderUtil {

    /**
     * 渲染接口json信息
     *
     * @author fengshuonan
     * @date 2020/12/15 21:40
     */
    public static void renderJsonResponse(HttpServletResponse response, Object responseData) {
        response.setCharacterEncoding(CharsetUtil.UTF_8);
        response.setContentType(ContentType.JSON.toString());
        String errorResponseJsonData = JSON.toJSONString(responseData);
        try {
            response.getWriter().write(errorResponseJsonData);
        } catch (IOException e) {
            log.error("渲染http json信息错误！", e);
        }
    }

    /**
     * 渲染接口json信息
     *
     * @author fengshuonan
     * @date 2020/12/15 21:40
     */
    public static void renderErrorResponse(HttpServletResponse response,
                                           String code, String message, String exceptionClazz) {
        response.setCharacterEncoding(CharsetUtil.UTF_8);
        response.setContentType(ContentType.JSON.toString());
        ErrorResponseData errorResponseData = new ErrorResponseData(code, message);
        errorResponseData.setExceptionClazz(exceptionClazz);
        String errorResponseJsonData = JSON.toJSONString(errorResponseData);
        try {
            response.getWriter().write(errorResponseJsonData);
        } catch (IOException e) {
            log.error("渲染http json信息错误！", e);
        }
    }

    /**
     * 设置渲染文件的头
     *
     * @author fengshuonan
     * @date 2021/7/1 15:01
     */
    public static void setRenderFileHeader(HttpServletResponse response, String fileName) {
        final String charset = ObjectUtil.defaultIfNull(response.getCharacterEncoding(), CharsetUtil.UTF_8);
        response.setHeader("Content-Disposition", StrUtil.format("attachment;filename={}", URLUtil.encode(fileName, Charset.forName(charset))));
        response.setContentType("application/octet-stream; charset=utf-8");
    }

}
