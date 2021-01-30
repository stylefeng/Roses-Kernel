package cn.stylefeng.roses.kernel.groovy.api;

/**
 * groovy动态脚本操作api
 *
 * @author fengshuonan
 * @date 2021/1/22 16:29
 */
public interface GroovyApi {

    /**
     * 将文本形式的java类转换成class对象
     *
     * @param javaClassCode java类的文本信息
     * @author fengshuonan
     * @date 2021/1/22 16:30
     */
    Class<?> textToClass(String javaClassCode);

    /**
     * 执行java语句
     *
     * @param javaCode java语句的文本信息
     * @author fengshuonan
     * @date 2021/1/22 16:31
     */
    Object executeShell(String javaCode);

    /**
     * 将java类代码转换成class，并执行某个方法
     *
     * @param javaClassCode  java类的代码
     * @param method         java类中某个方法名
     * @param parameterTypes 参数类型集合
     * @param args           参数的具体对象
     * @author fengshuonan
     * @date 2021/1/22 16:31
     */
    Object executeClassMethod(String javaClassCode, String method, Class<?>[] parameterTypes, Object[] args);

}
