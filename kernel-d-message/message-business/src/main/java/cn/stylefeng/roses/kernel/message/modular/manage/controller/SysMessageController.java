package cn.stylefeng.roses.kernel.message.modular.manage.controller;

import cn.stylefeng.roses.kernel.message.api.MessageApi;
import cn.stylefeng.roses.kernel.resource.api.annotation.ApiResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统消息控制器
 *
 * @author liuhanqing
 * @date 2021/1/1 22:30
 */
@RestController
@ApiResource(name = "系统消息控制器")
public class SysMessageController {

    /**
     * 系统消息api
     */
    @Autowired
    private MessageApi messageApi;


}
