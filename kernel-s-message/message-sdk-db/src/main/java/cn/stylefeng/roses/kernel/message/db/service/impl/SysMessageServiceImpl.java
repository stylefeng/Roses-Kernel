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
package cn.stylefeng.roses.kernel.message.db.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kernel.db.api.factory.PageFactory;
import cn.stylefeng.roses.kernel.db.api.factory.PageResultFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.message.api.exception.MessageException;
import cn.stylefeng.roses.kernel.message.api.exception.enums.MessageExceptionEnum;
import cn.stylefeng.roses.kernel.message.api.pojo.request.MessageRequest;
import cn.stylefeng.roses.kernel.message.db.entity.SysMessage;
import cn.stylefeng.roses.kernel.message.db.mapper.SysMessageMapper;
import cn.stylefeng.roses.kernel.message.db.service.SysMessageService;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统消息 service接口实现类
 *
 * @author liuhanqing
 * @date 2020/12/31 20:09
 */
@Service
public class SysMessageServiceImpl extends ServiceImpl<SysMessageMapper, SysMessage> implements SysMessageService {

    @Override
    public void add(MessageRequest messageRequest) {
        SysMessage sysMessage = new SysMessage();
        BeanUtil.copyProperties(messageRequest, sysMessage);
        this.save(sysMessage);
    }

    @Override
    public void del(MessageRequest messageRequest) {
        SysMessage sysMessage = this.querySysMessageById(messageRequest);
        // 逻辑删除
        sysMessage.setDelFlag(YesOrNotEnum.Y.getCode());
        this.updateById(sysMessage);
    }

    @Override
    public void edit(MessageRequest messageRequest) {
        SysMessage sysMessage = new SysMessage();
        BeanUtil.copyProperties(messageRequest, sysMessage);
        this.updateById(sysMessage);
    }

    @Override
    public SysMessage detail(MessageRequest messageRequest) {
        LambdaQueryWrapper<SysMessage> queryWrapper = this.createWrapper(messageRequest, true);
        return this.getOne(queryWrapper, false);
    }

    @Override
    public PageResult<SysMessage> findPage(MessageRequest messageRequest) {
        LambdaQueryWrapper<SysMessage> wrapper = createWrapper(messageRequest, true);
        Page<SysMessage> page = this.page(PageFactory.defaultPage(), wrapper);
        return PageResultFactory.createPageResult(page);
    }

    @Override
    public List<SysMessage> findList(MessageRequest messageRequest) {
        LambdaQueryWrapper<SysMessage> wrapper = createWrapper(messageRequest, true);
        return this.list(wrapper);
    }

    @Override
    public Integer findCount(MessageRequest messageRequest) {
        LambdaQueryWrapper<SysMessage> wrapper = createWrapper(messageRequest, false);
        return this.count(wrapper);
    }

    /**
     * 根据主键id获取对象
     *
     * @author liuhanqing
     * @date 2021/2/2 20:57
     */
    private SysMessage querySysMessageById(MessageRequest messageRequest) {
        SysMessage sysMessage = this.getById(messageRequest.getMessageId());
        if (ObjectUtil.isEmpty(sysMessage)) {
            throw new MessageException(MessageExceptionEnum.NOT_EXISTED, messageRequest.getMessageId());
        }
        return sysMessage;
    }

    /**
     * 创建wrapper
     *
     * @author liuhanqing
     * @date 2021/1/8 14:16
     */
    private LambdaQueryWrapper<SysMessage> createWrapper(MessageRequest messageRequest, boolean needOrderBy) {
        LambdaQueryWrapper<SysMessage> queryWrapper = new LambdaQueryWrapper<>();

        // 查询未删除的
        queryWrapper.ne(SysMessage::getDelFlag, YesOrNotEnum.Y.getCode());

        // 按发送事件倒序
        if (needOrderBy) {
            queryWrapper.orderByDesc(SysMessage::getMessageSendTime);
        }

        if (ObjectUtil.isEmpty(messageRequest)) {
            return queryWrapper;
        }

        // 消息标题
        String messageTitle = messageRequest.getMessageTitle();

        // 接收人id
        Long receiveUserId = messageRequest.getReceiveUserId();

        // 消息类型
        String messageType = messageRequest.getMessageType();

        // 阅读状态
        Integer readFlag = messageRequest.getReadFlag();

        // 拼接sql 条件
        queryWrapper.like(ObjectUtil.isNotEmpty(messageTitle), SysMessage::getMessageTitle, messageTitle);
        queryWrapper.eq(ObjectUtil.isNotEmpty(receiveUserId), SysMessage::getReceiveUserId, receiveUserId);
        queryWrapper.eq(ObjectUtil.isNotEmpty(messageType), SysMessage::getMessageType, messageType);
        queryWrapper.eq(ObjectUtil.isNotEmpty(readFlag), SysMessage::getReadFlag, readFlag);

        return queryWrapper;
    }
}
