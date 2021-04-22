package cn.stylefeng.roses.kernel.dsctn.api;

import cn.stylefeng.roses.kernel.dsctn.api.pojo.DataSourceDto;

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
     * @author fengshuonan
     * @date 2021/4/22 14:21
     */
    DataSourceDto getDataSourceInfoById(Long dbId);

}
