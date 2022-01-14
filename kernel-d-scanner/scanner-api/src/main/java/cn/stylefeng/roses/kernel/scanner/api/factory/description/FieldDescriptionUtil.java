package cn.stylefeng.roses.kernel.scanner.api.factory.description;

import cn.hutool.core.util.IdUtil;
import cn.stylefeng.roses.kernel.rule.annotation.ChineseDescription;
import cn.stylefeng.roses.kernel.scanner.api.enums.FieldMetadataTypeEnum;
import cn.stylefeng.roses.kernel.scanner.api.enums.FieldTypeEnum;
import cn.stylefeng.roses.kernel.scanner.api.factory.ClassDetailMetadataFactory;
import cn.stylefeng.roses.kernel.scanner.api.pojo.resource.FieldMetadata;
import cn.stylefeng.roses.kernel.scanner.api.util.ClassReflectUtil;
import cn.stylefeng.roses.kernel.scanner.api.util.ClassTypeUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 字段的元数据描述
 *
 * @author fengshuonan
 * @date 2022/1/14 10:59
 */
public class FieldDescriptionUtil {

    /**
     * 创建类内字段的元数据
     *
     * @author fengshuonan
     * @date 2022/1/13 18:06
     */
    public static FieldMetadata createFieldMetadata(Field field) {
        FieldMetadata fieldMetadataItem = new FieldMetadata();
        // 设置唯一id
        fieldMetadataItem.setMetadataId(IdUtil.fastSimpleUUID());
        // 设置字段中文含义
        ChineseDescription annotation = field.getAnnotation(ChineseDescription.class);
        if (annotation != null) {
            fieldMetadataItem.setChineseName(annotation.value());
        }
        // 设置字段类类型
        Class<?> classType = field.getType();
        fieldMetadataItem.setFieldClassType(classType.getSimpleName());
        // 设置类的全路径
        fieldMetadataItem.setFieldClassPath(classType.getName());
        // 设置对应字段名称
        fieldMetadataItem.setFieldName(field.getName());
        // 处理注解信息
        parsingAnnotation(field, fieldMetadataItem);
        // 设置是否带泛型
        Type genericType = field.getGenericType();
        if (genericType instanceof ParameterizedType) {
            fieldMetadataItem.setGenericFieldMetadataType(FieldMetadataTypeEnum.GENERIC.getCode());
        } else {
            fieldMetadataItem.setGenericFieldMetadataType(FieldMetadataTypeEnum.FIELD.getCode());
        }
        // 设置字段类型，基本、数组、还是object
        FieldTypeEnum classFieldType = ClassTypeUtil.getClassFieldType(genericType);
        fieldMetadataItem.setFieldType(classFieldType.getCode());
        // 根据情况，获取字段的具体子数据描述
        Set<FieldMetadata> fieldDetailMetadataSet = ClassDetailMetadataFactory.createFieldDetailMetadataSet(genericType);
        fieldMetadataItem.setGenericFieldMetadata(fieldDetailMetadataSet);
        return fieldMetadataItem;
    }

    /**
     * 解析字段上的注解
     *
     * @author fengshuonan
     * @date 2022/1/14 11:57
     */
    private static void parsingAnnotation(Field declaredField, FieldMetadata fieldDescription) {
        Annotation[] annotations = declaredField.getAnnotations();
        if (annotations != null && annotations.length > 0) {

            // 设置字段的所有注解
            fieldDescription.setAnnotations(ClassReflectUtil.annotationsToStrings(annotations));

            // 遍历字段上的所有注解，找到带groups属性的，按group分类组装注解
            Map<String, Set<String>> groupAnnotations = new HashMap<>();
            for (Annotation annotation : annotations) {
                Class<?>[] validateGroupsClasses = ClassReflectUtil.invokeAnnotationMethodIgnoreError(annotation, "groups", Class[].class);
                if (validateGroupsClasses != null) {
                    for (Class<?> validateGroupsClass : validateGroupsClasses) {
                        ClassReflectUtil.addGroupValidateAnnotation(annotation, validateGroupsClass, groupAnnotations);
                    }
                }
            }
            // 设置分组注解
            fieldDescription.setGroupValidationMessage(groupAnnotations);
        }
    }

}
