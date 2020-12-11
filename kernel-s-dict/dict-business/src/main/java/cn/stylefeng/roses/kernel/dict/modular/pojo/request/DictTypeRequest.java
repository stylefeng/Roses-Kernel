package cn.stylefeng.roses.kernel.dict.modular.pojo.request;

import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 字典类型，请求参数封装
 *
 * @author fengshuonan
 * @date 2020/10/30 11:05
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DictTypeRequest extends BaseRequest {

    /**
     * 字典类型id
     */
    @NotNull(message = "id不能为空，请检查id参数", groups = {edit.class, delete.class, detail.class, updateStatus.class})
    private Long id;

    /**
     * 字典类型： 1-业务类型，2-系统类型，参考 DictTypeClassEnum
     */
    @NotNull(message = "字典类型不能为空", groups = {add.class, edit.class})
    private Integer dictTypeClass;

    /**
     * 字典类型编码
     */
    @NotBlank(message = "字典类型编码不能为空", groups = {add.class, edit.class})
    private String dictTypeCode;

    /**
     * 字典类型名称
     */
    @NotBlank(message = "字典类型名称不能为空", groups = {add.class, edit.class})
    private String dictTypeName;

    /**
     * 字典类型描述
     */
    private String dictTypeDesc;

    /**
     * 字典类型的状态：1-启用，2-禁用，参考 StatusEnum
     */
    @NotNull(message = "状态不能为空", groups = {updateStatus.class})
    private Integer dictTypeStatus;

}
