package cn.stylefeng.roses.kernel.scanner.api.context;

import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.scanner.api.enums.FieldTypeEnum;
import cn.stylefeng.roses.kernel.scanner.api.enums.ParamTypeEnum;
import cn.stylefeng.roses.kernel.scanner.api.util.ClassTypeUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 字段处理时的当前上下文
 * <p>
 * 记录每次的解析类的元数据时，处理过哪些实体类型，防止解析字段过程中的无限递归解析实体
 *
 * @author fengshuonan
 * @date 2022/1/15 10:36
 */
public class MetadataContext {

    /**
     * 第一个key是唯一id，第二个key是当前context下解析过的实体类，用来标识针对某一次的类元数据解析
     */
    public static ConcurrentHashMap<String, Set<String>> META_DATA_CLASS_COUNT_CONTEXT = new ConcurrentHashMap<>();

    /**
     * 第一个key是唯一id，第二个key是当前context下处理的参数类型
     */
    public static ConcurrentHashMap<String, ParamTypeEnum> META_DATA_PARAM_TYPE_CONTEXT = new ConcurrentHashMap<>();

    /**
     * 第一个key是唯一id，第二个key是当前context下处理的参数名称
     */
    public static ConcurrentHashMap<String, String> META_DATA_PARAM_NAME_CONTEXT = new ConcurrentHashMap<>();

    /**
     * 添加对某次解析的类记录
     *
     * @author fengshuonan
     * @date 2022/1/15 10:47
     */
    public static void addClassRecord(String uuid, String classPathName) {
        Set<String> classRecords = META_DATA_CLASS_COUNT_CONTEXT.get(uuid);
        if (classRecords == null) {
            classRecords = new HashSet<>();
        }
        classRecords.add(classPathName);
        META_DATA_CLASS_COUNT_CONTEXT.put(uuid, classRecords);
    }

    /**
     * 判断某个类是否已经被解析过
     *
     * @author fengshuonan
     * @date 2022/1/15 10:50
     */
    public static boolean ensureFieldClassHaveParse(String uuid, String classPathName) {
        Set<String> classRecords = META_DATA_CLASS_COUNT_CONTEXT.get(uuid);
        if (classRecords == null) {
            return false;
        }
        return classRecords.contains(classPathName);
    }

    /**
     * 判断某个类是否已经被解析过
     *
     * @author fengshuonan
     * @date 2022/1/15 10:50
     */
    public static boolean ensureFieldClassHaveParse(String uuid, Type genericType) {
        Set<String> classRecords = META_DATA_CLASS_COUNT_CONTEXT.get(uuid);
        if (classRecords != null) {
            // 获取字段类型，如果是数组，collection带实体的，需要获取真实的实体类型
            FieldTypeEnum classFieldType = ClassTypeUtil.getClassFieldType(genericType);

            // 如果是对象类型，直接判断
            if (classFieldType.equals(FieldTypeEnum.OBJECT)) {
                return classRecords.contains(((Class<?>) genericType).getName());
            }

            // 数组类型，则获取数组的实体
            if (classFieldType.equals(FieldTypeEnum.ARRAY_WITH_OBJECT)) {
                Class<?> originClass = (Class<?>) genericType;
                return classRecords.contains(originClass.getComponentType().getName());
            }

            // 集合类型，获取集合的真实类型
            if (classFieldType.equals(FieldTypeEnum.COLLECTION_WITH_OBJECT)) {
                // 获取泛型
                ParameterizedType parameterizedType = (ParameterizedType) genericType;
                Type actualTypeArgument = parameterizedType.getActualTypeArguments()[0];
                return ensureFieldClassHaveParse(uuid, actualTypeArgument);
            }
        }
        return false;
    }

    /**
     * 添加本次解析的参数类型
     *
     * @author fengshuonan
     * @date 2022/1/20 13:50
     */
    public static void addParamTypeMetadata(String uuid, ParamTypeEnum paramTypeEnum) {
        META_DATA_PARAM_TYPE_CONTEXT.put(uuid, paramTypeEnum);
    }

    /**
     * 获取本次解析的参数类型
     *
     * @author fengshuonan
     * @date 2022/1/20 13:50
     */
    public static ParamTypeEnum getParamTypeMetadata(String uuid) {
        return META_DATA_PARAM_TYPE_CONTEXT.get(uuid);
    }

    /**
     * 设置本次解析的参数名称
     *
     * @author fengshuonan
     * @date 2022/1/24 15:09
     */
    public static void addParameterName(String uuid, String paramName) {
        META_DATA_PARAM_NAME_CONTEXT.put(uuid, paramName);
    }

    /**
     * 获取本次解析的参数名称
     *
     * @author fengshuonan
     * @date 2022/1/24 15:09
     */
    public static String getParamName(String uuid) {
        return META_DATA_PARAM_NAME_CONTEXT.get(uuid);
    }


    /**
     * 清空当前解析的记录
     *
     * @author fengshuonan
     * @date 2022/1/15 10:49
     */
    public static void cleanContext() {
        META_DATA_CLASS_COUNT_CONTEXT.clear();
        META_DATA_PARAM_TYPE_CONTEXT.clear();
        META_DATA_PARAM_NAME_CONTEXT.clear();
    }

    /**
     * 清空指定过程的缓存
     *
     * @author fengshuonan
     * @date 2022/1/15 10:49
     */
    public static void cleanContext(String uuid) {
        if (StrUtil.isEmpty(uuid)) {
            return;
        }
        META_DATA_CLASS_COUNT_CONTEXT.remove(uuid);
        META_DATA_PARAM_TYPE_CONTEXT.remove(uuid);
        META_DATA_PARAM_NAME_CONTEXT.remove(uuid);
    }

}
