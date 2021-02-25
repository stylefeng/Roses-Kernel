package cn.stylefeng.roses.kernel.system.modular.loginlog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.db.api.factory.PageFactory;
import cn.stylefeng.roses.kernel.db.api.factory.PageResultFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.rule.exception.base.ServiceException;
import cn.stylefeng.roses.kernel.rule.util.HttpServletUtil;
import cn.stylefeng.roses.kernel.system.api.LoginLogServiceApi;
import cn.stylefeng.roses.kernel.system.api.exception.enums.log.LogExceptionEnum;
import cn.stylefeng.roses.kernel.system.modular.loginlog.constants.LoginLogConstant;
import cn.stylefeng.roses.kernel.system.modular.loginlog.entity.SysLoginLog;
import cn.stylefeng.roses.kernel.system.modular.loginlog.mapper.SysLoginLogMapper;
import cn.stylefeng.roses.kernel.system.modular.loginlog.service.SysLoginLogService;
import cn.stylefeng.roses.kernel.system.api.pojo.loginlog.SysLoginLogRequest;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 系统应用service接口实现类
 *
 * @author fengshuonan
 * @date 2020/3/13 16:15
 */
@Service
public class SysLoginLogServiceImpl extends ServiceImpl<SysLoginLogMapper, SysLoginLog> implements SysLoginLogService, LoginLogServiceApi {

    @Override
    public void del(SysLoginLogRequest sysLoginLogRequest) {
        SysLoginLog sysLoginLog = this.querySysLoginLogById(sysLoginLogRequest);
        this.removeById(sysLoginLog.getLlgId());
    }

    @Override
    public SysLoginLog detail(SysLoginLogRequest sysLoginLogRequest) {
        LambdaQueryWrapper<SysLoginLog> queryWrapper = this.createWrapper(sysLoginLogRequest);
        return this.getOne(queryWrapper, false);
    }

    @Override
    public PageResult<SysLoginLog> findPage(SysLoginLogRequest sysLoginLogRequest) {
        LambdaQueryWrapper<SysLoginLog> wrapper = createWrapper(sysLoginLogRequest);
        wrapper.orderByDesc(SysLoginLog::getCreateTime);
        Page<SysLoginLog> page = this.page(PageFactory.defaultPage(), wrapper);
        return PageResultFactory.createPageResult(page);
    }

    @Override
    public void add(SysLoginLogRequest sysLoginLogRequest) {
        SysLoginLog sysLoginLog = new SysLoginLog();
        BeanUtil.copyProperties(sysLoginLogRequest, sysLoginLog);
        this.save(sysLoginLog);
    }

    @Override
    public void loginSuccess(Long userId) {
        SysLoginLog sysLoginLog = new SysLoginLog();
        sysLoginLog.setLlgName(LoginLogConstant.LOGIN_IN_LOGINNAME);
        sysLoginLog.setUserId(userId);
        sysLoginLog.setLlgIpAddress(HttpServletUtil.getRequestClientIp(HttpServletUtil.getRequest()));
        sysLoginLog.setLlgSucceed(LoginLogConstant.OPERATION_SUCCESS);
        sysLoginLog.setLlgMessage(LoginLogConstant.LOGIN_IN_SUCCESS_MESSAGE);
        this.save(sysLoginLog);
    }

    @Override
    public void loginFail(Long userId, String llgMessage) {
        SysLoginLog sysLoginLog = new SysLoginLog();
        sysLoginLog.setLlgName(LoginLogConstant.LOGIN_IN_LOGINNAME);
        sysLoginLog.setUserId(userId);
        sysLoginLog.setLlgIpAddress(HttpServletUtil.getRequestClientIp(HttpServletUtil.getRequest()));
        sysLoginLog.setLlgSucceed(LoginLogConstant.OPERATION_FAIL);
        sysLoginLog.setLlgMessage(llgMessage);
        this.save(sysLoginLog);
    }

    @Override
    public void loginOutSuccess(Long userId) {
        SysLoginLog sysLoginLog = new SysLoginLog();
        sysLoginLog.setLlgName(LoginLogConstant.LOGIN_OUT_LOGINNAME);
        sysLoginLog.setUserId(userId);
        sysLoginLog.setLlgIpAddress(HttpServletUtil.getRequestClientIp(HttpServletUtil.getRequest()));
        sysLoginLog.setLlgSucceed(LoginLogConstant.OPERATION_SUCCESS);
        sysLoginLog.setLlgMessage(LoginLogConstant.LOGIN_OUT_SUCCESS_MESSAGE);
        this.save(sysLoginLog);
    }

    @Override
    public void loginOutFail(Long userId) {
        SysLoginLog sysLoginLog = new SysLoginLog();
        sysLoginLog.setLlgName(LoginLogConstant.LOGIN_OUT_LOGINNAME);
        sysLoginLog.setUserId(userId);
        sysLoginLog.setLlgIpAddress(HttpServletUtil.getRequestClientIp(HttpServletUtil.getRequest()));
        sysLoginLog.setLlgSucceed(LoginLogConstant.OPERATION_FAIL);
        sysLoginLog.setLlgMessage(LoginLogConstant.LOGIN_OUT_SUCCESS_FAIL);
        this.save(sysLoginLog);
    }

    @Override
    public void delAll() {
        this.remove(null);
    }

    /**
     * 获取详细信息
     *
     * @author chenjinlong
     * @date 2021/1/13 10:50
     */
    private SysLoginLog querySysLoginLogById(SysLoginLogRequest sysLoginLogRequest) {
        SysLoginLog sysLoginLog = this.getById(sysLoginLogRequest.getLlgId());
        if (ObjectUtil.isNull(sysLoginLog)) {
            throw new ServiceException(LogExceptionEnum.LOG_NOT_EXIST);
        }
        return sysLoginLog;
    }

    /**
     * 构建wrapper
     *
     * @author chenjinlong
     * @date 2021/1/13 10:50
     */
    private LambdaQueryWrapper<SysLoginLog> createWrapper(SysLoginLogRequest sysLoginLogRequest) {
        LambdaQueryWrapper<SysLoginLog> queryWrapper = new LambdaQueryWrapper<>();

        if (ObjectUtil.isEmpty(sysLoginLogRequest)) {
            return queryWrapper;
        }

        // SQL条件拼接
        queryWrapper.eq(StrUtil.isNotBlank(sysLoginLogRequest.getLlgName()), SysLoginLog::getLlgName, sysLoginLogRequest.getLlgName());
        queryWrapper.ge(StrUtil.isNotBlank(sysLoginLogRequest.getBeginTime()), SysLoginLog::getCreateTime, sysLoginLogRequest.getBeginTime());
        queryWrapper.le(StrUtil.isNotBlank(sysLoginLogRequest.getEndTime()), SysLoginLog::getCreateTime, sysLoginLogRequest.getEndTime());

        return queryWrapper;
    }

}
