package cn.stylefeng.roses.kernel.resource.api.util;

import cn.hutool.core.collection.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 反射工具类，获取方法的一些元数据
 *
 * @author fengshuonan
 * @date 2020/12/8 17:48
 */
@Slf4j
public class MethodReflectUtil {

    /**
     * 获取方法上的注解
     * <p>
     * 注意，此方法只获取方法第一个参数的所有注解
     *
     * @param method 方法反射信息
     * @return 方法参数上的注解集合
     * @author fengshuonan
     * @date 2020/12/8 17:49
     */
    public static List<Annotation> getMethodFirstParamAnnotations(Method method) {
        if (method == null) {
            return null;
        }

        if (method.getParameterCount() <= 0) {
            return null;
        }

        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        if (parameterAnnotations.length == 0) {
            return null;
        }

        // 只获取第一个参数的所有注解，所以下标为0
        Annotation[] resultAnnotations = parameterAnnotations[0];
        if (resultAnnotations == null || resultAnnotations.length == 0) {
            return null;
        } else {
            return CollectionUtil.toList(resultAnnotations);
        }
    }

    /**
     * 获取方法上校验分组
     * <p>
     * 例如：获取如下方法的校验分组信息SysAppRequest.edit.class
     * <pre>
     * public ResponseData edit(@RequestBody @Validated(SysAppRequest.edit.class) SysAppRequest sysAppParam) {
     *     ...
     * }
     * </pre>
     *
     * @param method 方法反射信息
     * @return 方法的参数校验分组信息
     * @author fengshuonan
     * @date 2020/12/8 17:59
     */
    public static Set<String> getMethodValidateGroup(Method method) {
        List<Annotation> methodFirstParamAnnotations = getMethodFirstParamAnnotations(method);
        if (methodFirstParamAnnotations == null) {
            return null;
        }

        // 判断annotation有没有是@Validated注解类型的
        try {
            for (Annotation annotation : methodFirstParamAnnotations) {
                if (Validated.class.equals(annotation.annotationType())) {
                    Method validateGroupMethod = annotation.annotationType().getMethod("value");
                    Object invoke = validateGroupMethod.invoke(annotation);
                    if (invoke != null) {
                        Class<?>[] result = (Class<?>[])invoke;
                        if (result.length > 0) {
                            HashSet<String> groupClassNames = new HashSet<>();
                            for (Class<?> groupClass : result) {
                                groupClassNames.add(groupClass.getSimpleName());
                            }
                            return groupClassNames;
                        }
                    }
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            log.error("获取方法上的校验分组出错！", e);
        }
        return null;
    }

    /**
     * 获取方法第一个参数的类类型
     *
     * @param method 方法反射信息
     * @return 方法第一个参数的class类型
     * @author fengshuonan
     * @date 2020/12/8 18:16
     */
    public static Class<?> getMethodFirstParamClass(Method method) {
        if (method == null) {
            return null;
        }

        if (method.getParameterCount() <= 0) {
            return null;
        }

        Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length > 0) {
            return parameterTypes[0];
        } else {
            return null;
        }
    }

    /**
     * 获取方法的返回值class类型
     *
     * @param method 方法反射信息
     * @return 方法返回值的class类型
     * @author fengshuonan
     * @date 2020/12/8 18:20
     */
    public static Class<?> getMethodReturnClass(Method method) {
        if (method == null) {
            return null;
        }
        return method.getReturnType();
    }

}
