package cn.stylefeng.roses.kernel.scanner.api.factory;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.IdUtil;
import cn.stylefeng.roses.kernel.scanner.api.annotation.field.ChineseDescription;
import cn.stylefeng.roses.kernel.scanner.api.enums.FieldMetadataTypeEnum;
import cn.stylefeng.roses.kernel.scanner.api.enums.FieldTypeEnum;
import cn.stylefeng.roses.kernel.scanner.api.pojo.resource.FieldMetadata;
import cn.stylefeng.roses.kernel.scanner.api.util.ClassReflectUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * 字段信息创建工具
 *
 * @author fengshuonan
 * @date 2022/1/13 13:49
 */
@Slf4j
public class FieldMetadataFactory {

    private List<String> entityScanPackage = ListUtil.list(false, "cn.stylefeng");

    /**
     * 通过传入的类型（Class或ParameterizedType）进行字段校验，解析出字段的元数据
     *
     * @param type 类型
     * @return 传入类型的字段元数据信息
     * @author fengshuonan
     * @date 2022/1/13 13:51
     */
    public FieldMetadata beginCreateFieldMetadata(Type type) {
        if (type instanceof Class) {
            return this.processClass((Class<?>) type);
        } else if (type instanceof ParameterizedType) {
            return this.processParameterizedType((ParameterizedType) type);
        }
        // 其他情况，暂不处理
        return new FieldMetadata();
    }

    /**
     * 处理class类型
     *
     * @author fengshuonan
     * @date 2022/1/13 13:56
     */
    private FieldMetadata processClass(Class<?> clazz) {

        // 如果是基本类型
        if (ClassUtil.isSimpleValueType(clazz)) {
            return createClassMetadata(clazz, FieldTypeEnum.BASIC);
        }

        // 如果是基本数组类型
        else if (clazz.isArray()) {
            FieldMetadata classMetadata = createClassMetadata(clazz, FieldTypeEnum.ARRAY);
            // 数组元素真实类型
            Class<?> componentType = clazz.getComponentType();
            FieldMetadata fieldMetadata = processClass(componentType);
            classMetadata.addOneSubFieldMetadata(fieldMetadata);
            return classMetadata;
        }

        // 如果是集合类型，纯集合类型，不带泛型
        else if (Collection.class.isAssignableFrom(clazz)) {
            return createClassMetadata(clazz, FieldTypeEnum.ARRAY);
        }

        // 如果是实体对象类型
        else if (this.ensureEntityFlag(clazz)) {
            FieldMetadata classMetadata = createClassMetadata(clazz, FieldTypeEnum.OBJECT);

            // 获取每个字段的元数据信息，开始组装
            Field[] fields = clazz.getFields();
            HashSet<FieldMetadata> fieldMetadataSet = new HashSet<>();
            for (Field field : fields) {
                // 获取字段的具体属性
                FieldMetadata fieldMetadata = this.createFieldMetadata(field);
                fieldMetadataSet.add(fieldMetadata);
            }
            classMetadata.setGenericFieldMetadata(fieldMetadataSet);
        }

        // 其他类型，暂不处理
        else {
            log.info("未知类型的处理，打印出类的信息如下：{}", clazz.toGenericString());
        }

        return null;
    }

    /**
     * 处理带泛型的类型
     *
     * @author fengshuonan
     * @date 2022/1/13 13:56
     */
    private FieldMetadata processParameterizedType(ParameterizedType parameterizedType) {

        // 获取泛型的主体
        Type rawType = parameterizedType.getRawType();

        // 获取泛型的具体参数，只处理带一个泛型的
        Type actualTypeArgument = parameterizedType.getActualTypeArguments()[0];

        return null;
    }

