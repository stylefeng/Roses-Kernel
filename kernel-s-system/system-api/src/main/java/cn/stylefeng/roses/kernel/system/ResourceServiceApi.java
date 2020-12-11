package cn.stylefeng.roses.kernel.system;

import cn.stylefeng.roses.kernel.resource.api.pojo.resource.ResourceDefinition;
import cn.stylefeng.roses.kernel.resource.api.pojo.resource.ResourceUrlParam;

import java.util.List;

/**
 * 资源服务相关接口
 *
 * @author fengshuonan
 * @date 2020/12/3 15:45
 */
public interface ResourceServiceApi {

    /**
     * 通过url获取资源
     *
     * @param resourceUrlReq 资源url的封装
     * @return 资源详情
     * @author fengshuonan
     * @date 2020/10/19 22:06
     */
    ResourceDefinition getResourceByUrl(ResourceUrlParam resourceUrlReq);

    /**
     * 获取资源详情，根据资源id集合
     *
     * @param resourceIds 资源id集合
     * @return 资源详情列表
     * @author fengshuonan
     * @date 2020/11/29 19:49
     */
    List<ResourceDefinition> getResourceListByIds(List<String> resourceIds);

    /**
     * 获取资源的url列表，根据资源ids查询
     *
     * @param resourceIds 资源id集合
     * @return 资源url列表
     * @author fengshuonan
     * @date 2020/11/29 19:49
     */
    List<String> getResourceUrlsListByIds(List<String> resourceIds);

}
