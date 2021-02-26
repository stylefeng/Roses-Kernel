package cn.stylefeng.roses.kernel.message.modular.websocket;

import cn.stylefeng.roses.kernel.auth.api.context.LoginContext;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.LoginUser;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.GetResource;
import org.springframework.web.bind.annotation.RestController;

/**
 * websocket控制器
 *
 * @author liuhanqing
 * @date 2021/2/3 21:08
 */
@RestController
@ApiResource(name = "webSocket控制器")
public class WebSocketController {

    /**
     * 获取登录用户ws-url
     *
     * @author liuhanqing
     * @date 2021/2/3 21:15
     */
    @GetResource(name = "获取登录用户ws-url", path = "/webSocket/getWsUrl")
    public ResponseData getWsUrl() {
        LoginUser loginUser = LoginContext.me().getLoginUser();
        return new SuccessResponseData(loginUser.getWsUrl());
    }

}
