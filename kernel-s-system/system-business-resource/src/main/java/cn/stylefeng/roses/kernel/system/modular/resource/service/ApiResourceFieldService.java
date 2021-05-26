package cn.stylefeng.roses.kernel.system.modular.resource.service;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.system.api.pojo.resource.ApiResourceFieldRequest;
import cn.stylefeng.roses.kernel.system.modular.resource.entity.ApiResourceField;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 接口字段信息 服务类
 *
 * @author majianguo
 * @date 2021/05/21 15:03
 */
public interface ApiResourceFieldService extends IService<ApiResourceField> {

	/**
     * 新增
     *
     * @param apiResourceFieldRequest
     * @author majianguo
     * @date 2021/05/21 15:03
     */
    void add(ApiResourceFieldRequest apiResourceFieldRequest);

	/**
     * 删除
     *
     * @param apiResourceFieldRequest
     * @author majianguo
     * @date 2021/05/21 15:03
     */
    void del(ApiResourceFieldRequest apiResourceFieldRequest);

	/**
     * 编辑
     *
     * @param apiResourceFieldRequest
     * @author majianguo
     * @date 2021/05/21 15:03
     */
    void edit(ApiResourceFieldRequest apiResourceFieldRequest);

	/**
     * 查询详情
     *
     * @param apiResourceFieldRequest
     * @author majianguo
     * @date 2021/05/21 15:03
     */
    ApiResourceField detail(ApiResourceFieldRequest apiResourceFieldRequest);

	/**
     * 获取列表
     *
     * @param apiResourceFieldRequest
     * @return List<ApiResourceField>
     * @author majianguo
     * @date 2021/05/21 15:03
     */
    List<ApiResourceField> findList(ApiResourceFieldRequest apiResourceFieldRequest);

	/**
     * 获取列表（带分页）
     *
     * @param apiResourceFieldRequest
     * @return PageResult<ApiResourceField>
     * @author majianguo
     * @date 2021/05/21 15:03
     */
    PageResult<ApiResourceField> findPage(ApiResourceFieldRequest apiResourceFieldRequest);

}