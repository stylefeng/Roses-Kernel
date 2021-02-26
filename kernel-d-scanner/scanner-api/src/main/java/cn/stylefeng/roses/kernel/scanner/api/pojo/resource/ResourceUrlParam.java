package cn.stylefeng.roses.kernel.scanner.api.pojo.resource;

import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 获取资源通过url请求
 *
 * @author fengshuonan
 * @date 2020/10/19 22:05
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ResourceUrlParam extends BaseRequest {

    private String url;

}
