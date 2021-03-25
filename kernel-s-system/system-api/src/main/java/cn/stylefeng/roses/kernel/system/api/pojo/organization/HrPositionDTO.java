package cn.stylefeng.roses.kernel.system.api.pojo.organization;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 系统职位表
 *
 * @author fengshuonan
 * @date 2020/11/04 11:05
 */
@Data
public class HrPositionDTO {

    /**
     * 主键
     */
    private Long positionId;

    /**
     * 职位名称
     */
    private String positionName;

    /**
     * 职位编码
     */
    private String positionCode;

    /**
     * 排序
     */
    private BigDecimal positionSort;

    /**
     * 状态：1-启用，2-禁用
     */
    private Integer statusFlag;

    /**
     * 职位备注
     */
    private String positionRemark;

    /**
     * 删除标记：Y-已删除，N-未删除
     */
    private String delFlag;

}
