package cn.stylefeng.roses.kernel.log.db.service;

import cn.stylefeng.roses.kernel.log.api.pojo.manage.LogManagerParam;
import cn.stylefeng.roses.kernel.log.db.entity.SysLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 日志记录 service接口
 *
 * @author luojie
 * @date 2020/11/2 17:44
 */
public interface SysLogService extends IService<SysLog> {

    /**
     * 查看日志详情
     *
     * @param
     * @return
     * @author TSQ
     * @date 2021/1/11 17:51
     */
    SysLog detail(LogManagerParam logManagerParam);

}
