package cn.stylefeng.roses.kernel.dsctn.modular.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.db.api.factory.DruidDatasourceFactory;
import cn.stylefeng.roses.kernel.db.api.factory.PageFactory;
import cn.stylefeng.roses.kernel.db.api.factory.PageResultFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.druid.DruidProperties;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.dsctn.api.exception.DatasourceContainerException;
import cn.stylefeng.roses.kernel.dsctn.api.pojo.request.DatabaseInfoRequest;
import cn.stylefeng.roses.kernel.dsctn.context.DataSourceContext;
import cn.stylefeng.roses.kernel.dsctn.modular.entity.DatabaseInfo;
import cn.stylefeng.roses.kernel.dsctn.modular.factory.DruidPropertiesFactory;
import cn.stylefeng.roses.kernel.dsctn.modular.mapper.DatabaseInfoMapper;
import cn.stylefeng.roses.kernel.dsctn.modular.service.DatabaseInfoService;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static cn.stylefeng.roses.kernel.dsctn.api.constants.DatasourceContainerConstants.MASTER_DATASOURCE_NAME;
import static cn.stylefeng.roses.kernel.dsctn.api.constants.DatasourceContainerConstants.TENANT_DB_PREFIX;
import static cn.stylefeng.roses.kernel.dsctn.api.exception.enums.DatasourceContainerExceptionEnum.*;

/**
 * 数据库信息表 服务实现类
 *
 * @author fengshuonan
 * @date 2020/11/1 21:45
 */
@Service
public class DatabaseInfoServiceImpl extends ServiceImpl<DatabaseInfoMapper, DatabaseInfo> implements DatabaseInfoService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(DatabaseInfoRequest databaseInfoRequest) {

        // 判断数据库连接是否可用
        validateConnection(databaseInfoRequest);

        // 数据库中插入记录
        DatabaseInfo entity = new DatabaseInfo();
        BeanUtil.copyProperties(databaseInfoRequest, entity);
        this.save(entity);

