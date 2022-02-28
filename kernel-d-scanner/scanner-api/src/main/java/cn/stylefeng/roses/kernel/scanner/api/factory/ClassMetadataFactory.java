package cn.stylefeng.roses.kernel.scanner.api.factory;

import cn.stylefeng.roses.kernel.scanner.api.enums.FieldTypeEnum;
import cn.stylefeng.roses.kernel.scanner.api.pojo.resource.FieldMetadata;
import cn.stylefeng.roses.kernel.scanner.api.util.ClassDescriptionUtil;
import cn.stylefeng.roses.kernel.scanner.api.util.ClassTypeUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Set;

/**
 * 字段信息创建工具，一般用这个类作为类解析的入口
 *
 * @author fengshuonan
 * @date 2022/1/13 13:49
 */
@Slf4j
public class ClassMetadataFactory {

    /**
     * 通过传入的类型（Class或ParameterizedType）进行字段校验，解析出字段的元数据
     *
     * @param type 需要被解析的对象的类型，可以是class也可以是泛型
     * @param uuid 随机字符串，保证唯一性，用来标识从开始到结束一个context周期内的一系列解析
     * @return 传入类型的字段元数据信息
     * @author fengshuonan
     * @date 2022/1/13 13:51
     */
    public static FieldMetadata beginCreateFieldMetadata(Type type, String uuid) {

        // 获取类型的枚举
        FieldTypeEnum classFieldType = ClassTypeUtil.getClassFieldType(type);

        // 设置响应结果
        FieldMetadata fieldMetadata = null;

        // 组装类的基本信息
        if (type instanceof Class<?>) {
            Class<?> clazz = (Class<?>) type;

            // 创建类型的基本信息
            fieldMetadata = ClassDescriptionUtil.createClassMetadata(clazz, classFieldType, uuid);

            // 补充类型的子信息
            Set<FieldMetadata> fieldDetailMetadataSet = ClassDetailMetadataFactory.createFieldDetailMetadataSet(clazz, uuid);
            fieldMetadata.setGenericFieldMetadata(fieldDetailMetadataSet);
        }

        // 如果是带泛型的
        else if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;

            // 创建类型的基本信息
            fieldMetadata = ClassDescriptionUtil.createParameterizedMetadata(parameterizedType, classFieldType, uuid);

            // 补充类型的子信息
            Set<FieldMetadata> fieldDetailMetadataSet = ClassDetailMetadataFactory.createFieldDetailMetadataSet(type, uuid);
            fieldMetadata.setGenericFieldMetadata(fieldDetailMetadataSet);
        }

        // 其他情况
        else {
            log.debug("未知类型的处理，既不是class也不是ParameterizedType，打印出类的信息如下：{}", type.getTypeName());
        }

        return fieldMetadata;
    }

}
