package cn.stylefeng.roses.kernel.message.db.service;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.message.api.pojo.MessageParam;
import cn.stylefeng.roses.kernel.message.db.entity.SysMessage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 系统消息 service接口
 *
 * @author liuhanqing
 * @date 2020/12/31 20:09
 */
public interface SysMessageService extends IService<SysMessage> {
    /**
     * 分页查询
     *
     * @param messageParam 参数
     * @author liuhanqing
     * @date 2021/1/2 15:21
     */
    PageResult<SysMessage> page(MessageParam messageParam);

    /**
     * 列表查询
     *
     * @param messageParam 参数
     * @author liuhanqing
     * @date 2021/1/8 15:21
     */
    List<SysMessage> list(MessageParam messageParam);
}
