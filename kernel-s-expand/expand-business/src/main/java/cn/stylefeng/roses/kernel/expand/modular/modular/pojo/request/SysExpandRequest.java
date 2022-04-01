package cn.stylefeng.roses.kernel.expand.modular.modular.pojo.request;

import cn.stylefeng.roses.kernel.rule.annotation.ChineseDescription;
import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import cn.stylefeng.roses.kernel.validator.api.validators.status.StatusValue;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 业务拓展封装类
 *
 * @author fengshuonan
 * @date 2022/03/29 23:47
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysExpandRequest extends BaseRequest {

    /**
     * 主键id
     */
    @NotNull(message = "主键id不能为空", groups = {edit.class, delete.class, updateStatus.class})
    @ChineseDescription("主键id")
    private Long expandId;

    /**
     * 拓展业务名称
     */
    @ChineseDescription("拓展业务名称")
    private String expandName;

    /**
     * 拓展业务唯一编码
     */
    @ChineseDescription("拓展业务唯一编码")
    @NotBlank(message = "拓展编码不能为空", groups = getByExpandCode.class)
    private String expandCode;

    /**
     * 状态：1-启用，2-禁用
     */
    @ChineseDescription("状态：1-启用，2-禁用")
    @NotNull(message = "状态不能为空", groups = {updateStatus.class})
    @StatusValue(groups = updateStatus.class)
    private Integer expandStatus;

    /**
     * 主业务表，例如：sys_user
     */
    @ChineseDescription("主业务表，例如：sys_user")
    private String primaryTableName;

    /**
     * 业务主键id字段名，例如：user_id
     */
    @ChineseDescription("业务主键id字段名，例如：user_id")
    private String primaryFieldName;

    /**
     * 业务主键id字段名驼峰法，例如：userId
     */
    @ChineseDescription("业务主键id字段名驼峰法，例如：userId")
    private String primaryFieldCamel;

    /**
     * 业务主键id的值
     */
    @ChineseDescription("业务主键id的值")
    private Long primaryFieldValue;

    /**
     * 获取业务元数据信息
     *
     * @author fengshuonan
     * @date 2022/3/31 15:25
     */
    public @interface getByExpandCode {
    }


}
