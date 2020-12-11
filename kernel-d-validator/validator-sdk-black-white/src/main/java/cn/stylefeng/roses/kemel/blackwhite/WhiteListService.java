package cn.stylefeng.roses.kemel.blackwhite;

import cn.stylefeng.roses.kernel.cache.api.CacheOperatorApi;
import cn.stylefeng.roses.kernel.validator.WhiteListApi;

import java.util.Collection;

/**
 * 白名单的实现
 * <p>
 * 白名单的数据在访问资源时不受限
 *
 * @author fengshuonan
 * @date 2020/11/20 15:53
 */
public class WhiteListService implements WhiteListApi {

    private final CacheOperatorApi<String> cacheOperatorApi;

    public WhiteListService(CacheOperatorApi<String> cacheOperatorApi) {
        this.cacheOperatorApi = cacheOperatorApi;
    }

    @Override
    public void addWhiteItem(String content) {
        cacheOperatorApi.put(content, content);
    }

    @Override
    public void removeWhiteItem(String content) {
        cacheOperatorApi.remove(content);
    }

    @Override
    public Collection<String> getWhiteList() {
        return cacheOperatorApi.getAllKeys();
    }

    @Override
    public boolean contains(String content) {
        return cacheOperatorApi.contains(content);
    }

}