        // 往数据源容器文中添加数据源
        addDataSourceToContext(entity, false);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void del(DatabaseInfoRequest databaseInfoRequest) {

        DatabaseInfo databaseInfo = this.queryDatabaseInfoById(databaseInfoRequest);

        // 如果是租户数据库不能删除
        if (databaseInfo.getDbName().startsWith(TENANT_DB_PREFIX)) {
            throw new DatasourceContainerException(TENANT_DATASOURCE_CANT_DELETE);
        }

        // 不能删除主数据源
        if (MASTER_DATASOURCE_NAME.equals(databaseInfo.getDbName())) {
            throw new DatasourceContainerException(DATASOURCE_INFO_NOT_EXISTED);
        }

        // 删除库中的数据源记录
        LambdaUpdateWrapper<DatabaseInfo> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(DatabaseInfo::getDelFlag, YesOrNotEnum.Y.getCode());
        updateWrapper.eq(DatabaseInfo::getDbId, databaseInfoRequest.getDbId());
        this.update(updateWrapper);

        // 删除容器中的数据源记录
        DataSourceContext.removeDataSource(databaseInfo.getDbName());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void edit(DatabaseInfoRequest databaseInfoRequest) {

        DatabaseInfo databaseInfo = this.queryDatabaseInfoById(databaseInfoRequest);

        // 不能修改数据源的名称
        if (!databaseInfoRequest.getDbName().equals(databaseInfo.getDbName())) {
            throw new DatasourceContainerException(EDIT_DATASOURCE_NAME_ERROR, databaseInfo.getDbName());
        }

        // 判断数据库连接是否可用
        validateConnection(databaseInfoRequest);

        // 更新库中的记录
        BeanUtil.copyProperties(databaseInfoRequest, databaseInfo);
        this.updateById(databaseInfo);

        // 往数据源容器文中添加数据源
        addDataSourceToContext(databaseInfo, true);
    }

    @Override
    public PageResult<DatabaseInfo> findPage(DatabaseInfoRequest databaseInfoRequest) {
        LambdaQueryWrapper<DatabaseInfo> queryWrapper = createWrapper(databaseInfoRequest);

        // 查询分页结果
        Page<DatabaseInfo> result = this.page(PageFactory.defaultPage(), queryWrapper);

        // 更新密码
        List<DatabaseInfo> records = result.getRecords();
        for (DatabaseInfo record : records) {
            record.setPassword("***");
        }

        return PageResultFactory.createPageResult(result);
    }


    @Override
    public List<DatabaseInfo> findList(DatabaseInfoRequest databaseInfoRequest) {
        LambdaQueryWrapper<DatabaseInfo> wrapper = createWrapper(databaseInfoRequest);
        return this.list(wrapper);
    }


    @Override
    public DatabaseInfo detail(DatabaseInfoRequest databaseInfoRequest) {
        DatabaseInfo databaseInfo = this.queryDatabaseInfoById(databaseInfoRequest);
        databaseInfo.setPassword("***");
        return databaseInfo;
    }

    /**
     * 判断数据库连接是否可用
     *
     * @author fengshuonan
     * @date 2020/11/1 21:50
     */
    private void validateConnection(DatabaseInfoRequest param) {
        Connection conn = null;
        try {
            Class.forName(param.getJdbcDriver());
            conn = DriverManager.getConnection(param.getJdbcUrl(), param.getUsername(), param.getPassword());
        } catch (Exception e) {
            String userTip = StrUtil.format(VALIDATE_DATASOURCE_ERROR.getUserTip(), param.getJdbcUrl());
            throw new DatasourceContainerException(VALIDATE_DATASOURCE_ERROR, userTip);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 往数据源容器文中添加数据源
     *
     * @param databaseInfo 数据库信息实体
     * @author fengshuonan
     * @date 2020/12/19 16:16
     */
    private void addDataSourceToContext(DatabaseInfo databaseInfo, Boolean removeOldDatasource) {

        // 删除容器中的数据源记录
        if (removeOldDatasource) {
            DataSourceContext.removeDataSource(databaseInfo.getDbName());
        } else {
            // 先判断context中是否有了这个数据源
            DataSource dataSource = DataSourceContext.getDataSources().get(databaseInfo.getDbName());
            if (dataSource != null) {
                String userTip = StrUtil.format(DATASOURCE_NAME_REPEAT.getUserTip(), databaseInfo.getDbName());
                throw new DatasourceContainerException(DATASOURCE_NAME_REPEAT, userTip);
            }
        }

        // 往数据源容器文中添加数据源
        DruidProperties druidProperties = DruidPropertiesFactory.createDruidProperties(databaseInfo);
        DruidDataSource druidDataSource = DruidDatasourceFactory.createDruidDataSource(druidProperties);
        DataSourceContext.addDataSource(databaseInfo.getDbName(), druidDataSource, druidProperties);

        // 初始化数据源
        try {
            druidDataSource.init();
        } catch (SQLException exception) {
            log.error("初始化数据源异常！", exception);
            String userTip = StrUtil.format(INIT_DATASOURCE_ERROR.getUserTip(), exception.getMessage());
            throw new DatasourceContainerException(INIT_DATASOURCE_ERROR, userTip);
        }
    }

    /**
     * 查询数据库信息通过id
     *
     * @author fengshuonan
     * @date 2021/2/8 9:53
     */
    private DatabaseInfo queryDatabaseInfoById(DatabaseInfoRequest databaseInfoRequest) {
        DatabaseInfo databaseInfo = this.getById(databaseInfoRequest.getDbId());
        if (databaseInfo == null) {
            throw new DatasourceContainerException(DATASOURCE_INFO_NOT_EXISTED, databaseInfoRequest.getDbId());
        }
        return databaseInfo;
    }

    /**
     * 创建wrapper
     *
     * @author liuhanqing
     * @date 2021/1/8 14:16
     */
    private LambdaQueryWrapper<DatabaseInfo> createWrapper(DatabaseInfoRequest databaseInfoRequest) {
        LambdaQueryWrapper<DatabaseInfo> queryWrapper = new LambdaQueryWrapper<>();

        // 根据名称模糊查询
        String dbName = databaseInfoRequest.getDbName();

        // 拼接sql 条件
        queryWrapper.like(ObjectUtil.isNotEmpty(dbName), DatabaseInfo::getDbName, dbName);

        // 查询没被删除的
        queryWrapper.eq(DatabaseInfo::getDelFlag, YesOrNotEnum.N.getCode());

        return queryWrapper;
    }

}
