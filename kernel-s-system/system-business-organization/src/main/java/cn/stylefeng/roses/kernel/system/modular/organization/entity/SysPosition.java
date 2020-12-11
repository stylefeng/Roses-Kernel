package cn.stylefeng.roses.kernel.system.modular.organization.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
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
@TableName("sys_position")
public class SysPosition extends BaseEntity {

    /**
     * 主键
     */
    @TableId("id")
    private Long id;

    /**
     * 职位名称
     */
    @TableField("name")
    private String name;

    /**
     * 职位编码
     */
    @TableField("code")
    private String code;

    /**
     * 排序
     */
    @TableField("sort")
    private BigDecimal sort;

    /**
     * 状态（1-启用，2-禁用）
     */
    @TableField("status_flag")
    private Integer statusFlag;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 删除标记（Y-已删除，N-未删除）
     */
    @TableField("del_flag")
    private String delFlag;

}
