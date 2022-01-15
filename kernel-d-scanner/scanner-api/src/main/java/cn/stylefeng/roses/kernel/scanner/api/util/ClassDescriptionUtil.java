package cn.stylefeng.roses.kernel.scanner.api.util;

import cn.hutool.core.util.IdUtil;
import cn.stylefeng.roses.kernel.scanner.api.enums.FieldMetadataTypeEnum;
import cn.stylefeng.roses.kernel.scanner.api.enums.FieldTypeEnum;
import cn.stylefeng.roses.kernel.scanner.api.pojo.resource.FieldMetadata;

import java.lang.reflect.ParameterizedType;

/**
 * 类的元数据描述
 *
 * @author fengshuonan
 * @date 2022/1/14 10:59
 */
public class ClassDescriptionUtil {

    /**
     * 创建针对类的基础描述
     *
     * @author fengshuonan
     * @date 2022/1/13 18:06
     */
    public static FieldMetadata createClassMetadata(Class<?> clazz, FieldTypeEnum fieldTypeEnum) {
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
        fieldMetadataItem.setFieldName(null);
        // 设置是否带泛型
        fieldMetadataItem.setGenericFieldMetadataType(FieldMetadataTypeEnum.FIELD.getCode());
        // 设置字段类型，基本、数组、还是object
        fieldMetadataItem.setFieldType(fieldTypeEnum.getCode());
        // 设置字段
        return fieldMetadataItem;
    }

    /**
     * 创建针对类的基础描述
     *
     * @author fengshuonan
     * @date 2022/1/13 18:06
     */
    public static FieldMetadata createParameterizedMetadata(ParameterizedType parameterizedType, FieldTypeEnum fieldTypeEnum) {
        Class<?> rawType = (Class<?>) parameterizedType.getRawType();
        return createClassMetadata(rawType, fieldTypeEnum);
    }

}
