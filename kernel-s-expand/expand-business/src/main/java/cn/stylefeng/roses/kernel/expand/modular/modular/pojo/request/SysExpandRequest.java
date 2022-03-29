package cn.stylefeng.roses.kernel.expand.modular.modular.pojo.request;

import cn.stylefeng.roses.kernel.rule.annotation.ChineseDescription;
import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
    @NotNull(message = "主键id不能为空", groups = {edit.class, delete.class})
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
    private String expandCode;

    /**
     * 状态：1-启用，2-禁用
     */
    @ChineseDescription("状态：1-启用，2-禁用")
    private Integer expandStatus;

    /**
     * 业务主键id字段名，例如：user_id
     */
    @ChineseDescription("业务主键id字段名，例如：user_id")
    private String primaryFieldName;

}
