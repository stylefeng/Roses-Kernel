package cn.stylefeng.roses.kernel.auth.config;

import cn.stylefeng.roses.kernel.auth.api.SessionManagerApi;
import cn.stylefeng.roses.kernel.auth.api.context.LoginContext;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.LoginUser;
import cn.stylefeng.roses.kernel.config.api.ConfigInitCallbackApi;
import cn.stylefeng.roses.kernel.message.api.expander.WebSocketConfigExpander;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 项目初始化完成以后，修改用户websocket地址的配置
 *
 * @author majianguo
 * @date 2021/10/19 17:07
 */
@Component
public class InitConfigWebSocketCallbackApiImpl implements ConfigInitCallbackApi {

    @Resource
    private SessionManagerApi sessionManagerApi;

    @Override
    public void initBefore() {

    }

    @Override
    public void initAfter() {
        LoginUser loginUser = LoginContext.me().getLoginUser();
        loginUser.setWsUrl(WebSocketConfigExpander.getWebSocketWsUrl());
        sessionManagerApi.updateSession(loginUser.getToken(), loginUser);
    }
}
