package cn.stylefeng.roses.kernel.system.api.pojo.theme;

import cn.stylefeng.roses.kernel.rule.annotation.ChineseDescription;
import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 系统主题模板属性参数
 *
 * @author xixiaowei
 * @date 2021/12/17 10:42
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysThemeTemplateFieldRequest extends BaseRequest {

    /**
     * 主键ID
     */
    @NotNull(message = "主键ID不能为空", groups = {edit.class, delete.class, detail.class})
    @ChineseDescription("主键ID")
    private Long fieldId;

    /**
     * 模板ID
     */
    @ChineseDescription("模板ID")
    private Long templateId;

    /**
     * 属性名称
     */
    @NotBlank(message = "属性名称不能为空", groups = {add.class, edit.class})
    @ChineseDescription("属性名称")
    private String fieldName;

    @NotNull(message = "属性编码不能为空", groups = {add.class, edit.class})
    @ChineseDescription("属性编码")
    private String fieldCode;

    /**
     * 属性展示类型(字典维护)
     */
    @NotBlank(message = "属性展示类型不能为空", groups = {add.class, edit.class})
    @ChineseDescription("属性展示类型")
    private String fieldType;

    /**
     * 是否必填：Y-必填，N-非必填
     */
    @ChineseDescription("是否必填")
    private Character fieldRequired;

    /**
     * 属性长度
     */
    @ChineseDescription("属性长度")
    private Integer fieldLength;

    /**
     * 属性描述
     */
    @ChineseDescription("属性描述")
    private String fieldDescription;
}
