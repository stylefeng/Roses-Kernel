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

/**
 * 项目相关的工具类
 *
 * @author fengshuonan
 * @date 2021/5/18 10:41
 */
@Slf4j
public class ProjectUtil {

    /**
     * 前后端分离项目的标识
     */
    public static Boolean SEPARATION_FLAG = null;

    /**
     * 获取项目是否是前后端分离的，通过系统中的ErrorView来判断
     * <p>
     * 如果是分离版项目，则项目中会有ErrorStaticJsonView，如果是不分离版本，项目中则会有CustomErrorView
     *
     * @return true-分离版，false-不分离版
     * @author fengshuonan
     * @date 2021/5/18 10:42
     */
    public static Boolean getSeparationFlag() {
        if (SEPARATION_FLAG != null) {
            return SEPARATION_FLAG;
        }

        try {
            Class.forName("cn.stylefeng.roses.kernel.system.integration.ErrorStaticJsonView");
            SEPARATION_FLAG = true;
            return SEPARATION_FLAG;
        } catch (ClassNotFoundException e) {
            SEPARATION_FLAG = false;
            return SEPARATION_FLAG;
        }
    }

}  