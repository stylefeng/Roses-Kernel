package cn.stylefeng.roses.kernel.expand.modular.api.pojo;

import lombok.Data;

/**
 * 拓展字段信息
 *
 * @author fengshuonan
 * @date 2022/4/1 9:46
 */
@Data
public class ExpandFieldInfo {

    /**
     * 拓展id
     */
    private Long expandId;

    /**
     * 字段中文名
     */
    private String fieldName;

    /**
     * 字段英文名
     */
    private String fieldCode;

}
