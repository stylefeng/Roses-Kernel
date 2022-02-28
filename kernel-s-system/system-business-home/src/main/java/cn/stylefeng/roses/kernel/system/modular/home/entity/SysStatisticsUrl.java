package cn.stylefeng.roses.kernel.system.modular.home.entity;

import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 常用功能列表实例类
 *
 * @author fengshuonan
 * @date 2022/02/10 21:17
 */
@TableName("sys_statistics_url")
@Data
@EqualsAndHashCode(callSuper = true)
public class SysStatisticsUrl extends BaseEntity {

    /**
     * 主键ID
     */
    @TableId(value = "stat_url_id", type = IdType.ASSIGN_ID)
    private Long statUrlId;

    /**
     * 被统计名称
     */
    @TableField("stat_name")
    private String statName;

    /**
     * 被统计菜单ID
     */
    @TableField("stat_menu_id")
    private Long statMenuId;

    /**
     * 被统计的URL
     */
    @TableField("stat_url")
    private String statUrl;

    /**
     * 是否常驻显示，Y-是，N-否
     */
    @TableField("always_show")
    private String alwaysShow;

}
