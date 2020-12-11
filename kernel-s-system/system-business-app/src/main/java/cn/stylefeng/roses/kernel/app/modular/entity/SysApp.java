package cn.stylefeng.roses.kernel.app.modular.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统应用表
 *
 * @author fengshuonan
 * @date 2020/11/24 21:05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_app")
public class SysApp extends BaseEntity {

    /**
     * 主键id
     */
    @TableId("id")
    private Long id;

    /**
     * 应用名称
     */
    @TableField("name")
    private String name;

    /**
     * 编码
     */
    @TableField("code")
    private String code;

    /**
     * 是否默认激活（Y-是，N-否）
     */
    @TableField("active_flag")
    private String activeFlag;

    /**
     * 状态（字典 1启用 2禁用）
     */
    @TableField("status_flag")
    private Integer statusFlag;

    /**
     * 是否删除（Y-已删除，N-未删除）
     */
    @TableField("del_flag")
    private String delFlag;

}
