package cn.stylefeng.roses.kernel.scanner.api.holder;

import cn.stylefeng.roses.kernel.rule.threadlocal.RemoveThreadLocalApi;
import org.springframework.stereotype.Component;

/**
 * 清除ip地址相关的ThreadLocalHolder
 *
 * @author fengshuonan
 * @date 2021/10/29 11:42
 */
@Component
public class IpAddrRemoveThreadLocalHolder implements RemoveThreadLocalApi {

    @Override
    public void removeThreadLocalAction() {
        IpAddrHolder.clear();
    }

}
