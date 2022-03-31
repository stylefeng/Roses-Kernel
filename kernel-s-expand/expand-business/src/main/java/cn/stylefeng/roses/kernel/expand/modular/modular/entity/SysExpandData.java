package cn.stylefeng.roses.kernel.expand.modular.modular.entity;

import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
import cn.stylefeng.roses.kernel.rule.annotation.ChineseDescription;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 业务拓展-具体数据实例类
 *
 * @author fengshuonan
 * @date 2022/03/29 23:47
 */
@TableName("sys_expand_data")
@Data
@EqualsAndHashCode(callSuper = true)
public class SysExpandData extends BaseEntity {

    /**
     * 主键id
     */
    @TableId(value = "expand_data_id", type = IdType.ASSIGN_ID)
    @ChineseDescription("主键id")
    private Long expandDataId;

    /**
     * 拓展业务id
     */
    @TableField("expand_id")
    @ChineseDescription("拓展业务id")
    private Long expandId;

    /**
     * 业务主键id
     */
    @TableField("primary_field_value")
    @ChineseDescription("业务主键id")
    private Long primaryFieldValue;

    /**
     * 拓展业务具体数据
     */
    @TableField("expand_data")
    @ChineseDescription("拓展业务具体数据")
    private String expandData;

    /**
     * 拓展业务信息
     */
    @ChineseDescription("拓展业务信息")
    private transient SysExpand expandInfo;

    /**
     * 字段元数据信息列表
     */
    @ChineseDescription("字段元数据信息列表")
    private transient List<SysExpandField> fieldInfoList;

}
