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

    /**
     * 正在进行解析的Class
     */
    private final static Map<String, String> RUN_MAP = new HashMap<>(2);

    /**
     * 获取ResponseData类型的字段描述，参数clazz是ResponseData的data属性的类型
     *
     * @author fengshuonan
     * @date 2022/1/7 15:37
     */
    public static Set<FieldMetadata> getResponseDataClassFieldDescription(Class<?> clazz) {

        HashSet<FieldMetadata> responseDataFieldMetadata = new HashSet<>();

        // 创建ResponseData几个固定的属性描述
        FieldMetadata success = new FieldMetadata();
        success.setMetadataId(IdUtil.fastSimpleUUID());
        success.setChineseName("请求是否成功");
        success.setFieldClassType("Boolean");
        success.setFieldClassPath("java.lang.Boolean");
        success.setFieldName("success");
        responseDataFieldMetadata.add(success);

        // 创建ResponseData几个固定的属性描述
        FieldMetadata code = new FieldMetadata();
        code.setMetadataId(IdUtil.fastSimpleUUID());
        code.setChineseName("响应状态码");
        code.setFieldClassType("String");
        code.setFieldClassPath("java.lang.String");
        code.setFieldName("code");
        responseDataFieldMetadata.add(code);

        // 创建ResponseData几个固定的属性描述
        FieldMetadata message = new FieldMetadata();
        message.setMetadataId(IdUtil.fastSimpleUUID());
        message.setChineseName("响应信息");
        message.setFieldClassType("String");
        message.setFieldClassPath("java.lang.String");
        message.setFieldName("message");
        responseDataFieldMetadata.add(message);

        // 创建ResponseData几个固定的属性描述
        FieldMetadata data = new FieldMetadata();
        data.setMetadataId(IdUtil.fastSimpleUUID());
        data.setChineseName("响应对象");
        data.setFieldClassType(clazz.getSimpleName());
        data.setFieldClassPath(clazz.getName());
        data.setFieldName("data");

        // 设置data属性类型具体的元数据
        Set<FieldMetadata> dataFieldMetadata = getClassFieldDescription(clazz);
        data.setGenericFieldMetadata(dataFieldMetadata);

        responseDataFieldMetadata.add(data);

        return responseDataFieldMetadata;
    }

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
        String runing = RUN_MAP.get(clazz.getName());

        // 返回null则标识这个类正在运行，则不对该类再进行解析
        if (ObjectUtil.isNotEmpty(runing)) {
            return null;
        }

        // 获取该类和该类所有父类的属性
        while (clazz != null) {
            RUN_MAP.put(clazz.getName(), clazz.getName());
            // 获取类中的所有字段
            Field[] declaredFields = ClassUtil.getDeclaredFields(clazz);
            for (Field declaredField : declaredFields) {
                // 获取字段元数据
                FieldMetadata fieldDescription = getFieldMetadata(declaredField);
                fieldDescriptions.add(fieldDescription);
            }

            RUN_MAP.remove(clazz.getName());

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

        // 解析字段类
        fieldClassParsing(declaredField, fieldDescription, declaredFieldType);

        return fieldDescription;
    }

    /**
     * 字段class类解析
     *
     * @author majianguo
     * @date 2022/1/6 16:09
     **/
    private static void fieldClassParsing(Field declaredField, FieldMetadata fieldDescription, Class<?> declaredFieldType) {

        // 获取泛型
        Type genericType = declaredField.getGenericType();

        // 基本类型处理
        if (isPrimitive(declaredFieldType)) {

            // 如果是泛型，则把结果放到集合内
            if (genericType instanceof ParameterizedType) {

                // 读取所有泛型信息解析
                ParameterizedType type = (ParameterizedType) genericType;
                Set<FieldMetadata> metadataSet = null;
                for (Type actualTypeArgument : type.getActualTypeArguments()) {
                    Class<?> aClass = TypeUtil.getClass(actualTypeArgument);
                    // 泛型如果是基本类型则解析基本类型信息，否则什么也不干，最后有泛型的判断解析，这里只处理基本类型
                    if (isPrimitive(aClass)) {
                        FieldMetadata fieldMetadata = baseTypeParsing(declaredField, aClass);
                        if (metadataSet == null) {
                            metadataSet = new LinkedHashSet<>();
                        }
                        metadataSet.add(fieldMetadata);
                    }
                }
                fieldDescription.setGenericFieldMetadata(metadataSet);
            }
        }

        // 处理数组类型
        else if (declaredFieldType.getComponentType() != null) {
            declaredFieldType = declaredFieldType.getComponentType();
            if (!isPrimitive(declaredFieldType)) {
                fieldDescription.setGenericFieldMetadata(getClassFieldDescription(declaredFieldType));
            }
        }

        // 其他类型
        else {
            fieldDescription.setGenericFieldMetadata(getClassFieldDescription(declaredFieldType));
        }

        // 获取字段的所有注解
        parsingAnnotation(declaredField, fieldDescription);

        // 该类解析完以后，判断一下会不会是集合嵌套集合的情况，集合嵌套集合就继续下一层解析
        if (genericType instanceof ParameterizedType && ((ParameterizedType) genericType).getActualTypeArguments()[0] instanceof ParameterizedType) {
            ParameterizedType type = (ParameterizedType) ((ParameterizedType) genericType).getActualTypeArguments()[0];
            Type typeArgument = type.getActualTypeArguments()[0];
            Class<?> typeArgumentItem = TypeUtil.getClass(typeArgument);
            fieldDescription.getGenericFieldMetadata().iterator().next().setGenericFieldMetadata(getClassFieldDescription(typeArgumentItem));
        }

        // 如果是泛型，则解析一下所有泛型
        else if (genericType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericType;
            Set<FieldMetadata> fieldMetadataSet = new LinkedHashSet<>();
            for (Type actualTypeArgument : parameterizedType.getActualTypeArguments()) {
                Class<?> typeClass = TypeUtil.getClass(actualTypeArgument);
                if (!isPrimitive(typeClass)) {
                    Set<FieldMetadata> classFieldDescription = getClassFieldDescription(typeClass);
                    if (null != classFieldDescription) {
                        fieldMetadataSet.addAll(classFieldDescription);
                    }
                } else {
                    FieldMetadata fieldMetadata = baseTypeParsing(declaredField, typeClass);
                    fieldMetadataSet.add(fieldMetadata);
                }
                fieldDescription.setGenericFieldMetadata(fieldMetadataSet);
            }
        }
    }

    /**
     * 解析所有注解
     *
     * @author majianguo
     * @date 2022/1/7 18:26
     **/
    private static void parsingAnnotation(Field declaredField, FieldMetadata fieldDescription) {
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
    }

    /**
     * 基础类型解析
     *
     * @author majianguo
     * @date 2022/1/6 16:09
     **/
    private static FieldMetadata baseTypeParsing(Field declaredField, Class<?> actualTypeArgument) {
        FieldMetadata item = new FieldMetadata();
        item.setMetadataId(IdUtil.fastSimpleUUID());
        item.setFieldName(actualTypeArgument.getName());
        item.setFieldClassType(actualTypeArgument.getTypeName());
        // 填充字段的中文名称
        ChineseDescription chineseDescription = declaredField.getAnnotation(ChineseDescription.class);
        if (null != chineseDescription) {
            item.setChineseName(chineseDescription.value());
        }
        return item;
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
            return (T) method.invoke(apiResource);
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

    /**
     * 判断类是否是基本类型
     *
     * @return {@link boolean}
     * @author majianguo
     * @date 2022/1/7 10:42
     **/
    private static boolean isPrimitive(Class<?> clazz) {
        boolean isPrimitive;
        try {
            // 基本类型
            if (clazz.isPrimitive()) {
                isPrimitive = true;
            } else {
                // 基本类型包装类
                isPrimitive = ((Class<?>) clazz.getField("TYPE").get(null)).isPrimitive();
            }
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            isPrimitive = false;
        }

        Package packageObj = clazz.getPackage();
        if (!isPrimitive && ObjectUtil.isNotEmpty(packageObj)) {
            isPrimitive = "java.lang".equals(packageObj.getName()) || "java.util".equals(packageObj.getName()) || "java.math".equals(packageObj.getName());
        }

        return isPrimitive;

    }
    
}
