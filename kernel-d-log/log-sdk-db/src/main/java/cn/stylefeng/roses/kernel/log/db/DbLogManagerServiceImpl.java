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
package cn.stylefeng.roses.kernel.log.db;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.log.api.LogManagerApi;
import cn.stylefeng.roses.kernel.log.api.pojo.manage.LogManagerRequest;
import cn.stylefeng.roses.kernel.log.api.pojo.record.LogRecordDTO;
import cn.stylefeng.roses.kernel.log.db.entity.SysLog;
import cn.stylefeng.roses.kernel.log.db.service.SysLogService;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 日志管理，数据库实现
 *
 * @author luojie
 * @date 2020/11/2 17:40
 */
@Slf4j
public class DbLogManagerServiceImpl implements LogManagerApi {

    @Resource
    private SysLogService sysLogService;

    @Override
    public List<LogRecordDTO> findList(LogManagerRequest logManagerRequest) {
        List<SysLog> sysLogList = this.sysLogService.findList(logManagerRequest);
        List<LogRecordDTO> logRecordDTOList = CollUtil.newArrayList();
        BeanUtil.copyProperties(sysLogList, logRecordDTOList);
        return logRecordDTOList;
    }

    @Override
    public PageResult<LogRecordDTO> findPage(LogManagerRequest logManagerRequest) {
        PageResult<SysLog> sysLogPageResult = this.sysLogService.findPage(logManagerRequest);

        // 分页类型转换
        PageResult<LogRecordDTO> logRecordDTOPageResult = new PageResult<>();
        BeanUtil.copyProperties(sysLogPageResult, logRecordDTOPageResult);

        // 转化数组
        List<SysLog> rows = sysLogPageResult.getRows();
        ArrayList<LogRecordDTO> newRows = new ArrayList<>();
        for (SysLog row : rows) {
            LogRecordDTO logRecordDTO = new LogRecordDTO();
            BeanUtil.copyProperties(row, logRecordDTO);

            // 设置请求和响应为空，减少带宽开销
            logRecordDTO.setRequestResult(null);
            logRecordDTO.setRequestResult(null);
            newRows.add(logRecordDTO);
        }
        logRecordDTOPageResult.setRows(newRows);

        return logRecordDTOPageResult;
    }

    @Override
    public void del(LogManagerRequest logManagerRequest) {
        this.sysLogService.del(logManagerRequest);
    }

    @Override
    public LogRecordDTO detail(LogManagerRequest logManagerRequest) {
        SysLog detail = this.sysLogService.detail(logManagerRequest);
        LogRecordDTO logRecordDTO = new LogRecordDTO();
        BeanUtil.copyProperties(detail, logRecordDTO);
        return logRecordDTO;
    }

}
