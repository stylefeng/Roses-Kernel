package cn.stylefeng.roses.kernel.message.db.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kernel.db.api.factory.PageFactory;
import cn.stylefeng.roses.kernel.db.api.factory.PageResultFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.message.api.pojo.MessageParam;
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
    public PageResult<SysMessage> page(MessageParam messageParam) {
        LambdaQueryWrapper<SysMessage> wrapper = createWrapper(messageParam);
        Page<SysMessage> page = this.page(PageFactory.defaultPage(), wrapper);
        return PageResultFactory.createPageResult(page);
    }

    @Override
    public List<SysMessage> list(MessageParam messageParam) {
        LambdaQueryWrapper<SysMessage> wrapper = createWrapper(messageParam);
        return this.list(wrapper);
    }
    @Override
    public Integer count(MessageParam messageParam) {
        LambdaQueryWrapper<SysMessage> wrapper = createWrapper(messageParam);
        return this.count(wrapper);
    }

    /**
     * 创建wrapper
     *
     * @author liuhanqing
     * @date 2021/1/8 14:16
     */
    private LambdaQueryWrapper<SysMessage> createWrapper(MessageParam messageParam) {
        LambdaQueryWrapper<SysMessage> queryWrapper = new LambdaQueryWrapper<>();

        if (ObjectUtil.isNotNull(messageParam)) {

            // 拼接消息标题
            if (ObjectUtil.isNotEmpty(messageParam.getMessageTitle())) {
                queryWrapper.like(SysMessage::getMessageTitle, messageParam.getMessageTitle());
            }

            // 拼接接收人id查询条件
            if (ObjectUtil.isNotEmpty(messageParam.getReceiveUserId())) {
                queryWrapper.eq(SysMessage::getReceiveUserId, messageParam.getReceiveUserId());
            }

            // 拼接消息类型
            if (ObjectUtil.isNotEmpty(messageParam.getMessageType())) {
                queryWrapper.eq(SysMessage::getMessageType, messageParam.getMessageType());
            }

            // 拼接阅读状态
            if (ObjectUtil.isNotEmpty(messageParam.getReadFlag())) {
                queryWrapper.eq(SysMessage::getReadFlag, messageParam.getReadFlag());
            }
        }
        // 查询未删除的
        queryWrapper.ne(SysMessage::getDelFlag, YesOrNotEnum.Y.getCode());

        // 按发送事件倒序
        queryWrapper.orderByDesc(SysMessage::getMessageSendTime);

        return queryWrapper;
    }
}
