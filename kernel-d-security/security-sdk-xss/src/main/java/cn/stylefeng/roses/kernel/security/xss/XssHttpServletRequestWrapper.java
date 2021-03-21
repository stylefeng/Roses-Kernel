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
import cn.hutool.http.HtmlUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * 对原有HttpServletRequest包装，在执行获取参数等操作时候，进行xss过滤
 *
 * @author fengshuonan
 * @date 2021/1/13 22:50
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    public XssHttpServletRequestWrapper(HttpServletRequest servletRequest) {
        super(servletRequest);
    }

    /**
     * 获取所有param方式传参的属性的值
     *
     * @author fengshuonan
     * @date 2021/1/13 22:52
     */
    @Override
    public String[] getParameterValues(String parameter) {

        // 获取所有参数
        String[] values = super.getParameterValues(parameter);
        if (ObjectUtil.isEmpty(values)) {
            return values;
        }

        // 针对每一个string参数进行过滤
        String[] encodedValues = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            encodedValues[i] = HtmlUtil.filter(values[i]);
        }

        return encodedValues;
    }

    /**
     * 获取单个param方式传参的属性的值
     *
     * @author fengshuonan
     * @date 2021/1/13 22:52
     */
    @Override
    public String getParameter(String parameter) {
        String value = super.getParameter(parameter);
        if (ObjectUtil.isEmpty(value)) {
            return value;
        }
        return HtmlUtil.filter(value);
    }

    /**
     * 获取header的值
     *
     * @author fengshuonan
     * @date 2021/1/13 22:53
     */
    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        if (ObjectUtil.isEmpty(value)) {
            return value;
        }
        return HtmlUtil.filter(value);
    }

}
