package cn.stylefeng.roses.kernel.system.modular.resource.service;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.system.api.pojo.resource.ApiResourceRequest;
import cn.stylefeng.roses.kernel.system.modular.resource.entity.ApiResource;
import cn.stylefeng.roses.kernel.system.modular.resource.entity.ApiResourceField;
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
    List<ApiResourceField> allField(ApiResourceRequest apiResourceRequest);

    /**
     * 重置
     *
     * @author majianguo
     * @date 2021/5/27 下午3:34
     **/
    ApiResource reset(ApiResourceRequest apiResourceRequest);
}