package cn.stylefeng.roses.kernel.system.pojo.role.response;

import cn.stylefeng.roses.kernel.auth.api.enums.DataScopeTypeEnum;
import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author majianguo
 * @date 2020/11/5 下午3:33
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysRoleResponse extends BaseRequest {

    /**
     * 主键
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 编码
     */
    private String code;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 数据范围类型（枚举 10全部数据 20本部门及以下数据 30本部门数据 40仅本人数据 50自定义数据）
     */
    private Integer dataScopeType;

    /**
     * 数据范围类型枚举
     */
    private DataScopeTypeEnum dataScopeTypeEnum;

    /**
     * 备注
     */
    private String remark;

    /**
     * 状态（字典 1正常 2停用）
     */
    private Integer statusFlag;
}
