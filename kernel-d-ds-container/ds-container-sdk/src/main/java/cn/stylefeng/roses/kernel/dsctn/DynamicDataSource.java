/*
 * Copyright [2020-2030] [https://www.stylefeng.cn]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Guns采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Guns源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/stylefeng/guns
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/stylefeng/guns
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */
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
