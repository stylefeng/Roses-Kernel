package cn.stylefeng.roses.kernel.system.modular.organization.entity;

import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 系统职位表
 *
 * @author fengshuonan
 * @date 2020/11/04 11:07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("hr_position")
public class HrPosition extends BaseEntity {

    /**
     * 主键
     */
    @TableId("position_id")
    private Long positionId;

    /**
     * 职位名称
     */
    @TableField("position_name")
    private String positionName;

    /**
     * 职位编码
     */
    @TableField("position_code")
    private String positionCode;

    /**
     * 排序
     */
    @TableField("position_sort")
    private BigDecimal positionSort;

    /**
     * 状态：1-启用，2-禁用
     */
    @TableField("status_flag")
    private Integer statusFlag;

    /**
     * 职位备注
     */
    @TableField("position_remark")
    private String positionRemark;

    /**
     * 删除标记：Y-已删除，N-未删除
     */
    @TableField("del_flag")
    private String delFlag;

}
