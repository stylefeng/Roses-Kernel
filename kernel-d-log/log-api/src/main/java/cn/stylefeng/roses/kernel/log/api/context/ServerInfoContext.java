package cn.stylefeng.roses.kernel.log.api.context;


import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.StrUtil;

/**
 * 临时缓存服务器信息
 *
 * @author fengshuonan
 * @date 2020/10/27 17:53
 */
public class ServerInfoContext {

    /**
     * 服务器IP
     */
    private static String serverIp;

    /**
     * 禁止new创建
     */
    private ServerInfoContext() {
    }

    /**
     * 获取server的ip
     *
     * @author fengshuonan
     * @date 2020/10/27 17:56
     */
    public static String getServerIp() {
        if (StrUtil.isEmpty(serverIp)) {
            serverIp = NetUtil.getLocalhostStr();
        }
        return serverIp;
    }

}
