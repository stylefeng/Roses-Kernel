package cn.stylefeng.roses.kernel.scanner.api.pojo.resource;

import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 获取用户资源请求
 *
 * @author fengshuonan
 * @date 2020/10/19 22:04
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserResourceParam extends BaseRequest {

    /**
     * 用户id
     */
    private String userId;

}
