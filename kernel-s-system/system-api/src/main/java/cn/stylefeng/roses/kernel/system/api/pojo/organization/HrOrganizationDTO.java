package cn.stylefeng.roses.kernel.system.api.pojo.organization;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 系统组织机构表
 *
 * @author fengshuonan
 * @date 2020/11/04 11:05
 */
@Data
public class HrOrganizationDTO {

    /**
     * 主键
     */
    private Long orgId;

    /**
     * 父id，一级节点父id是0
     */
    private Long orgParentId;

    /**
     * 父ids
     */
    private String orgPids;

    /**
     * 组织名称
     */
    private String orgName;

    /**
     * 组织编码
     */
    private String orgCode;

    /**
     * 排序
     */
    private BigDecimal orgSort;

    /**
     * 状态：1-启用，2-禁用
     */
    private Integer statusFlag;

    /**
     * 组织机构描述
     */
    private String orgRemark;

    /**
     * 删除标记（Y-已删除，N-未删除）
     */
    private String delFlag;

}
