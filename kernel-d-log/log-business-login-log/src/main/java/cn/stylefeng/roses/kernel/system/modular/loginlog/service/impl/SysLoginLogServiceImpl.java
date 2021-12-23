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
package cn.stylefeng.roses.kernel.system.modular.loginlog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.db.api.factory.PageFactory;
import cn.stylefeng.roses.kernel.db.api.factory.PageResultFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.log.api.LoginLogServiceApi;
import cn.stylefeng.roses.kernel.log.api.pojo.loginlog.SysLoginLogDto;
import cn.stylefeng.roses.kernel.log.api.pojo.loginlog.SysLoginLogRequest;
import cn.stylefeng.roses.kernel.rule.exception.base.ServiceException;
import cn.stylefeng.roses.kernel.rule.util.HttpServletUtil;
import cn.stylefeng.roses.kernel.system.api.UserServiceApi;
import cn.stylefeng.roses.kernel.system.api.exception.enums.log.LogExceptionEnum;
import cn.stylefeng.roses.kernel.system.api.pojo.user.SysUserDTO;
import cn.stylefeng.roses.kernel.system.modular.loginlog.constants.LoginLogConstant;
import cn.stylefeng.roses.kernel.system.modular.loginlog.entity.SysLoginLog;
import cn.stylefeng.roses.kernel.system.modular.loginlog.mapper.SysLoginLogMapper;
import cn.stylefeng.roses.kernel.system.modular.loginlog.service.SysLoginLogService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;

/**
 * 系统应用service接口实现类
 *
 * @author fengshuonan
 * @date 2020/3/13 16:15
 */
@Service
public class SysLoginLogServiceImpl extends ServiceImpl<SysLoginLogMapper, SysLoginLog> implements SysLoginLogService, LoginLogServiceApi {

    @Resource
    private UserServiceApi userServiceApi;

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
    public PageResult<SysLoginLogDto> findPage(SysLoginLogRequest sysLoginLogRequest) {
        LambdaQueryWrapper<SysLoginLog> wrapper = this.createWrapper(sysLoginLogRequest);
        Page<SysLoginLog> page = this.page(PageFactory.defaultPage(), wrapper);

        ArrayList<SysLoginLogDto> sysLoginLogDtos = new ArrayList<>();
        for (SysLoginLog record : page.getRecords()) {
            SysLoginLogDto sysLoginLogDto = new SysLoginLogDto();
            BeanUtil.copyProperties(record, sysLoginLogDto);

            // 填充用户姓名
            SysUserDTO userInfoByUserId = userServiceApi.getUserInfoByUserId(sysLoginLogDto.getUserId());
            if (userInfoByUserId != null) {
                sysLoginLogDto.setUserName(userInfoByUserId.getRealName());
            }
            sysLoginLogDtos.add(sysLoginLogDto);
        }

        return PageResultFactory.createPageResult(sysLoginLogDtos, page.getTotal(), Convert.toInt(page.getSize()), Convert.toInt(page.getCurrent()));
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

        Date beginDate = null;
        if (StrUtil.isNotBlank(sysLoginLogRequest.getBeginTime())) {
            beginDate = DateUtil.parseDate(sysLoginLogRequest.getBeginTime()).toJdkDate();
        }
        Date endDate = null;
        if (StrUtil.isNotBlank(sysLoginLogRequest.getEndTime())) {
            endDate = DateUtil.parseDate(sysLoginLogRequest.getEndTime()).toJdkDate();
        }

        // SQL条件拼接
        queryWrapper.eq(StrUtil.isNotBlank(sysLoginLogRequest.getLlgName()), SysLoginLog::getLlgName, sysLoginLogRequest.getLlgName());
        queryWrapper.ge(StrUtil.isNotBlank(sysLoginLogRequest.getBeginTime()), SysLoginLog::getCreateTime, beginDate);
        queryWrapper.le(StrUtil.isNotBlank(sysLoginLogRequest.getEndTime()), SysLoginLog::getCreateTime, endDate);

        // 根据创建时间降序排列
        queryWrapper.orderByDesc(SysLoginLog::getCreateTime);

        return queryWrapper;
    }

}
