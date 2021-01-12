package cn.stylefeng.roses.kernel.rule.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * IP工具类
 *
 * @author fengshuonan
 * @date 2021/1/10 14:25
 */
public class IpInfoUtils {

    /**
     * 获取当前机器的hostname
     *
     * @author fengshuonan
     * @date 2021/1/10 18:40
     */
    public static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException ignored) {
            return "未知";
        }
    }

}
