package cn.stylefeng.roses.kernel.system.api.pojo.theme;

import cn.stylefeng.roses.kernel.rule.annotation.ChineseDescription;
import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 系统主题模板参数
 *
 * @author xixiaowei
 * @date 2021/12/17 16:19
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysThemeTemplateRequest extends BaseRequest {

    /**
     * 主键ID
     */
    @NotNull(message = "主键ID不能为空", groups = {edit.class, delete.class, detail.class, updateStatus.class})
    @ChineseDescription("主键ID")
    private Long templateId;

    /**
     * 主题模板名称
     */
    @NotBlank(message = "主题模板名称不能为空", groups = {add.class, edit.class})
    @ChineseDescription("主题模板名称")
    private String templateName;

    /**
     * 主题模板编码
     */
    @NotNull(message = "主题模板编码不能为空", groups = {add.class, edit.class})
    @ChineseDescription("主题模板编码")
    private String templateCode;

    /**
     * 主题模板类型：1-系统类型，2-业务类型
     */
    @NotNull(message = "主题模板类型不能为空", groups = {add.class, edit.class})
    @ChineseDescription("主题模板类型")
    private Short templateType;

    /**
     * 主题模板启用状态：Y-启用，N-禁用
     */
    @ChineseDescription("主题模板启用状态")
    private Character statusFlag;
}
