package cn.stylefeng.roses.kernel.system.api.pojo.theme;

import cn.stylefeng.roses.kernel.rule.annotation.ChineseDescription;
import lombok.Data;

/**
 * 系统主题模板详细查询返回的数据封装
 *
 * @author xixiaowei
 * @date 2021/12/17 15:20
 */
@Data
public class SysThemeTemplateDataDTO {

    /**
     * 主键ID
     */
    @ChineseDescription("主键id")
    private Long templateId;

    /**
     * 主题模板名称
     */
    @ChineseDescription("主题模板名称")
    private String templateName;

    /**
     * 主题模板编码
     */
    @ChineseDescription("主题模板编码")
    private String templateCode;

    /**
     * 主键ID
     */
    @ChineseDescription("属性字段id")
    private Long fieldId;

    /**
     * 属性名称
     */
    @ChineseDescription("属性名称")
    private String fieldName;

    /**
     * 属性编码
     */
    @ChineseDescription("想属性编码")
    private String fieldCode;

    /**
     * 属性展示类型
     */
    @ChineseDescription("属性展示类型")
    private String fieldType;

    /**
     * 是否必填：Y-必填，N-非必填
     */
    @ChineseDescription("是否必填：Y-必填，N-非必填")
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
