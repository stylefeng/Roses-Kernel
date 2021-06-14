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
package cn.stylefeng.roses.kernel.system.modular.notice.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.db.api.factory.PageFactory;
import cn.stylefeng.roses.kernel.db.api.factory.PageResultFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.message.api.MessageApi;
import cn.stylefeng.roses.kernel.message.api.enums.MessageBusinessTypeEnum;
import cn.stylefeng.roses.kernel.message.api.pojo.request.MessageSendRequest;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import cn.stylefeng.roses.kernel.system.api.exception.SystemModularException;
import cn.stylefeng.roses.kernel.system.api.exception.enums.notice.NoticeExceptionEnum;
import cn.stylefeng.roses.kernel.system.api.pojo.notice.SysNoticeRequest;
import cn.stylefeng.roses.kernel.system.modular.notice.entity.SysNotice;
import cn.stylefeng.roses.kernel.system.modular.notice.mapper.SysNoticeMapper;
import cn.stylefeng.roses.kernel.system.modular.notice.service.SysNoticeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 通知表 服务实现类
 *
 * @author liuhanqing
 * @date 2021/1/8 22:45
 */
@Service
public class SysNoticeServiceImpl extends ServiceImpl<SysNoticeMapper, SysNotice> implements SysNoticeService {

    private static final String NOTICE_SCOPE_ALL = "all";

    @Resource
    private MessageApi messageApi;

    @Override
    public void add(SysNoticeRequest sysNoticeRequest) {
        SysNotice sysNotice = new SysNotice();

        // 拷贝属性
        BeanUtil.copyProperties(sysNoticeRequest, sysNotice);

        // 没传递通知范围，则默认发给所有人
        if (StrUtil.isBlank(sysNotice.getNoticeScope())) {
            sysNotice.setNoticeScope(NOTICE_SCOPE_ALL);
        }

        // 如果保存成功调用发送消息
        if (this.save(sysNotice)) {
            sendMessage(sysNotice);
        }
    }

    @Override
    public void del(SysNoticeRequest sysNoticeRequest) {
        SysNotice sysNotice = this.querySysNoticeById(sysNoticeRequest);
        // 逻辑删除
        sysNotice.setDelFlag(YesOrNotEnum.Y.getCode());
        this.updateById(sysNotice);
    }

    @Override
    public void edit(SysNoticeRequest sysNoticeRequest) {
        SysNotice sysNotice = this.querySysNoticeById(sysNoticeRequest);

        // 通知范围不允许修改， 如果通知范围不同抛出异常
        if (!sysNoticeRequest.getNoticeScope().equals(sysNotice.getNoticeScope())) {
            throw new SystemModularException(NoticeExceptionEnum.NOTICE_SCOPE_NOT_EDIT);
        }

        // 获取通知范围，如果为空则设置为all
        String noticeScope = sysNotice.getNoticeScope();
        if (StrUtil.isBlank(noticeScope)) {
            sysNoticeRequest.setNoticeScope(NOTICE_SCOPE_ALL);
        }

        // 更新属性
        BeanUtil.copyProperties(sysNoticeRequest, sysNotice);

        // 修改成功后发送信息
        if (this.updateById(sysNotice)) {
            sendMessage(sysNotice);
        }
    }

    @Override
    public SysNotice detail(SysNoticeRequest sysNoticeRequest) {
        LambdaQueryWrapper<SysNotice> queryWrapper = this.createWrapper(sysNoticeRequest);
        return this.getOne(queryWrapper, false);
    }

    @Override
    public PageResult<SysNotice> findPage(SysNoticeRequest sysNoticeRequest) {
        LambdaQueryWrapper<SysNotice> wrapper = createWrapper(sysNoticeRequest);
        Page<SysNotice> page = this.page(PageFactory.defaultPage(), wrapper);
        return PageResultFactory.createPageResult(page);
    }

    @Override
    public List<SysNotice> findList(SysNoticeRequest sysNoticeRequest) {
        LambdaQueryWrapper<SysNotice> wrapper = createWrapper(sysNoticeRequest);
        return this.list(wrapper);
    }

    /**
     * 获取通知管理
     *
     * @author liuhanqing
     * @date 2021/1/9 16:56
     */
    private SysNotice querySysNoticeById(SysNoticeRequest sysNoticeRequest) {
        SysNotice sysNotice = this.getById(sysNoticeRequest.getNoticeId());
        if (ObjectUtil.isNull(sysNotice)) {
            throw new SystemModularException(NoticeExceptionEnum.NOTICE_NOT_EXIST, sysNoticeRequest.getNoticeId());
        }
        return sysNotice;
    }

    /**
     * 创建wrapper
     *
     * @author liuhanqing
     * @date 2020/1/9 16:16
     */
    private LambdaQueryWrapper<SysNotice> createWrapper(SysNoticeRequest sysNoticeRequest) {
        LambdaQueryWrapper<SysNotice> queryWrapper = new LambdaQueryWrapper<>();

        // 按时间倒序排列
        queryWrapper.orderByDesc(BaseEntity::getCreateTime);

        // 查询未删除状态的
        queryWrapper.eq(SysNotice::getDelFlag, YesOrNotEnum.N.getCode());

        if (ObjectUtil.isEmpty(sysNoticeRequest)) {
            return queryWrapper;
        }

        // 通知id
        Long noticeId = sysNoticeRequest.getNoticeId();

        // 通知标题
        String noticeTitle = sysNoticeRequest.getNoticeTitle();

        // 拼接sql 条件
        queryWrapper.like(ObjectUtil.isNotEmpty(noticeTitle), SysNotice::getNoticeTitle, noticeTitle);
        queryWrapper.eq(ObjectUtil.isNotEmpty(noticeId), SysNotice::getNoticeId, noticeId);

        return queryWrapper;
    }

    /**
     * 发送消息
     *
     * @author fengshuonan
     * @date 2021/2/8 19:30
     */
    private void sendMessage(SysNotice sysNotice) {
        MessageSendRequest message = new MessageSendRequest();

        // 消息标题
        message.setMessageTitle(sysNotice.getNoticeTitle());

        // 消息内容
        message.setMessageContent(sysNotice.getNoticeContent());

        // 消息优先级
        message.setPriorityLevel(sysNotice.getPriorityLevel());

        // 消息发送范围
        message.setReceiveUserIds(sysNotice.getNoticeScope());

        // 消息业务类型
        message.setBusinessType(MessageBusinessTypeEnum.SYS_NOTICE.getCode());
        message.setBusinessTypeValue(MessageBusinessTypeEnum.SYS_NOTICE.getName());

        message.setBusinessId(sysNotice.getNoticeId());
        message.setMessageSendTime(new Date());

        try {
            messageApi.sendMessage(message);
        } catch (Exception exception) {
            // 发送失败打印异常
            log.error("发送消息失败:", exception);
        }
    }

}
