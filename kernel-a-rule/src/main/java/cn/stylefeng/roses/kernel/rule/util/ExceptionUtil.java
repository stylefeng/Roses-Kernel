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

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.rule.pojo.response.ErrorResponseData;

/**
 * 异常处理类
 *
 * @author fengshuonan
 * @date 2020/12/16 16:03
 */
public class ExceptionUtil {

    /**
     * 获取第一条包含参数包名的堆栈记录
     *
     * @param throwable   异常类
     * @param packageName 指定包名
     * @return 某行堆栈信息
     * @author fengshuonan
     * @date 2020/12/16 16:04
     */
    public static String getFirstStackTraceByPackageName(Throwable throwable, String packageName) {

        if (ObjectUtil.hasEmpty(throwable)) {
            return "";
        }

        // 获取所有堆栈信息
        StackTraceElement[] stackTraceElements = throwable.getStackTrace();

        // 默认返回第一条堆栈信息
        String stackTraceElementString = stackTraceElements[0].toString();

        // 包名没传就返第一条堆栈信息
        if (StrUtil.isEmpty(packageName)) {
            return stackTraceElementString;
        }

        // 找到项目包名开头的第一条异常信息
        for (StackTraceElement stackTraceElement : stackTraceElements) {
            if (stackTraceElement.toString().contains(packageName)) {
                stackTraceElementString = stackTraceElement.toString();
                break;
            }
        }

        return stackTraceElementString;
    }

    /**
     * 将异常信息填充到ErrorResponseData中
     *
     * @author fengshuonan
     * @date 2020/12/16 16:09
     */
    public static void fillErrorResponseData(ErrorResponseData errorResponseData, Throwable throwable, String projectPackage) {
        if (errorResponseData == null || throwable == null) {
            return;
        }

        // 填充异常类信息
        errorResponseData.setExceptionClazz(throwable.getClass().getSimpleName());

        // 填充异常提示信息
        errorResponseData.setExceptionTip(throwable.getMessage());

        // 填充第一行项目包路径的堆栈
        errorResponseData.setExceptionPlace(getFirstStackTraceByPackageName(throwable, projectPackage));

    }

}
