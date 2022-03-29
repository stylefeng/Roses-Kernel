package cn.stylefeng.roses.kernel.expand.modular.modular.service;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.expand.modular.modular.entity.SysExpandField;
import cn.stylefeng.roses.kernel.expand.modular.modular.pojo.request.SysExpandFieldRequest;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 业务拓展-字段信息 服务类
 *
 * @author fengshuonan
 * @date 2022/03/29 23:47
 */
public interface SysExpandFieldService extends IService<SysExpandField> {

	/**
     * 新增
     *
     * @param sysExpandFieldRequest 请求参数
     * @author fengshuonan
     * @date 2022/03/29 23:47
     */
    void add(SysExpandFieldRequest sysExpandFieldRequest);

	/**
     * 删除
     *
     * @param sysExpandFieldRequest 请求参数
     * @author fengshuonan
     * @date 2022/03/29 23:47
     */
    void del(SysExpandFieldRequest sysExpandFieldRequest);

	/**
     * 编辑
     *
     * @param sysExpandFieldRequest 请求参数
     * @author fengshuonan
     * @date 2022/03/29 23:47
     */
    void edit(SysExpandFieldRequest sysExpandFieldRequest);

	/**
     * 查询详情
     *
     * @param sysExpandFieldRequest 请求参数
     * @author fengshuonan
     * @date 2022/03/29 23:47
     */
    SysExpandField detail(SysExpandFieldRequest sysExpandFieldRequest);

	/**
     * 获取列表
     *
     * @param sysExpandFieldRequest        请求参数
     * @return List<SysExpandField>   返回结果
     * @author fengshuonan
     * @date 2022/03/29 23:47
     */
    List<SysExpandField> findList(SysExpandFieldRequest sysExpandFieldRequest);

	/**
     * 获取列表（带分页）
     *
     * @param sysExpandFieldRequest              请求参数
     * @return PageResult<SysExpandField>   返回结果
     * @author fengshuonan
     * @date 2022/03/29 23:47
     */
    PageResult<SysExpandField> findPage(SysExpandFieldRequest sysExpandFieldRequest);

}
