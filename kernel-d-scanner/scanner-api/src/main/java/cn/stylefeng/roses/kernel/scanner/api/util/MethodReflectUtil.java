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
package cn.stylefeng.roses.kernel.scanner.api.util;

import cn.hutool.core.collection.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
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
                        Class<?>[] result = (Class<?>[]) invoke;
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
     * 返回方法的所有类型参数信息
     *
     * @param method 方法反射信息
     * @return 方法第一个参数的class类型
     * @author fengshuonan
     * @date 2020/12/8 18:16
     */
    public static Type[] getMethodGenericTypes(Method method) {
        if (method == null) {
            return null;
        }
        return method.getGenericParameterTypes();
    }

    /**
     * 获取方法的返回值type类型，type可能是class也可能是带泛型的类型
     *
     * @param method 方法反射信息
     * @return 方法返回值的class类型
     * @author fengshuonan
     * @date 2020/12/8 18:20
     */
    public static Type getMethodReturnType(Method method) {
        if (method == null) {
            return null;
        }
        return method.getGenericReturnType();
    }

}
