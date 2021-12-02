package cn.stylefeng.roses.kernel.system.modular.resource.service;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.system.api.pojo.resource.ApiGroupRequest;
import cn.stylefeng.roses.kernel.system.api.pojo.resource.ApiResourceFieldRequest;
import cn.stylefeng.roses.kernel.system.api.pojo.resource.ApiResourceRequest;
import cn.stylefeng.roses.kernel.system.modular.resource.entity.ApiResource;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 接口信息 服务类
 *
 * @author majianguo
 * @date 2021/05/21 15:03
 */
public interface ApiResourceService extends IService<ApiResource> {

    /**
     * 新增
     *
     * @param apiResourceRequest
     * @author majianguo
     * @date 2021/05/21 15:03
     */
    void add(ApiResourceRequest apiResourceRequest);

    /**
     * 删除
     *
     * @param apiResourceRequest
     * @author majianguo
     * @date 2021/05/21 15:03
     */
    void del(ApiResourceRequest apiResourceRequest);

    /**
     * 编辑
     *
     * @param apiResourceRequest
     * @author majianguo
     * @date 2021/05/21 15:03
     */
    void edit(ApiResourceRequest apiResourceRequest);

    /**
     * 查询详情
     *
     * @param apiResourceRequest
     * @author majianguo
     * @date 2021/05/21 15:03
     */
    ApiResource detail(ApiResourceRequest apiResourceRequest);

    /**
     * 获取列表
     *
     * @param apiResourceRequest
     * @return List<ApiResource>
     * @author majianguo
     * @date 2021/05/21 15:03
     */
    List<ApiResource> findList(ApiResourceRequest apiResourceRequest);

    /**
     * 获取列表（带分页）
     *
     * @param apiResourceRequest
     * @return PageResult<ApiResource>
     * @author majianguo
     * @date 2021/05/21 15:03
     */
    PageResult<ApiResource> findPage(ApiResourceRequest apiResourceRequest);

    /**
     * 请求记录
     *
     * @author majianguo
     * @date 2021/5/22 下午3:01
     **/
    ApiResource record(ApiResourceRequest apiResourceRequest);

    /**
     * 查询该资源所有字段
     *
     * @author majianguo
     * @date 2021/5/24 下午6:45
     **/
    List<ApiResourceFieldRequest> allField(ApiResourceRequest apiResourceRequest);

    /**
     * 重置
     *
     * @author majianguo
     * @date 2021/5/27 下午3:34
     **/
    ApiResource reset(ApiResourceRequest apiResourceRequest);

    /**
     * 数据列表
     *
     * @param apiGroupRequest 分组请求数据
     * @return {@link java.util.List<cn.stylefeng.roses.kernel.system.modular.resource.entity.ApiResource>}
     * @author majianguo
     * @date 2021/6/18 下午6:28
     **/
    List<ApiResource> dataList(ApiGroupRequest apiGroupRequest);

    /**
     * 刷新表中的数据
     * <p>
     * 把表中的resource_code中的app code和当前启动的app code对应
     *
     * @author majianguo
     * @date 2021/12/2 13:49
     **/
    void refreshTableData();

    /**
     * 根据应用编码更新接口资源
     *
     * @param oldAppCode        等待替换的应用编码
     * @param newAppCode        新应用编码
     * @param currentSystemFlag 是否是当前系统
     * @author majianguo
     * @date 2021/12/2 14:30
     **/
    void updateApiResourceByAppCode(String oldAppCode, String newAppCode, String currentSystemFlag);
}