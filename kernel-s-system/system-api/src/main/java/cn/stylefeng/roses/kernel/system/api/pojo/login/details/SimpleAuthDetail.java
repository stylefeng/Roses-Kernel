package cn.stylefeng.roses.kernel.system.api.pojo.login.details;

import lombok.Data;

import java.util.List;

/**
 * 相关权限的数据，用在登录接口的返回详情
 *
 * @author fengshuonan
 * @date 2021/1/7 17:06
 */
@Data
public class SimpleAuthDetail {

    /**
     * 角色的编码
     */
    private String id;

    /**
     * 具体的按钮
     */
    private List<String> operation;

}
