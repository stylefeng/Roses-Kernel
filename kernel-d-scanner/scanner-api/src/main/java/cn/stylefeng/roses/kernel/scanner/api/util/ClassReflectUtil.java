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

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.TypeUtil;
import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import cn.stylefeng.roses.kernel.scanner.api.annotation.field.ChineseDescription;
import cn.stylefeng.roses.kernel.scanner.api.pojo.resource.FieldMetadata;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toCollection;

/**
 * 类的反射工具
 *
 * @author fengshuonan
 * @date 2020/12/8 18:23
 */
public class ClassReflectUtil {

    private static Map<String, String> runMap = new HashMap<>(2);

    /**
     * 获取一个类的所有字段描述
     *
     * @param clazz 类的class类型
     * @return 该类下所有字段的描述信息
     * @author fengshuonan
     * @date 2020/12/8 18:27
     */
    public static Set<FieldMetadata> getClassFieldDescription(Class<?> clazz) {
        HashSet<FieldMetadata> fieldDescriptions = new HashSet<>();

        if (clazz == null) {
            return fieldDescriptions;
        }

        // 查询本类是否正在进行解析(防止死循环)
        String runing = runMap.get(clazz.getName());

        // 返回null则标识这个类正在运行，则不对该类再进行解析
        if (ObjectUtil.isNotEmpty(runing)) {
            return null;
        }

        // 获取该类和该类所有父类的属性
        while (clazz != null) {
            runMap.put(clazz.getName(), clazz.getName());
            // 获取类中的所有字段
            Field[] declaredFields = ClassUtil.getDeclaredFields(clazz);
            for (Field declaredField : declaredFields) {
                // 获取字段元数据
                FieldMetadata fieldDescription = getFieldMetadata(declaredField);
                fieldDescriptions.add(fieldDescription);
            }

            runMap.remove(clazz.getName());

            // 获取本类的父类
            clazz = clazz.getSuperclass();
        }
        return fieldDescriptions.stream().collect(toCollection(() -> new TreeSet<>(comparing(FieldMetadata::getFieldName))));
    }

