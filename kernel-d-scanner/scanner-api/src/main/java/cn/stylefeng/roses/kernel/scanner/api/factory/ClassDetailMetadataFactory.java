package cn.stylefeng.roses.kernel.scanner.api.factory;

import cn.stylefeng.roses.kernel.scanner.api.constants.ScannerConstants;
import cn.stylefeng.roses.kernel.scanner.api.context.MetadataContext;
import cn.stylefeng.roses.kernel.scanner.api.enums.FieldTypeEnum;
import cn.stylefeng.roses.kernel.scanner.api.pojo.resource.FieldMetadata;
import cn.stylefeng.roses.kernel.scanner.api.util.ClassDescriptionUtil;
import cn.stylefeng.roses.kernel.scanner.api.util.ClassTypeUtil;
import cn.stylefeng.roses.kernel.scanner.api.util.FieldDescriptionUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 字段信息创建工具
 *
 * @author fengshuonan
 * @date 2022/1/13 13:49
 */
@Slf4j
public class ClassDetailMetadataFactory {

    /**
     * 根据传入的类型，解析出这个类型的所有子字段类型
     *
     * @param fieldType 需要被解析的对象的类型，可以是class也可以是泛型
     * @param uuid      随机字符串，保证唯一性，用来标识从开始到结束一个context周期内的一系列解析
     * @author fengshuonan
     * @date 2022/1/14 14:31
     */
    public static Set<FieldMetadata> createFieldDetailMetadataSet(Type fieldType, String uuid) {

        // 获取参数的类型枚举
        FieldTypeEnum classFieldType = ClassTypeUtil.getClassFieldType(fieldType);

        // 设置响应结果
        Set<FieldMetadata> fieldMetadata = null;

        // 根据字段类型的不同进行响应的处理
        switch (classFieldType) {
            case BASIC:
                // 基本类型不需要填充子类型信息
                fieldMetadata = null;
                break;
            case BASE_ARRAY:
                // 如果是简单数组类型，则描述一下这个基本类型的信息
                Class<?> baseArrayFieldClazz = (Class<?>) fieldType;
                Class<?> baseArrayComponentType = baseArrayFieldClazz.getComponentType();
                FieldMetadata baseMetadata = ClassDescriptionUtil.createClassMetadata(baseArrayComponentType, FieldTypeEnum.BASIC, uuid);
                fieldMetadata = new LinkedHashSet<>();
                fieldMetadata.add(baseMetadata);
                break;
            case ARRAY_WITH_OBJECT:
                // 如果是带实体的数组，则加上实体对应的字段信息
                Class<?> objArrayClass = (Class<?>) fieldType;
                Class<?> objArrayComponentType = objArrayClass.getComponentType();
                fieldMetadata = ClassDetailMetadataFactory.createFieldDetailMetadataSet(objArrayComponentType, uuid);
                break;
            case BASE_COLLECTION:
                // 如果是基础集合，因为不确定集合的内容，所以使用Object来描述一下集合的具体内容
                FieldMetadata collectionFieldMetadata = ClassDescriptionUtil.createClassMetadata(Object.class, FieldTypeEnum.OBJECT, uuid);
                fieldMetadata = new LinkedHashSet<>();
                fieldMetadata.add(collectionFieldMetadata);
                break;
            case COLLECTION_WITH_OBJECT:
                // 如果是集合里带的具体实体对象，则描述一下具体实体的数据结构
                ParameterizedType collectionParameterizedType = (ParameterizedType) fieldType;
                Type[] actualTypeArguments = collectionParameterizedType.getActualTypeArguments();
                fieldMetadata = ClassDetailMetadataFactory.createFieldDetailMetadataSet(actualTypeArguments[0], uuid);
                break;
            case OBJECT:
                // 如果是实体对象，则描述实体对象的所有字段信息
                Class<?> objectClass = (Class<?>) fieldType;
                Field[] fields = objectClass.getDeclaredFields();
                fieldMetadata = new LinkedHashSet<>();

                // 在处理Object中所有字段之前，将当前父类放进context，所有子字段不能含有父类的类型，否则会递归
                MetadataContext.addClassRecord(uuid, objectClass.getName());

                for (Field field : fields) {
                    FieldMetadata fieldInfo;

                    // 判断字段是否是基础字段例如serialVersionUID，或者delFlag等字段
                    if (ScannerConstants.DONT_PARSE_FIELD.contains(field.getName())) {
                        continue;
                    }

                    // 判断该实体是否被解析过，防止无限递归解析实体
                    if (MetadataContext.ensureFieldClassHaveParse(uuid, field.getGenericType())) {
                        fieldInfo = FieldDescriptionUtil.createBasicMetadata(field, uuid);
                    } else {
                        fieldInfo = FieldDescriptionUtil.createFieldMetadata(field, uuid);
                    }
                    fieldMetadata.add(fieldInfo);
                }
                break;
            case OBJECT_WITH_GENERIC:
                // 如果是带泛型的Object实体类型
                ParameterizedType objWithGenericParameterizedType = (ParameterizedType) fieldType;
                // 先获取主体类型
                Type rawType = objWithGenericParameterizedType.getRawType();
                // 获取具体泛型
                Type genericType = objWithGenericParameterizedType.getActualTypeArguments()[0];

                // 判断带泛型的实体，有没有进行做字段解析，如果解析过，则跳过
                String totalName = fieldType.getTypeName() + genericType.getTypeName();
                if (MetadataContext.ensureFieldClassHaveParse(uuid, totalName)) {
                    return null;
                }
                MetadataContext.addClassRecord(uuid, totalName);

                // 获取主体的所有字段信息
                fieldMetadata = getEntityWithGenericFieldMetadataList(rawType, genericType, uuid);
            default:
        }

        return fieldMetadata;
    }

