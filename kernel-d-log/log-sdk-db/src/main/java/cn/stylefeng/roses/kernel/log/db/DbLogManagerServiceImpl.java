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
import java.util.List;

/**
 * 日志管理，数据库实现
 *
 * @author luojie
 * @date 2020/11/2 17:40
 */
@Slf4j
public class DbLogManagerServiceImpl implements LogManagerApi {

    /**
     * 日志记录 service接口
     */
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
