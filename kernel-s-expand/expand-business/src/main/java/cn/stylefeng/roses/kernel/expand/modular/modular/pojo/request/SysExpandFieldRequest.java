package cn.stylefeng.roses.kernel.expand.modular.modular.pojo.request;

import cn.stylefeng.roses.kernel.rule.annotation.ChineseDescription;
import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 业务拓展-字段信息封装类
 *
 * @author fengshuonan
 * @date 2022/03/29 23:47
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysExpandFieldRequest extends BaseRequest {

    /**
     * 主键id
     */
    @NotNull(message = "主键id不能为空", groups = {edit.class, delete.class})
    @ChineseDescription("主键id")
    private Long fieldId;

    /**
     * 对应拓展业务的主键id
     */
    @NotNull(message = "对应拓展业务的主键id不能为空", groups = {add.class, edit.class, page.class})
    @ChineseDescription("对应拓展业务的主键id")
    private Long expandId;

    /**
     * 字段中文名称，例如：身份证号
     */
    @NotBlank(message = "字段中文名称，例如：身份证号不能为空", groups = {add.class, edit.class})
    @ChineseDescription("字段中文名称，例如：身份证号")
    private String fieldName;

    /**
     * 字段英文名称，例如：idCard
     */
    @NotBlank(message = "字段英文名称，例如：idCard不能为空", groups = {add.class, edit.class})
    @ChineseDescription("字段英文名称，例如：idCard")
    private String fieldCode;

    /**
     * 字段类型：1-字符串类型，2-数字类型，3-字典类型
     */
    @ChineseDescription("字段类型：1-字符串类型，2-数字类型，3-字典类型")
    @NotNull(message = "字典类型不能为空", groups = {add.class, edit.class})
    private Integer fieldType;

    /**
     * 是否必填：Y-必填，N-非必填
     */
    @ChineseDescription("是否必填：Y-必填，N-非必填")
    @NotBlank(message = "是否必填不能为空", groups = {add.class, edit.class})
    private String fieldRequired;

    /**
     * 属性值长度，用于数字类型
     */
    @ChineseDescription("属性值长度，用于数字类型")
    private Integer fieldLength;

    /**
     * 字典类型编码，用于字典类型
     */
    @ChineseDescription("字典类型编码，用于字典类型")
    private String fieldDictTypeCode;

    /**
     * 列表是否显示：Y-显示，N-不显示
     */
    @ChineseDescription("列表是否显示：Y-显示，N-不显示")
    @NotBlank(message = "列表是否显示不能为空", groups = {add.class, edit.class})
    private String listShowFlag;

}
