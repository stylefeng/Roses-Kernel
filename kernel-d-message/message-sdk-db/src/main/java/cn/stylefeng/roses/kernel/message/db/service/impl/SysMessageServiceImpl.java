package cn.stylefeng.roses.kernel.message.db.service.impl;

import cn.stylefeng.roses.kernel.message.db.entity.SysMessage;
import cn.stylefeng.roses.kernel.message.db.mapper.SysMessageMapper;
import cn.stylefeng.roses.kernel.message.db.service.SysMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 系统消息 service接口实现类
 *
 * @author liuhanqing
 * @date 2020/12/31 20:09
 */
@Service
public class SysMessageServiceImpl extends ServiceImpl<SysMessageMapper, SysMessage> implements SysMessageService {

}
