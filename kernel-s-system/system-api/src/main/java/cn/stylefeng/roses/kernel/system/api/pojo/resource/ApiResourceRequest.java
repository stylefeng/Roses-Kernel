package cn.stylefeng.roses.kernel.system.api.pojo.resource;

import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import cn.stylefeng.roses.kernel.scanner.api.annotation.field.ChineseDescription;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * 接口信息封装类
 *
 * @author majianguo
 * @date 2021/05/21 15:03
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ApiResourceRequest extends BaseRequest {

    /**
     * 接口信息主键
     */
    @NotNull(message = "接口信息主键不能为空", groups = {detail.class, reset.class, edit.class, delete.class, record.class})
    @ChineseDescription("接口信息主键")
    private Long apiResourceId;

    /**
     * 资源分组数据主键
     */
    @NotNull(message = "资源分组数据主键不能为空", groups = {add.class, edit.class, reset.class})
    @ChineseDescription("资源分组数据主键")
    private Long groupId;

    /**
     * 请求头
     */
    @ChineseDescription("请求头")
    private Map<String, String> lastRequestHeader;

    /**
     * 请求url：完整路径，包含http协议头
     */
    @NotBlank(message = "请求路径不能为空", groups = {record.class})
    @ChineseDescription("请求url：完整路径，包含http协议头")
    private String requestUrl;

    /**
     * 请求方式：GET，POST
     */
    @NotBlank(message = "请求方式不能为空", groups = {edit.class})
    @ChineseDescription("请求方式：GET，POST")
    private String requestMethod;

    /**
     * 接口自定义名称，区别于sys_resource表的名称
     */
    @NotBlank(message = "接口自定义名称，区别于sys_resource表的名称不能为空", groups = {edit.class})
    @ChineseDescription("接口自定义名称，区别于sys_resource表的名称")
    private String apiAlias;

    /**
     * 资源唯一编码,关联sys_resource表的code
     */
    @NotBlank(message = "资源唯一编码,关联sys_resource表的code不能为空", groups = {add.class, reset.class, allField.class})
    @ChineseDescription("资源唯一编码,关联sys_resource表的code")
    private String resourceCode;

    /**
     * 上次接口调用的参数内容
     */
    @ChineseDescription("上次接口调用的参数内容")
    private String lastRequestContent;

    /**
     * 上次接口调用的响应内容
     */
    @ChineseDescription("上次接口调用的响应内容")
    private String lastResponseContent;

    /**
     * 资源排序
     */
    @NotNull(message = "接口自定义名称不能为空", groups = {edit.class})
    @ChineseDescription("资源排序")
    private java.math.BigDecimal resourceSort;

    /**
     * 资源字段列表
     */
    private List<ApiResourceFieldRequest> apiResourceFieldRequestList;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 创建用户
     */
    private Long createUser;

    /**
     * 修改时间
     */
    private String updateTime;

    /**
     * 修改用户
     */
    private Long updateUser;

    /**
     * 记录请求内容
     */
    public @interface record {

    }

    /**
     * 全部字段
     */
    public @interface allField {

    }

    /**
     * 重置
     */
    public @interface reset {

    }
}