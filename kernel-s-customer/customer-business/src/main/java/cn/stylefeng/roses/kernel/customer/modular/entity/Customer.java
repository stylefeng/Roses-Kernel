package cn.stylefeng.roses.kernel.customer.modular.entity;

import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
import cn.stylefeng.roses.kernel.scanner.api.annotation.field.ChineseDescription;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * C端用户表实例类
 *
 * @author fengshuonan
 * @date 2021/06/07 11:40
 */
@TableName("toc_customer")
@Data
@EqualsAndHashCode(callSuper = true)
public class Customer extends BaseEntity {

    /**
     * 主键id
     */
    @TableId(value = "customer_id", type = IdType.ASSIGN_ID)
    @ChineseDescription("主键id")
    private Long customerId;

    /**
     * 账号
     */
    @TableField("account")
    @ChineseDescription("账号")
    private String account;

    /**
     * 密码
     */
    @TableField("password")
    @ChineseDescription("密码")
    private String password;

    /**
     * 昵称（显示名称）
     */
    @TableField("nick_name")
    @ChineseDescription("昵称（显示名称）")
    private String nickName;

    /**
     * 邮箱
     */
    @TableField("email")
    @ChineseDescription("邮箱")
    private String email;

    /**
     * 手机
     */
    @TableField("telephone")
    @ChineseDescription("手机")
    private String telephone;

    /**
     * 用户头像（文件表id）
     */
    @TableField("avatar")
    @ChineseDescription("用户头像（文件表id）")
    private Long avatar;

    /**
     * 用户头像的文件全名
     */
    @TableField("avatar_object_name")
    @ChineseDescription("用户头像的文件全名")
    private String avatarObjectName;

    /**
     * 用户积分
     */
    @TableField("score")
    @ChineseDescription("用户积分")
    private Integer score;

}