    /**
     * 获取字段详情
     *
     * @param declaredField 字段
     * @return {@link cn.stylefeng.roses.kernel.scanner.api.pojo.resource.FieldMetadata}
     * @author majianguo
     * @date 2021/6/23 上午10:03
     **/
    private static FieldMetadata getFieldMetadata(Field declaredField) {
        FieldMetadata fieldDescription = new FieldMetadata();

        // 生成uuid，给前端用
        fieldDescription.setMetadataId(IdUtil.fastSimpleUUID());

        // 获取字段的名称
        String name = declaredField.getName();
        fieldDescription.setFieldName(name);

        // 获取字段的类型
        Class<?> declaredFieldType = declaredField.getType();
        fieldDescription.setFieldClassType(declaredFieldType.getSimpleName());
        fieldDescription.setFieldClassPath(declaredFieldType.getName());

        // 如果字段类型是Object类型，则遍历Object类型里的字段
        if (BaseRequest.class.isAssignableFrom(declaredFieldType)) {
            fieldDescription.setGenericFieldMetadata(getClassFieldDescription(declaredFieldType));
        } else if (List.class.isAssignableFrom(declaredFieldType)) {
            // 如果是集合类型，则处理集合里面的字段
            Type genericType = declaredField.getGenericType();

            // 得到泛型里的class类型对象
            Class<?> actualTypeArgument;
            // 处理List没写泛型的情况
            if (genericType instanceof ParameterizedType) {
                ParameterizedType pt = (ParameterizedType)genericType;
                Type typeArgument = pt.getActualTypeArguments()[0];

                // 处理List<?>这种情况
                if (!(typeArgument instanceof WildcardType)) {
                    actualTypeArgument = typeArgument.getClass();
                } else {
                    actualTypeArgument = Object.class;
                }
            } else {
                actualTypeArgument = Object.class;
            }

            // 基本类型处理
            if (actualTypeArgument.isPrimitive() || "java.lang".equals(actualTypeArgument.getPackage().getName()) || "java.util".equals(actualTypeArgument.getPackage().getName())) {
                FieldMetadata item = new FieldMetadata();
                item.setMetadataId(IdUtil.fastSimpleUUID());
                item.setFieldName(actualTypeArgument.getName());
                item.setFieldClassType(actualTypeArgument.getTypeName());
                // 填充字段的中文名称
                ChineseDescription chineseDescription = declaredField.getAnnotation(ChineseDescription.class);
                if (chineseDescription != null) {
                    item.setChineseName(chineseDescription.value());
                }
                fieldDescription.setGenericFieldMetadata(Collections.singleton(item));
            } else {
                fieldDescription.setGenericFieldMetadata(getClassFieldDescription(actualTypeArgument));
            }

        } else if (Collection.class.isAssignableFrom(declaredFieldType)) {
            // 如果是泛型类型，遍历泛泛型的class类型里的字段
            Class<?> genericClass = TypeUtil.getClass(TypeUtil.getTypeArgument(TypeUtil.getType(declaredField)));
            if (BaseRequest.class.isAssignableFrom(genericClass)) {
                fieldDescription.setGenericFieldMetadata(getClassFieldDescription(genericClass));
            }
        }
        // 获取字段的所有注解
        Annotation[] annotations = declaredField.getAnnotations();
        if (annotations != null && annotations.length > 0) {

            // 设置字段的所有注解
            fieldDescription.setAnnotations(annotationsToStrings(annotations));

            // 遍历字段上的所有注解，找到带groups属性的，按group分类组装注解
            Map<String, Set<String>> groupAnnotations = new HashMap<>();
            for (Annotation annotation : annotations) {
                Class<?>[] validateGroupsClasses = invokeAnnotationMethodIgnoreError(annotation, "groups", Class[].class);
                if (validateGroupsClasses != null) {
                    for (Class<?> validateGroupsClass : validateGroupsClasses) {
                        addGroupValidateAnnotation(annotation, validateGroupsClass, groupAnnotations);
                    }
                }
            }
            // 设置分组注解
            fieldDescription.setGroupValidationMessage(groupAnnotations);

            // 填充字段的中文名称
            ChineseDescription chineseDescription = declaredField.getAnnotation(ChineseDescription.class);
            if (chineseDescription != null) {
                fieldDescription.setChineseName(chineseDescription.value());
            }
        }
        return fieldDescription;
    }

    /**
     * 调用注解上的某个方法，并获取结果，忽略异常
     *
     * @author fengshuonan
     * @date 2020/12/8 17:13
     */
    private static <T> T invokeAnnotationMethodIgnoreError(Annotation apiResource, String methodName, Class<T> resultType) {
        try {
            Class<? extends Annotation> annotationType = apiResource.annotationType();
            Method method = annotationType.getMethod(methodName);
            return (T)method.invoke(apiResource);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            // 忽略异常
        }
        return null;
    }

    /**
     * 将字段上的分组注解添加到对应的组中
     *
     * @param fieldAnnotation     字段上的注解
     * @param validateGroupsClass 校验分组
     * @param groupAnnotations    分组注解集合
     * @author fengshuonan
     * @date 2020/12/8 19:12
     */
    private static void addGroupValidateAnnotation(Annotation fieldAnnotation, Class<?> validateGroupsClass, Map<String, Set<String>> groupAnnotations) {
        Set<String> annotations = groupAnnotations.get(validateGroupsClass.getSimpleName());
        if (annotations == null) {
            annotations = new HashSet<>();
        }
        String messageTip = invokeAnnotationMethodIgnoreError(fieldAnnotation, "message", String.class);
        annotations.add(messageTip);
        groupAnnotations.put(validateGroupsClass.getSimpleName(), annotations);
    }

    /**
     * 注解转化为string名称
     * <p>
     * 例如：
     * NotBlack注解 > NotBlack
     *
     * @author fengshuonan
     * @date 2020/12/9 13:39
     */
    private static Set<String> annotationsToStrings(Annotation[] annotations) {
        Set<String> strings = new HashSet<>();

        if (annotations == null || annotations.length == 0) {
            return strings;
        }

        for (Annotation annotation : annotations) {
            strings.add(annotation.annotationType().getSimpleName());
        }

        return strings;
    }
}
