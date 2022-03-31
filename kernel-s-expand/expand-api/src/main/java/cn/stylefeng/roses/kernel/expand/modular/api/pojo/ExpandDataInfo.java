package cn.stylefeng.roses.kernel.expand.modular.api.pojo;

import lombok.Data;

import java.util.Map;

/**
 * 拓展数据信息
 *
 * @author fengshuonan
 * @date 2022/3/31 21:25
 */
@Data
public class ExpandDataInfo {

    /**
     * 拓展id
     */
    private Long expandId;

    /**
     * 动态表单数据
     */
    private Map<String, Object> expandData;

    /**
     * 主键字段值
     */
    private Long primaryFieldValue;

}