    /**
     * 获取实体带泛型类型的字段填充详情，例如PageResult<SysUser>这种字段
     *
     * @author fengshuonan
     * @date 2022/1/14 18:51
     */
    public static Set<FieldMetadata> getEntityWithGenericFieldMetadataList(Type fieldType, Type genericType, String uuid) {
        if (fieldType instanceof Class<?>) {
            Class<?> clazz = (Class<?>) fieldType;
            // 获取主类型的所有属性
            Set<FieldMetadata> fieldDetailMetadataSet = createFieldDetailMetadataSet(clazz, uuid);
            if (fieldDetailMetadataSet == null) {
                return null;
            }
            for (FieldMetadata fieldMetadata : fieldDetailMetadataSet) {
                // 如果是带泛型集合，如下情况List<T>，又或是直接 T 这种形式
                if (FieldTypeEnum.COLLECTION_WITH_OBJECT.getCode().equals(fieldMetadata.getFieldType())
                        || FieldTypeEnum.WITH_UNKNOWN_GENERIC.getCode().equals(fieldMetadata.getFieldType())) {
                    // 设置这个字段的子字段描述
                    fieldMetadata.setGenericFieldMetadata(createFieldDetailMetadataSet(genericType, uuid));
                }

                // 如果T在携带在一个实体类上，例如ResponseData<T>这种形式
                else if (FieldTypeEnum.OBJECT_WITH_GENERIC.getCode().equals(fieldMetadata.getFieldType())) {
                    // 设置这个字段的子字段描述
                    Set<FieldMetadata> current = null;
                    try {
                        current = getEntityWithGenericFieldMetadataList(Class.forName(fieldMetadata.getFieldClassPath()), genericType, uuid);
                    } catch (ClassNotFoundException e) {
                        log.error("类无法找到" + fieldMetadata.getFieldClassPath(), e);
                        continue;
                    }
                    fieldMetadata.setGenericFieldMetadata(current);
                }

                // 如果是T这种形式，应该将当前fieldMetadata的类型改为泛型的类型，例如参数genericType是List时候
                if (FieldTypeEnum.WITH_UNKNOWN_GENERIC.getCode().equals(fieldMetadata.getFieldType())) {
                    FieldTypeEnum classFieldType = ClassTypeUtil.getClassFieldType(genericType);
                    fieldMetadata.setFieldType(classFieldType.getCode());
                }
            }

            return fieldDetailMetadataSet;
        } else {
            log.debug("带泛型的实体主题rawType非Class类型" + fieldType);
            return null;
        }
    }

}
