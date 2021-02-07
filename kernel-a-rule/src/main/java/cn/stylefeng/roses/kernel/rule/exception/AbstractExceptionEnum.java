package cn.stylefeng.roses.kernel.rule.exception;

/**
 * 异常枚举格式规范，每个异常枚举都要实现这个接口
 * <p>
 * 为了在抛出ServiceException的时候规范抛出的内容
 * <p>
 * ServiceException抛出时必须为本接口的实现类
 *
 * @author fengshuonan
 * @date 2020/10/14 21:41
 */
public interface AbstractExceptionEnum {

    /**
     * 获取异常的状态码
     *
     * @return 状态码
     * @author fengshuonan
     * @date 2020/10/14 21:42
     */
    String getErrorCode();

    /**
     * 获取给用户提示信息
     *
     * @return 提示信息
     * @author fengshuonan
     * @date 2020/10/14 21:42
     */
    String getUserTip();

}
