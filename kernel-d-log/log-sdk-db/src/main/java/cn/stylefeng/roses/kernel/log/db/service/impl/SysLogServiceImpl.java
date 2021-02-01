package cn.stylefeng.roses.kernel.log.db.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.db.api.factory.PageFactory;
import cn.stylefeng.roses.kernel.db.api.factory.PageResultFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.log.api.pojo.manage.LogManagerRequest;
import cn.stylefeng.roses.kernel.log.db.entity.SysLog;
import cn.stylefeng.roses.kernel.log.db.mapper.SysLogMapper;
import cn.stylefeng.roses.kernel.log.db.service.SysLogService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 日志记录 service接口实现类
 *
 * @author luojie
 * @date 2020/11/2 17:45
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {


    @Override
    public void add(LogManagerRequest logManagerRequest) {
        SysLog sysLog = new SysLog();
        BeanUtil.copyProperties(logManagerRequest, sysLog);
        this.save(sysLog);
    }

    @Override
    public void del(LogManagerRequest logManagerRequest) {
        SysLog sysLog = this.querySysLogById(logManagerRequest);
        this.removeById(sysLog.getLogId());
    }

    @Override
    public void delAll(LogManagerRequest logManagerRequest) {
        this.remove(null);
    }

    @Override
    public SysLog detail(LogManagerRequest logManagerRequest) {
        LambdaQueryWrapper<SysLog> queryWrapper = this.createWrapper(logManagerRequest);
        return this.getOne(queryWrapper, false);
    }


    @Override
    public List<SysLog> findList(LogManagerRequest logManagerRequest) {
        LambdaQueryWrapper<SysLog> wrapper = this.createWrapper(logManagerRequest);
        return this.list(wrapper);
    }

    @Override
    public PageResult<SysLog> findPage(LogManagerRequest logManagerRequest) {
        LambdaQueryWrapper<SysLog> wrapper = createWrapper(logManagerRequest);
        Page<SysLog> page = this.page(PageFactory.defaultPage(), wrapper);
        return PageResultFactory.createPageResult(page);
    }


    /**
     * 根据主键id获取对象
     *
     * @author chenjinlong
     * @date 2021/1/26 13:28
     */
    private SysLog querySysLogById(LogManagerRequest logManagerRequest) {
        SysLog sysLog = this.getById(logManagerRequest.getLogId());
        return sysLog;
    }

    /**
     * 实体构建queryWrapper
     *
     * @author chenjinlong
     * @date 2021/1/24 22:03
     */
    private LambdaQueryWrapper<SysLog> createWrapper(LogManagerRequest logManagerRequest) {
        LambdaQueryWrapper<SysLog> queryWrapper = new LambdaQueryWrapper<>();

        String beginDateTime = logManagerRequest.getBeginDateTime();
        String endDateTime = logManagerRequest.getEndDateTime();
        // SQL条件拼接
        String name = logManagerRequest.getLogName();
        String appName = logManagerRequest.getAppName();
        String serverIp = logManagerRequest.getServerIp();
        Long userId = logManagerRequest.getUserId();
        String clientIp = logManagerRequest.getClientIp();
        String url = logManagerRequest.getRequestUrl();

        queryWrapper.between(!StrUtil.isAllBlank(beginDateTime, endDateTime), SysLog::getCreateTime, beginDateTime, endDateTime);
        queryWrapper.eq(StrUtil.isNotEmpty(name), SysLog::getLogName, name);
        queryWrapper.eq(StrUtil.isNotEmpty(appName), SysLog::getAppName, appName);
        queryWrapper.eq(StrUtil.isNotEmpty(serverIp), SysLog::getServerIp, serverIp);
        queryWrapper.eq(ObjectUtil.isNotNull(userId), SysLog::getUserId, userId);
        queryWrapper.eq(StrUtil.isNotEmpty(clientIp), SysLog::getClientIp, clientIp);
        queryWrapper.eq(StrUtil.isNotEmpty(url), SysLog::getRequestUrl, url);

        // 根据时间倒序排序
        queryWrapper.orderByDesc(SysLog::getCreateTime);

        return queryWrapper;
    }


}
