package cn.stylefeng.roses.kernel.system.modular.resource.entity;

import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * 接口信息实例类
 *
 * @author majianguo
 * @date 2021/05/21 15:03
 */
@TableName("api_resource")
@Data
@EqualsAndHashCode(callSuper = true)
public class ApiResource extends BaseEntity {

    /**
     * 接口信息主键
     */
    @TableId(value = "api_resource_id", type = IdType.ASSIGN_ID)
    private Long apiResourceId;

    /**
     * 资源分组数据主键
     */
    @TableField("group_id")
    private Long groupId;

    /**
     * 请求方式：GET，POST
     */
    @TableField("request_method")
    private String requestMethod;

    /**
     * 接口自定义名称，区别于sys_resource表的名称
     */
    @TableField("api_alias")
    private String apiAlias;

    /**
     * 资源唯一编码,关联sys_resource表的code
     */
    @TableField("resource_code")
    private String resourceCode;

    /**
     * 上次接口调用的头部信息
     */
    @TableField("last_request_header")
    private String lastRequestHeader;

    /**
     * 上次接口调用的参数内容
     */
    @TableField("last_request_content")
    private String lastRequestContent;

    /**
     * 上次接口调用的响应内容
     */
    @TableField("last_response_content")
    private String lastResponseContent;

    /**
     * 资源排序
     */
    @TableField("resource_sort")
    private java.math.BigDecimal resourceSort;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 创建用户
     */
    @TableField("create_user")
    private Long createUser;

    /**
     * 修改时间
     */
    @TableField("update_time")
    private Date updateTime;

    /**
     * 修改用户
     */
    @TableField("update_user")
    private Long updateUser;

    /**
     * 该资源所有字段
     */
    @TableField(exist = false)
    private List<ApiResourceField> apiResourceFieldList;

}