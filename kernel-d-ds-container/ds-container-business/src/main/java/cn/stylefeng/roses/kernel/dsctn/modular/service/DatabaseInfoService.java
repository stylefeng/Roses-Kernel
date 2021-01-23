package cn.stylefeng.roses.kernel.dsctn.modular.service;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.dsctn.modular.entity.DatabaseInfo;
import cn.stylefeng.roses.kernel.dsctn.modular.pojo.DatabaseInfoParam;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 数据库信息表 服务类
 *
 * @author fengshuonan
 * @date 2020/11/1 21:46
 */
public interface DatabaseInfoService extends IService<DatabaseInfo> {

    /**
     * 新增数据库信息
     *
     * @param databaseInfoParam 新增参数
     * @author fengshuonan
     * @date 2020/11/1 21:47
     */
    void add(DatabaseInfoParam databaseInfoParam);

    /**
     * 编辑数据库信息
     *
     * @param databaseInfoParam 编辑参数
     * @author fengshuonan
     * @date 2020/11/1 21:47
     */
    void edit(DatabaseInfoParam databaseInfoParam);

    /**
     * 删除，删除会导致某些用该数据源的service操作失败
     *
     * @param databaseInfoParam 删除参数
     * @author fengshuonan
     * @date 2020/11/1 21:47
     */
    void delete(DatabaseInfoParam databaseInfoParam);

    /**
     * 查询数据库信息
     *
     * @param databaseInfoParam 查询参数
     * @return 查询分页结果
     * @author fengshuonan
     * @date 2020/11/1 21:47
     */
    PageResult<DatabaseInfo> page(DatabaseInfoParam databaseInfoParam);

    /**
     * 查询数据库信息详情
     *
     * @param databaseInfoParam 查询参数
     * @author fengshuonan
     * @date 2021/1/23 20:30
     */
    DatabaseInfo detail(DatabaseInfoParam databaseInfoParam);

}
