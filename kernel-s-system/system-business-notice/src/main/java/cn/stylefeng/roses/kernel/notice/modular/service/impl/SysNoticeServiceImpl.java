package cn.stylefeng.roses.kernel.notice.modular.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.db.api.factory.PageFactory;
import cn.stylefeng.roses.kernel.db.api.factory.PageResultFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.message.api.MessageApi;
import cn.stylefeng.roses.kernel.message.api.enums.MessageBusinessTypeEnum;
import cn.stylefeng.roses.kernel.message.api.pojo.MessageSendParam;
import cn.stylefeng.roses.kernel.notice.modular.entity.SysNotice;
import cn.stylefeng.roses.kernel.notice.modular.mapper.SysNoticeMapper;
import cn.stylefeng.roses.kernel.notice.modular.service.SysNoticeService;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import cn.stylefeng.roses.kernel.rule.exception.base.ServiceException;
import cn.stylefeng.roses.kernel.system.exception.enums.NoticeExceptionEnum;
import cn.stylefeng.roses.kernel.system.pojo.notice.SysNoticeRequest;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 通知表 服务实现类
 *
 * @author liuhanqing
 * @date 2021/1/8 22:45
 */
@Service
public class SysNoticeServiceImpl extends ServiceImpl<SysNoticeMapper, SysNotice> implements SysNoticeService {


    private static String NOTICE_SCOPE_ALL = "all";


    /**
     * 系统消息api
     */
    @Resource
    private MessageApi messageApi;

    private void sendMessage(SysNotice sysNotice) {
        MessageSendParam message = new MessageSendParam();
        // 消息标题
        message.setMessageTitle(sysNotice.getNoticeTitle());
        // 消息内容
        message.setMessageContent(sysNotice.getNoticeSummary());
        // 消息优先级
        message.setPriorityLevel(sysNotice.getPriorityLevel());
        // 消息发送范围
        message.setReceiveUserIds(sysNotice.getNoticeScope());
        // 消息业务类型
        message.setBusinessType(MessageBusinessTypeEnum.SYS_NOTICE.getCode());
        message.setBusinessId(sysNotice.getNoticeId());
        messageApi.sendMessage(message);
    }

    @Override
    public void add(SysNoticeRequest sysNoticeRequest) {
        SysNotice sysNotice = new SysNotice();
        BeanUtil.copyProperties(sysNoticeRequest, sysNotice);
        if (StrUtil.isBlank(sysNotice.getNoticeScope())) {
            sysNotice.setNoticeScope(NOTICE_SCOPE_ALL);
        }

        // 如果保存成功调用发送消息
        if (this.save(sysNotice)) {
            sendMessage(sysNotice);
        }
    }

    @Override
    public void edit(SysNoticeRequest sysNoticeRequest) {

        SysNotice sysNotice = this.querySysNotice(sysNoticeRequest);
        String noticeScope = sysNotice.getNoticeScope();
        if (StrUtil.isBlank(sysNoticeRequest.getNoticeScope())) {
            sysNoticeRequest.setNoticeScope(NOTICE_SCOPE_ALL);
        }
        if (sysNoticeRequest.equals(sysNotice.getNoticeScope())) {
            throw new ServiceException(NoticeExceptionEnum.NOTICE_SCOPE_NOT_EDIT);
        }
        BeanUtil.copyProperties(sysNoticeRequest, sysNotice);

        // 通知范围不允许修改
        sysNotice.setNoticeScope(noticeScope);

        if (this.updateById(sysNotice)) {
            sendMessage(sysNotice);
        }
    }


    @Override
    public void delete(SysNoticeRequest sysNoticeRequest) {
        SysNotice sysNotice = this.querySysNotice(sysNoticeRequest);
        // 逻辑删除
        sysNotice.setDelFlag(YesOrNotEnum.Y.getCode());
        this.updateById(sysNotice);
    }

    @Override
    public SysNotice detail(SysNoticeRequest sysNoticeRequest) {
        return this.querySysNotice(sysNoticeRequest);
    }

    @Override
    public PageResult<SysNotice> page(SysNoticeRequest sysNoticeRequest) {
        LambdaQueryWrapper<SysNotice> wrapper = createWrapper(sysNoticeRequest);
        Page<SysNotice> page = this.page(PageFactory.defaultPage(), wrapper);
        return PageResultFactory.createPageResult(page);
    }

    @Override
    public List<SysNotice> list(SysNoticeRequest sysNoticeRequest) {
        LambdaQueryWrapper<SysNotice> wrapper = createWrapper(sysNoticeRequest);
        return this.list(wrapper);
    }


    /**
     * 获取通知管理
     *
     * @author liuhanqing
     * @date 2021/1/9 16:56
     */
    private SysNotice querySysNotice(SysNoticeRequest sysNoticeRequest) {
        SysNotice sysNotice = this.getById(sysNoticeRequest.getNoticeId());
        if (ObjectUtil.isNull(sysNotice)) {
            throw new ServiceException(NoticeExceptionEnum.NOTICE_NOT_EXIST);
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
        if (ObjectUtil.isNotNull(sysNoticeRequest)) {
            // 根据id查询
            if (ObjectUtil.isNotEmpty(sysNoticeRequest.getNoticeId())) {
                queryWrapper.eq(SysNotice::getNoticeId, sysNoticeRequest.getNoticeId());
            }

            // 根据消息标题模糊查询
            if (ObjectUtil.isNotEmpty(sysNoticeRequest.getNoticeTitle())) {
                queryWrapper.like(SysNotice::getNoticeTitle, sysNoticeRequest.getNoticeTitle()).or().like(SysNotice::getNoticeSummary, sysNoticeRequest.getNoticeTitle()).or().like(SysNotice::getNoticeContent, sysNoticeRequest.getNoticeTitle());
            }

        }

        // 查询未删除状态的
        queryWrapper.eq(SysNotice::getDelFlag, YesOrNotEnum.N.getCode());

        return queryWrapper;
    }

}