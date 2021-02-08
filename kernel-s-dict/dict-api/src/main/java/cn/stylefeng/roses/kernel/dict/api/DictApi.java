package cn.stylefeng.roses.kernel.dict.api;

import cn.stylefeng.roses.kernel.rule.pojo.dict.SimpleDict;

import java.util.List;

/**
 * 字典模块对外提供的api，方便其他模块直接调用
 *
 * @author fengshuonan
 * @date 2020/10/29 14:45
 */
public interface DictApi {

    /**
     * 通过字典类型编码和字典编码获取名称
     *
     * @param dictTypeCode 字典类型编码
     * @param dictCode     字典编码
     * @return 字典名称
     * @author liuhanqing
     * @date 2021/1/16 23:18
     */
    String getDictName(String dictTypeCode, String dictCode);

    /**
     * 根据字典类型编码获取所有的字典
     *
     * @param dictTypeCode 字典类型编码
     * @return 字典的集合
     * @author fengshuonan
     * @date 2021/1/27 22:13
     */
    List<SimpleDict> getDictDetailsByDictTypeCode(String dictTypeCode);

    /**
     * 删除字典，通过dictId
     *
     * @param dictId 字典id
     * @author fengshuonan
     * @date 2021/1/30 10:03
     */
    void deleteByDictId(Long dictId);

}
