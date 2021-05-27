package cn.stylefeng.roses.kernel.dsctn.api;

import cn.stylefeng.roses.kernel.dsctn.api.exception.DatasourceContainerException;
import cn.stylefeng.roses.kernel.dsctn.api.pojo.DataSourceDto;
import cn.stylefeng.roses.kernel.dsctn.api.pojo.request.DatabaseInfoRequest;

/**
 * 数据库连接的api
 *
 * @author fengshuonan
 * @date 2021/4/22 14:19
 */
public interface DataSourceApi {

    /**
     * 根据dbId获取数据库连接信息
     *
     * @param dbId 数据库连接id
     * @return 数据库连接信息
     * @throws DatasourceContainerException 找不到对应的dbId会抛出异常
     * @author fengshuonan
     * @date 2021/4/22 14:21
     */
    DataSourceDto getDataSourceInfoById(Long dbId);

    /**
     * 新增数据库信息
     *
     * @param databaseInfoRequest 新增参数
     * @author fengshuonan
     * @date 2020/11/1 21:47
     */
    void add(DatabaseInfoRequest databaseInfoRequest);

    /**
     * 通过数据源编码删除数据源
     *
     * @param datasourceCode 数据源编码
     * @author fengshuonan
     * @date 2021/5/27 10:06
     */
    void deleteByDatasourceCode(String datasourceCode);

}
