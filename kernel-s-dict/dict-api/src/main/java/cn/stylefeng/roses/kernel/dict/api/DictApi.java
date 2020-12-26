package cn.stylefeng.roses.kernel.dict.api;

/**
 * 字典模块对外提供的api，方便其他模块直接调用
 *
 * @author fengshuonan
 * @date 2020/10/29 14:45
 */
public interface DictApi {

    /**
     * 获取字典名称通过id
     *
     * @author fengshuonan
     * @date 2020/12/25 14:14
     */
    String getDictNameByDictCode(String dictCode);

}
