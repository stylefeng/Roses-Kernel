package cn.stylefeng.roses.kernel.rule.threadlocal;

/**
 * 对程序进行拓展，方便清除ThreadLocal
 *
 * @author fengshuonan
 * @date 2021/10/29 11:14
 */
public interface RemoveThreadLocalApi {

    /**
     * 具体删除ThreadLocal的逻辑
     *
     * @author fengshuonan
     * @date 2021/10/29 11:19
     */
    void removeThreadLocalAction();

}
