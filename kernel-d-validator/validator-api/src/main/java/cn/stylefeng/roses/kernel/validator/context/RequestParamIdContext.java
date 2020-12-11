package cn.stylefeng.roses.kernel.validator.context;

/**
 * 临时保存参数id字段值，用于唯一性校验
 * <p>
 * 注意：如果要用@TableUniqueValue这个校验，必须得主键的字段名是id，否则会校验失败
 *
 * @author fengshuonan
 * @date 2020/11/4 14:34
 */
public class RequestParamIdContext {

    private static final ThreadLocal<Long> PARAM_ID_HOLDER = new ThreadLocal<>();

    /**
     * 设置临时缓存的id
     *
     * @author fengshuonan
     * @date 2020/11/4 14:35
     */
    public static void set(Long id) {
        PARAM_ID_HOLDER.set(id);
    }

    /**
     * 获取临时缓存的id
     *
     * @author fengshuonan
     * @date 2020/11/4 14:35
     */
    public static Long get() {
        return PARAM_ID_HOLDER.get();
    }

    /**
     * 清除临时缓存的id
     *
     * @author fengshuonan
     * @date 2020/11/4 14:35
     */
    public static void clear() {
        PARAM_ID_HOLDER.remove();
    }

}
