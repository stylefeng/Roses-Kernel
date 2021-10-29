package cn.stylefeng.roses.kernel.auth.api.context;

import cn.stylefeng.roses.kernel.rule.threadlocal.RemoveThreadLocalApi;
import org.springframework.stereotype.Component;

/**
 * 清除当前登录用户相关的ThreadLocalHolder
 *
 * @author fengshuonan
 * @date 2021/10/29 11:41
 */
@Component
public class LoginUserRemoveThreadLocalHolder implements RemoveThreadLocalApi {

    @Override
    public void removeThreadLocalAction() {
        LoginUserHolder.remove();
    }

}
