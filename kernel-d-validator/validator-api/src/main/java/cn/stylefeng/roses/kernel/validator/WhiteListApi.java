package cn.stylefeng.roses.kernel.validator;


import java.util.Collection;

/**
 * 白名单Api
 * <p>
 * 在白名单的用户不会进行被访问受限
 *
 * @author fengshuonan
 * @date 2020/11/15 16:31
 */
public interface WhiteListApi {

    /**
     * 添加名单条目
     *
     * @param content 白名单的一条内容，可以是ip，用户id之类的
     * @author fengshuonan
     * @date 2020/11/15 16:32
     */
    void addWhiteItem(String content);

    /**
     * 删除名单条目
     *
     * @param content 白名单的一条内容，可以是ip，用户id之类的
     * @author fengshuonan
     * @date 2020/11/15 16:32
     */
    void removeWhiteItem(String content);

    /**
     * 获取整个白名单
     *
     * @return 白名单内容的列表
     * @author fengshuonan
     * @date 2020/11/15 16:33
     */
    Collection<String> getWhiteList();

    /**
     * 是否包含某个值
     *
     * @param content 白名单的一条内容，可以是ip，用户id之类的
     * @return true-包含值，false-不包含值
     * @author fengshuonan
     * @date 2020/11/20 16:55
     */
    boolean contains(String content);

}
