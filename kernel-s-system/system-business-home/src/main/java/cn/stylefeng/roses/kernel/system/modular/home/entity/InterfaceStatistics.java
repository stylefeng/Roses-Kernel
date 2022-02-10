package cn.stylefeng.roses.kernel.system.modular.home.entity;

import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
import cn.stylefeng.roses.kernel.rule.annotation.ChineseDescription;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 接口统计实体
 *
 * @author xixiaowei
 * @date 2022/2/10 9:59
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("interface_statistics")
public class InterfaceStatistics extends BaseEntity {

    /**
     * 主键ID
     */
    @TableId(value = "statistics_id", type = IdType.ASSIGN_ID)
    @ChineseDescription("主键ID")
    private Long statisticsId;

    /**
     * 接口名称
     */
    @TableField("interface_name")
    @ChineseDescription("接口名称")
    private String interfaceName;

    /**
     * 接口路径
     */
    @TableField("interface_url")
    @ChineseDescription("接口路径")
    private String interfaceUrl;

    /**
     * 访问次数
     */
    @TableField("request_count")
    @ChineseDescription("访问次数")
    private Integer requestCount;
}
