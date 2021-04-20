package cn.stylefeng.roses.kernel.auth.session.timer;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.LoginUser;
import cn.stylefeng.roses.kernel.cache.api.CacheOperatorApi;
import cn.stylefeng.roses.kernel.timer.api.TimerAction;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 定时清除无用的登录用户缓存
 *
 * @author fengshuonan
 * @date 2021/3/30 11:19
 */
public class ClearInvalidLoginUserCacheTimer implements TimerAction {

    /**
     * 登录用户缓存
     */
    private final CacheOperatorApi<LoginUser> loginUserCacheOperatorApi;

    /**
     * 用户token的缓存，这个缓存用来存储一个用户的所有token
     */
    private final CacheOperatorApi<Set<String>> allPlaceLoginTokenCache;

    public ClearInvalidLoginUserCacheTimer(CacheOperatorApi<LoginUser> loginUserCacheOperatorApi, CacheOperatorApi<Set<String>> allPlaceLoginTokenCache) {
        this.loginUserCacheOperatorApi = loginUserCacheOperatorApi;
        this.allPlaceLoginTokenCache = allPlaceLoginTokenCache;
    }

    @Override
    public void action(String params) {
        Collection<String> allOnlineUsers = allPlaceLoginTokenCache.getAllKeys();
        if (ObjectUtil.isNotEmpty(allOnlineUsers)) {
            for (String userId : allOnlineUsers) {

                // 获取当前用户的所有token
                Set<String> userTokens = allPlaceLoginTokenCache.get(userId);

                // 新的userToken
                Set<String> newUserTokens = new HashSet<>();

                // 因为有的token用户没有点退出清空，这里遍历一下，清空无效的userToken
                for (String userToken : userTokens) {
                    LoginUser loginUser = loginUserCacheOperatorApi.get(userToken);
                    if (loginUser != null) {
                        newUserTokens.add(userToken);
                    }
                }

                // 如果userToken都过期了，这个set整体清除掉
                if (ObjectUtil.isEmpty(newUserTokens)) {
                    allPlaceLoginTokenCache.remove(userId);
                } else {
                    allPlaceLoginTokenCache.put(userId, newUserTokens);
                }
            }
        }
    }

}
