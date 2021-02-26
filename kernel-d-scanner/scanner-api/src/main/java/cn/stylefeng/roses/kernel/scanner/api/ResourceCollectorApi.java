package cn.stylefeng.roses.kernel.scanner.api;

import cn.stylefeng.roses.kernel.scanner.api.pojo.resource.ResourceDefinition;

import java.util.List;
import java.util.Map;

/**
 * 权限资源收集器，搜集本项目中的资源，仅搜集并缓存起来，不持久化
 *
 * @author fengshuonan
 * @date 2018-01-03-下午3:00
 */
public interface ResourceCollectorApi {

    /**
     * 保存所有扫描到的资源
     *
     * @param apiResource 被存储的资源集合
     * @author fengshuonan
     * @date 2020/10/19 15:26
     */
    void collectResources(List<ResourceDefinition> apiResource);

    /**
     * 通过资源编码获取资源详情
     *
     * @param resourceCode 资源编码，形如 this-system$user_manager$get_users
     * @return 资源对象的详细信息
     * @author fengshuonan
     * @date 2020/10/19 15:32
     */
    ResourceDefinition getResource(String resourceCode);

    /**
     * 获取当前运行项目的所有资源
     *
     * @return 资源集合
     * @author fengshuonan
     * @date 2020/10/19 15:53
     */
    List<ResourceDefinition> getAllResources();

    /**
     * 通过模块编码获取资源
     *
     * @param code 模块编码，一般为下划线分割的控制器前缀，不带Controller
     * @return 资源集合
     * @author fengshuonan
     * @date 2020/10/19 15:53
     */
    List<ResourceDefinition> getResourcesByModularCode(String code);

    /**
     * 通过资源code获取资源中文名称
     *
     * @param code 资源编码
     * @return 资源中文名称
     * @author fengshuonan
     * @date 2020/10/19 15:56
     */
    String getResourceName(String code);

    /**
     * 添加资源的code和名称
     *
     * @param code 资源编码
     * @param name 资源名称
     * @author fengshuonan
     * @date 2020/10/19 16:02
     */
    void bindResourceName(String code, String name);

    /**
     * 获取所有模块资源
     * <p>
     * 第一个key是模块名称，是下划线分割的控制器名称，不带Controller结尾
     * <p>
     * 第二个key是资源的编码
     *
     * @return 当前项目所有模块的资源集合
     * @author fengshuonan
     * @date 2020/10/19 16:03
     */
    Map<String, Map<String, ResourceDefinition>> getModularResources();

    /**
     * 通过编码获取url
     *
     * @param code 资源编码
     * @return 资源的url
     * @author fengshuonan
     * @date 2020/10/19 16:17
     */
    String getResourceUrl(String code);

    /**
     * 通过url获取资源声明
     *
     * @param url 资源的url
     * @return 资源详情
     * @author fengshuonan
     * @date 2020/10/19 16:17
     */
    ResourceDefinition getResourceByUrl(String url);

}
