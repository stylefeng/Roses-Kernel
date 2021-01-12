package cn.stylefeng.roses.kernel.log.db.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.log.api.pojo.manage.LogManagerParam;
import cn.stylefeng.roses.kernel.log.db.entity.SysLog;
import cn.stylefeng.roses.kernel.log.db.mapper.SysLogMapper;
import cn.stylefeng.roses.kernel.log.db.service.SysLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 日志记录 service接口实现类
 *
 * @author luojie
 * @date 2020/11/2 17:45
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {

    @Override
    public SysLog detail(LogManagerParam logManagerParam) {
        return this.querySysLog(logManagerParam);
    }

    /**
     * 查询日志详细信息
     *
     * @param
     * @return
     * @author TSQ
     * @date 2021/1/11 17:54
     */
    private SysLog querySysLog(LogManagerParam logManagerParam) {
        SysLog hrOrganization = this.getById(logManagerParam.getLogId());
        return hrOrganization;
    }

}
