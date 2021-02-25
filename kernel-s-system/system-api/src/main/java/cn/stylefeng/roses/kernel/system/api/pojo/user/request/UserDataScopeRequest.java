package cn.stylefeng.roses.kernel.system.api.pojo.user.request;


import lombok.Data;

/**
 * 用户数据范围
 *
 * @author chenjinlong
 * @date 2021/2/3 15:35
 */
@Data
public class UserDataScopeRequest {

    /**
     * 主键
     */
    private Long userDataScopeId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 机构id
     */
    private Long orgId;
}
