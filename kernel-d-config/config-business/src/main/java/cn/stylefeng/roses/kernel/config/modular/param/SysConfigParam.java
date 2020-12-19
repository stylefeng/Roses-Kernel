package cn.stylefeng.roses.kernel.config.modular.param;

import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import cn.stylefeng.roses.kernel.validator.validators.flag.FlagValue;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 系统参数配置参数
 *
 * @author fengshuonan
 * @date 2020/4/14 10:18
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysConfigParam extends BaseRequest {

    /**
     * 主键
     */
    @NotNull(message = "configId不能为空", groups = {edit.class, delete.class, detail.class})
    private Long configId;

    /**
     * 名称
     */
    @NotBlank(message = "名称不能为空", groups = {add.class, edit.class})
    private String configName;

    /**
     * 编码
     */
    @NotBlank(message = "编码不能为空", groups = {add.class, edit.class})
    private String configCode;

    /**
     * 配置值
     */
    @NotBlank(message = "配置值不能为空", groups = {add.class, edit.class})
    private String configValue;

    /**
     * 是否是系统参数：Y-是，N-否
     */
    @NotBlank(message = "是否是系统参数不能为空，请检查sysFlag参数", groups = {add.class, edit.class})
    @FlagValue(message = "是否是系统参数格式错误，正确格式应该Y或者N，请检查sysFlag参数", groups = {add.class, edit.class})
    private String sysFlag;

    /**
     * 备注
     */
    private String remark;

    /**
     * 状态：1-正常，2停用
     */
    private Integer statusFlag;

    /**
     * 常量所属分类的编码，来自于“常量的分类”字典
     */
    @NotBlank(message = "量所属分类的编码不能为空", groups = {add.class, edit.class})
    private String groupCode;

}
