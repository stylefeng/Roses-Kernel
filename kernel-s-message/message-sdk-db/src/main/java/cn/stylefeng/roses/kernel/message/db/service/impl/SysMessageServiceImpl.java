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
        LambdaQueryWrapper<SysMessage> queryWrapper = this.createWrapper(messageRequest);
        return this.getOne(queryWrapper, false);
    }

    @Override
    public PageResult<SysMessage> findPage(MessageRequest messageRequest) {
        LambdaQueryWrapper<SysMessage> wrapper = createWrapper(messageRequest);
        Page<SysMessage> page = this.page(PageFactory.defaultPage(), wrapper);
        return PageResultFactory.createPageResult(page);
    }

    @Override
    public List<SysMessage> findList(MessageRequest messageRequest) {
        LambdaQueryWrapper<SysMessage> wrapper = createWrapper(messageRequest);
        return this.list(wrapper);
    }

    @Override
    public Integer findCount(MessageRequest messageRequest) {
        LambdaQueryWrapper<SysMessage> wrapper = createWrapper(messageRequest);
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
    private LambdaQueryWrapper<SysMessage> createWrapper(MessageRequest messageRequest) {
        LambdaQueryWrapper<SysMessage> queryWrapper = new LambdaQueryWrapper<>();

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

        // 查询未删除的
        queryWrapper.ne(SysMessage::getDelFlag, YesOrNotEnum.Y.getCode());

        // 按发送事件倒序
        queryWrapper.orderByDesc(SysMessage::getMessageSendTime);

        return queryWrapper;
    }
}
