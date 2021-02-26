package cn.stylefeng.roses.kernel.scanner.api.holder;

/**
 * IP地址的临时存储 用在资源扫描
 * <p>
 * 获取ip地址的方法较慢，并且每个资源装配需要装填ip地址，所以用ThreadLocal临时缓存
 *
 * @author fengshuonan
 * @date 2020/10/19 21:15
 */
public class IpAddrHolder {

    private static final ThreadLocal<String> IP_ADDR_HOLDER = new ThreadLocal<>();

    public static void set(String ip) {
        IP_ADDR_HOLDER.set(ip);
    }

    public static String get() {
        return IP_ADDR_HOLDER.get();
    }

    public static void clear() {
        IP_ADDR_HOLDER.remove();
    }

}
