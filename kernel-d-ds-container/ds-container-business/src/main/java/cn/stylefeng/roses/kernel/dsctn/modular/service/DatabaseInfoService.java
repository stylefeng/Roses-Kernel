package cn.stylefeng.roses.kernel.dsctn.modular.service;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.dsctn.modular.entity.DatabaseInfo;
import cn.stylefeng.roses.kernel.dsctn.api.pojo.request.DatabaseInfoRequest;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

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
     * @param databaseInfoRequest 新增参数
     * @author fengshuonan
     * @date 2020/11/1 21:47
     */
    void add(DatabaseInfoRequest databaseInfoRequest);

    /**
     * 编辑数据库信息
     *
     * @param databaseInfoRequest 编辑参数
     * @author fengshuonan
     * @date 2020/11/1 21:47
     */
    void edit(DatabaseInfoRequest databaseInfoRequest);

    /**
     * 删除，删除会导致某些用该数据源的service操作失败
     *
     * @param databaseInfoRequest 删除参数
     * @author fengshuonan
     * @date 2020/11/1 21:47
     */
    void del(DatabaseInfoRequest databaseInfoRequest);


    /**
     * 查询数据库信息详情
     *
     * @param databaseInfoRequest 查询参数
     * @author fengshuonan
     * @date 2021/1/23 20:30
     */
    DatabaseInfo detail(DatabaseInfoRequest databaseInfoRequest);

    /**
     * 查询数据库信息
     *
     * @param databaseInfoRequest 查询参数
     * @return 查询分页结果
     * @author fengshuonan
     * @date 2020/11/1 21:47
     */
    PageResult<DatabaseInfo> findPage(DatabaseInfoRequest databaseInfoRequest);

    /**
     * 列表查询
     *
     * @param databaseInfoRequest 参数
     * @author liuhanqing
     * @date 2021/2/2 21:21
     */
    List<DatabaseInfo> findList(DatabaseInfoRequest databaseInfoRequest);


}
