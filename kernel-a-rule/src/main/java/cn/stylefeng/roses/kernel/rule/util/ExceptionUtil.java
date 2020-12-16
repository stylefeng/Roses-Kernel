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