    /**
     * 判断类类型是否是扫描的包范围之内
     *
     * @author fengshuonan
     * @date 2022/1/13 17:49
     */
    private boolean ensureEntityFlag(Class<?> clazz) {
        for (String packageName : entityScanPackage) {
            if (clazz.getName().startsWith(packageName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取类类型的类别
     *
     * @author fengshuonan
     * @date 2022/1/14 0:25
     */
    private FieldTypeEnum getClassFieldType(Class<?> clazz) {
        // 如果是基本类型
        if (ClassUtil.isSimpleValueType(clazz)) {
            return FieldTypeEnum.BASIC;
        }

        // 如果是基本数组类型
        else if (clazz.isArray()) {
            return FieldTypeEnum.ARRAY;
        }

        // 如果是集合类型，纯集合类型，不带泛型
        else if (Collection.class.isAssignableFrom(clazz)) {
            return FieldTypeEnum.ARRAY;
        }

        // 如果是实体对象类型
        else if (this.ensureEntityFlag(clazz)) {
            return FieldTypeEnum.OBJECT;
        }

        // 其他类型，暂不处理
        else {
            log.info("未知类型的处理，打印出类的信息如下：{}", clazz.toGenericString());
            return FieldTypeEnum.OTHER;
        }
    }

    /**
     * 创建类或数组的元数据
     *
     * @author fengshuonan
     * @date 2022/1/13 18:06
     */
    private FieldMetadata createClassMetadata(Class<?> clazz, FieldTypeEnum fieldTypeEnum) {
        FieldMetadata fieldMetadataItem = new FieldMetadata();
        // 设置唯一id
        fieldMetadataItem.setMetadataId(IdUtil.fastSimpleUUID());
        // 设置字段中文含义
        fieldMetadataItem.setChineseName(clazz.getSimpleName());
        // 设置字段类类型
        fieldMetadataItem.setFieldClassType(clazz.getSimpleName());
        // 设置类的全路径
        fieldMetadataItem.setFieldClassPath(clazz.getName());
        // 设置对应字段名称，无字段名称则填写类的简称
        fieldMetadataItem.setFieldName(clazz.getSimpleName());
        // 设置是否带泛型
        fieldMetadataItem.setGenericFieldMetadataType(FieldMetadataTypeEnum.FIELD.getCode());
        // 设置字段类型，基本、数组、还是object
        fieldMetadataItem.setFieldType(fieldTypeEnum.getCode());
        // 设置字段
        return fieldMetadataItem;
    }

    /**
     * 创建类内字段的元数据
     *
     * @author fengshuonan
     * @date 2022/1/13 18:06
     */
    private FieldMetadata createFieldMetadata(Field field) {
        FieldMetadata fieldMetadataItem = new FieldMetadata();
        // 设置唯一id
        fieldMetadataItem.setMetadataId(IdUtil.fastSimpleUUID());
        // 设置字段中文含义
        ChineseDescription annotation = field.getAnnotation(ChineseDescription.class);
        if (annotation != null) {
            fieldMetadataItem.setChineseName(annotation.value());
        }
        // 设置字段类类型
        Class<?> fieldType = field.getType();
        fieldMetadataItem.setFieldClassType(fieldType.getSimpleName());
        // 设置类的全路径
        fieldMetadataItem.setFieldClassPath(fieldType.getName());
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
        fieldMetadataItem.setFieldType(getClassFieldType(fieldType).getCode());
        // 处理本字段的子字段信息
        fieldMetadataItem.setGenericFieldMetadata(getFieldSubMetadataSet(field));
        // 设置字段
        return fieldMetadataItem;
    }

    /**
     * 获取一个字段的子类型集合，如果改字段没有子类型则返回null即可
     *
     * @author fengshuonan
     * @date 2022/1/14 0:45
     */
    private static Set<FieldMetadata> getFieldSubMetadataSet(Field field) {

        Type genericType = field.getGenericType();

        if (genericType instanceof Class<?>) {

        } else if (genericType instanceof ParameterizedType) {

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

    public static void main(String[] args) {
    }

}
