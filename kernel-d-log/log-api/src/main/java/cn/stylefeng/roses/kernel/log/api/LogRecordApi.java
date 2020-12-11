package cn.stylefeng.roses.kernel.log.api;

import cn.stylefeng.roses.kernel.log.api.pojo.record.LogRecordDTO;

import java.util.List;

/**
 * 日志记录的api，只用于记录日志
 *
 * @author fengshuonan
 * @date 2020/10/27 16:19
 */
public interface LogRecordApi {

    /**
     * 同步记录日志，也就是记录成功才方法返回
     *
     * @param logRecordDTO 日志记录的参数
     * @author fengshuonan
     * @date 2020/10/27 17:38
     */
    void recordLog(LogRecordDTO logRecordDTO);

    /**
     * 批量同步记录日志
     *
     * @param logRecords 待输出日志列表
     * @author majianguo
     * @date 2020/11/2 下午2:59
     */
    void recordLogByList(List<LogRecordDTO> logRecords);

    /**
     * 异步记录日志，调用本方法直接返回结果，之后再异步记录日志
     *
     * @param logRecordDTO 日志记录的参数
     * @author fengshuonan
     * @date 2020/10/27 17:38
     */
    void recordLogAsync(LogRecordDTO logRecordDTO);

}