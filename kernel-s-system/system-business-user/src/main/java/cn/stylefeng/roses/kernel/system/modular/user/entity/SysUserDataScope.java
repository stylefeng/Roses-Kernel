package cn.stylefeng.roses.kernel.system.modular.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 系统用户数据范围表
 *
 * @author luojie
 * @date 2020/11/6 09:46
 */
@Data
@TableName("sys_user_data_scope")
public class SysUserDataScope {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户id
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 机构id
     */
    @TableField("organization_id")
    private Long organizationId;
}
