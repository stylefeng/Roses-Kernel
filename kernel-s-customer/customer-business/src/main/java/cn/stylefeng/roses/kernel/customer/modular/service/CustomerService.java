package cn.stylefeng.roses.kernel.customer.modular.service;

import cn.stylefeng.roses.kernel.customer.modular.entity.Customer;
import cn.stylefeng.roses.kernel.customer.modular.request.CustomerRequest;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * C端用户表 服务类
 *
 * @author fengshuonan
 * @date 2021/06/07 11:40
 */
public interface CustomerService extends IService<Customer> {

    /**
     * 新增
     *
     * @author fengshuonan
     * @date 2021/06/07 11:40
     */
    void add(CustomerRequest customerRequest);

    /**
     * 删除
     *
     * @author fengshuonan
     * @date 2021/06/07 11:40
     */
    void del(CustomerRequest customerRequest);

    /**
     * 编辑
     *
     * @author fengshuonan
     * @date 2021/06/07 11:40
     */
    void edit(CustomerRequest customerRequest);

    /**
     * 查询详情
     *
     * @author fengshuonan
     * @date 2021/06/07 11:40
     */
    Customer detail(CustomerRequest customerRequest);

    /**
     * 获取列表
     *
     * @author fengshuonan
     * @date 2021/06/07 11:40
     */
    List<Customer> findList(CustomerRequest customerRequest);

    /**
     * 获取列表（带分页）
     *
     * @author fengshuonan
     * @date 2021/06/07 11:40
     */
    PageResult<Customer> findPage(CustomerRequest customerRequest);

}