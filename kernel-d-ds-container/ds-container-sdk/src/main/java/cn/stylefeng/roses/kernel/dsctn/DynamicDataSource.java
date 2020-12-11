package cn.stylefeng.roses.kernel.dsctn;

import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.dsctn.api.context.CurrentDataSourceContext;
import cn.stylefeng.roses.kernel.dsctn.context.DataSourceContext;

import javax.sql.DataSource;
import java.util.Map;

import static cn.stylefeng.roses.kernel.dsctn.api.constants.DatasourceContainerConstants.MASTER_DATASOURCE_NAME;

/**
 * 动态数据源的实现，带切换功能的
 *
 * @author fengshuonan
 * @date 2020/11/1 0:08
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    /**
     * 决断当前正在进行的service或者mapper用哪个数据源
     *
     * @author fengshuonan
     * @date 2020/11/1 0:08
     */
    @Override
    protected DataSource determineDataSource() {

        // 获取当前Context存储的数据源名称
        String dataSourceName = CurrentDataSourceContext.getDataSourceName();

        // 如果当前Context没有值，就用主数据源
        if (StrUtil.isEmpty(dataSourceName)) {
            dataSourceName = MASTER_DATASOURCE_NAME;
        }

        // 从数据源容器中获取对应的数据源
        Map<String, DataSource> dataSources = DataSourceContext.getDataSources();
        return dataSources.get(dataSourceName);
    }

}
