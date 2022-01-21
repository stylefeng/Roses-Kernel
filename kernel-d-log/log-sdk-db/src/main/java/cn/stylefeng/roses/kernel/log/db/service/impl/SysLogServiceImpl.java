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
package cn.stylefeng.roses.kernel.log.db.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.db.api.factory.PageFactory;
import cn.stylefeng.roses.kernel.db.api.factory.PageResultFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.log.api.exception.LogException;
import cn.stylefeng.roses.kernel.log.api.exception.enums.LogExceptionEnum;
import cn.stylefeng.roses.kernel.log.api.pojo.manage.LogManagerRequest;
import cn.stylefeng.roses.kernel.log.db.entity.SysLog;
import cn.stylefeng.roses.kernel.log.db.mapper.SysLogMapper;
import cn.stylefeng.roses.kernel.log.db.service.SysLogService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
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
        LambdaUpdateWrapper<SysLog> queryWrapper = new LambdaUpdateWrapper<>();

        queryWrapper.between(SysLog::getCreateTime, logManagerRequest.getBeginDate() + " 00:00:00", logManagerRequest.getEndDate() + " 23:59:59");
        queryWrapper.eq(SysLog::getAppName, logManagerRequest.getAppName());

        this.remove(queryWrapper);
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
        if (sysLog == null) {
            throw new LogException(LogExceptionEnum.LOG_NOT_EXISTED, logManagerRequest.getLogId());
        }
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

        // 根据时间倒序排序
        queryWrapper.orderByDesc(SysLog::getCreateTime);

        if (ObjectUtil.isEmpty(logManagerRequest)) {
            return queryWrapper;
        }

        String beginDateTime = logManagerRequest.getBeginDate();
        String endDateTime = logManagerRequest.getEndDate();

        Date beginDate = null;
        Date endDate = null;
        if (StrUtil.isNotBlank(beginDateTime)) {
            beginDate = DateUtil.parseDate(beginDateTime + " 00:00:00").toJdkDate();
        }
        if (StrUtil.isNotBlank(endDateTime)) {
            endDate = DateUtil.parseDate(endDateTime + " 23:59:59").toJdkDate();
        }

        // SQL条件拼接
        String name = logManagerRequest.getLogName();
        String appName = logManagerRequest.getAppName();
        String serverIp = logManagerRequest.getServerIp();
        Long userId = logManagerRequest.getUserId();
        String clientIp = logManagerRequest.getClientIp();
        String url = logManagerRequest.getRequestUrl();
        Long logId = logManagerRequest.getLogId();

        queryWrapper.eq(ObjectUtil.isNotEmpty(logId), SysLog::getLogId, logId);
        queryWrapper.between(ObjectUtil.isAllNotEmpty(beginDate, endDate), SysLog::getCreateTime, beginDate, endDate);
        queryWrapper.like(StrUtil.isNotEmpty(name), SysLog::getLogName, name);
        queryWrapper.like(StrUtil.isNotEmpty(appName), SysLog::getAppName, appName);
        queryWrapper.like(StrUtil.isNotEmpty(serverIp), SysLog::getServerIp, serverIp);
        queryWrapper.eq(ObjectUtil.isNotNull(userId), SysLog::getUserId, userId);
        queryWrapper.eq(StrUtil.isNotEmpty(clientIp), SysLog::getClientIp, clientIp);
        queryWrapper.eq(StrUtil.isNotEmpty(url), SysLog::getRequestUrl, url);

        return queryWrapper;
    }


}
