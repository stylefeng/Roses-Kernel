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
package cn.stylefeng.roses.kernel.dsctn.modular.timer;


import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kernel.dsctn.api.enums.DataSourceStatusEnum;
import cn.stylefeng.roses.kernel.dsctn.api.pojo.request.DatabaseInfoRequest;
import cn.stylefeng.roses.kernel.dsctn.modular.entity.DatabaseInfo;
import cn.stylefeng.roses.kernel.dsctn.modular.service.DatabaseInfoService;
import cn.stylefeng.roses.kernel.timer.api.TimerAction;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 定时刷新各个数据源的状态，并更新到数据库
 *
 * @author fengshuonan
 * @date 2021/4/22 10:45
 */
@Component
public class DataSourceStatusCheckTimer implements TimerAction {

    @Resource
    private DatabaseInfoService databaseInfoService;

    @Override
    public void action(String params) {

        // 获取所有的数据源信息
        List<DatabaseInfo> list = databaseInfoService.list();

        if (ObjectUtil.isEmpty(list)) {
            return;
        }

        // 校验每个数据库连接的信息
        for (DatabaseInfo databaseInfo : list) {

            // 设置jdbc相关连接
            DatabaseInfoRequest databaseInfoRequest = new DatabaseInfoRequest();
            databaseInfoRequest.setJdbcDriver(databaseInfo.getJdbcDriver());
            databaseInfoRequest.setJdbcUrl(databaseInfo.getJdbcUrl());
            databaseInfoRequest.setUsername(databaseInfo.getUsername());
            databaseInfoRequest.setPassword(databaseInfo.getPassword());

            // 检测每个连接的准确性
            try {
                databaseInfoService.validateConnection(databaseInfoRequest);
            } catch (Exception exception) {
                // 如果有错误信息，将错误信息存储到表中
                String errorMessage = exception.getMessage();

                // 如果当前非错误状态则更新状态
                if (!DataSourceStatusEnum.ERROR.getCode().equals(databaseInfo.getStatusFlag())) {
                    databaseInfo.setStatusFlag(DataSourceStatusEnum.ERROR.getCode());
                    databaseInfo.setErrorDescription(errorMessage);
                    databaseInfoService.updateById(databaseInfo);
                }

                continue;
            }

            // 如果数据库状态为空，则修改为正常
            if (!DataSourceStatusEnum.ENABLE.getCode().equals(databaseInfo.getStatusFlag())) {
                databaseInfo.setStatusFlag(DataSourceStatusEnum.ENABLE.getCode());
                databaseInfoService.updateById(databaseInfo);
            }
        }

    }

}
