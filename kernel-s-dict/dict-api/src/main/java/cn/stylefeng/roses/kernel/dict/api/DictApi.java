package cn.stylefeng.roses.kernel.dict.api;

/**
 * 字典模块对外提供的api，方便其他模块直接调用
 *
 * @author fengshuonan
 * @date 2020/10/29 14:45
 */
public interface DictApi {

    /**
     * 获取字典名称通过code
     *
     * @author fengshuonan
     * @date 2020/12/25 14:14
     */
    String getDictNameByDictCode(String dictCode);

    /**
     * 通过字典类型编码和字典编码获取名称
     *
     * @param dictTypeCode 字典类型编码
     * @param dictCode 字典编码
     * @return 字典名称
     * @author liuhanqing
     * @date 2021/1/16 23:18
     */
    String getDictName(String dictTypeCode, String dictCode);

}
