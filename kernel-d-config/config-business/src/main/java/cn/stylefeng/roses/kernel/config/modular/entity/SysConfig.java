package cn.stylefeng.roses.kernel.config.modular.entity;

import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 参数配置
 * </p>
 *
 * @author stylefeng
 * @date 2019/6/20 13:44
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_config")
public class SysConfig extends BaseEntity {

    /**
     * 主键
     */
    @TableId(value = "config_id", type = IdType.ASSIGN_ID)
    private Long configId;

    /**
     * 名称
     */
    @TableField("config_name")
    private String configName;

    /**
     * 编码
     */
    @TableField("config_code")
    private String configCode;

    /**
     * 属性值
     */
    @TableField("config_value")
    private String configValue;

    /**
     * 是否是系统参数：Y-是，N-否
     */
    @TableField("sys_flag")
    private String sysFlag;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 状态：1-正常，2停用
     */
    @TableField("status_flag")
    private Integer statusFlag;

    /**
     * 常量所属分类的编码，来自于“常量的分类”字典
     */
    @TableField("group_code")
    private String groupCode;

    /**
     * 是否删除：Y-被删除，N-未删除
     */
    @TableField(value = "del_flag", fill = FieldFill.INSERT)
    private String delFlag;

}
