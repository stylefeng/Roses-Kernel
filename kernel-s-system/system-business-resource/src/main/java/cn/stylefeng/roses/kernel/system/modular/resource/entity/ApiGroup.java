package cn.stylefeng.roses.kernel.system.modular.resource.entity;

import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
import cn.stylefeng.roses.kernel.scanner.api.annotation.field.ChineseDescription;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 接口分组实例类
 *
 * @author majianguo
 * @date 2021/05/21 15:03
 */
@TableName("api_group")
@Data
@EqualsAndHashCode(callSuper = true)
public class ApiGroup extends BaseEntity {

    /**
     * 资源分组主键
     */
    @TableId(value = "group_id", type = IdType.ASSIGN_ID)
    @ChineseDescription("资源分组主键")
    private Long groupId;

    /**
     * 分组名称
     */
    @TableField("group_name")
    @ChineseDescription("分组名称")
    private String groupName;

    /**
     * 分组父ID
     */
    @TableField("group_pid")
    @ChineseDescription("分组父ID")
    private Long groupPid;

    /**
     * 分组父ID集合
     */
    @TableField("group_pids")
    @ChineseDescription("分组父ID集合")
    private String groupPids;

    /**
     * 分组排序
     */
    @TableField("group_sort")
    @ChineseDescription("分组排序")
    private java.math.BigDecimal groupSort;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 创建人
     */
    @TableField("create_user")
    private Long createUser;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;

    /**
     * 更新人
     */
    @TableField("update_user")
    private Long updateUser;

}