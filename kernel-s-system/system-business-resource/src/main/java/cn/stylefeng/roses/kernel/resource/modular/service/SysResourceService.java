package cn.stylefeng.roses.kernel.resource.modular.service;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.resource.api.pojo.resource.ResourceDefinition;
import cn.stylefeng.roses.kernel.resource.modular.entity.SysResource;
import cn.stylefeng.roses.kernel.resource.modular.pojo.ResourceTreeNode;
import cn.stylefeng.roses.kernel.system.pojo.resource.request.ResourceRequest;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 资源服务类
 *
 * @author fengshuonan
 * @date 2020/11/24 19:56
 */
public interface SysResourceService extends IService<SysResource> {

    /**
     * 获取资源分页列表
     *
     * @param resourceRequest 请求参数
     * @return 返回结果
     * @author fengshuonan
     * @date 2020/11/24 20:45
     */
    PageResult<SysResource> getResourceList(ResourceRequest resourceRequest);

    /**
     * 通过应用code获取获取资源下拉列表
     * <p>
     * 只获取菜单资源
     *
     * @param resourceRequest 请求参数
     * @return 响应下拉结果
     * @author fengshuonan
     * @date 2020/11/24 20:45
     */
    List<SysResource> getMenuResourceList(ResourceRequest resourceRequest);

    /**
     * 删除某个项目的所有资源
     *
     * @param projectCode 项目编码，一般为spring application name
     * @author fengshuonan
     * @date 2020/11/24 20:46
     */
    void deleteResourceByProjectCode(String projectCode);

    /**
     * 获取资源树列表，用于生成api接口
     *
     * @return 资源树列表
     * @author fengshuonan
     * @date 2020/12/18 15:06
     */
    List<ResourceTreeNode> getResourceTree();

    /**
     * 获取资源的详情
     *
     * @param resourceRequest 请求参数
     * @return 资源详情
     * @author fengshuonan
     * @date 2020/12/18 16:04
     */
    ResourceDefinition getResourceDetail(ResourceRequest resourceRequest);

}
