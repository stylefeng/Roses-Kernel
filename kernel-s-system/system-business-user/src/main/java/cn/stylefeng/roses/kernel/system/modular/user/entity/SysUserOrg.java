package cn.stylefeng.roses.kernel.system.modular.user.entity;

import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 企业员工表，用户-组织机构的关联
 *
 * @author fengshuonan
 * @date 2020/11/04 11:05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user_org")
public class SysUserOrg extends BaseEntity {

    /**
     * 主键
     */
    @TableId("user_org_id")
    private Long userOrgId;

    /**
     * 用户id
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 所属机构id
     */
    @TableField("org_id")
    private Long orgId;

    /**
     * 职位id
     */
    @TableField("position_id")
    private Long positionId;

}
