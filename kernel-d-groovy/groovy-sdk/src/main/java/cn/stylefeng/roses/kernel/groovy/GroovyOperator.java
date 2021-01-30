package cn.stylefeng.roses.kernel.groovy;

import cn.stylefeng.roses.kernel.groovy.api.GroovyApi;
import cn.stylefeng.roses.kernel.groovy.api.exception.GroovyException;
import cn.stylefeng.roses.kernel.groovy.api.exception.enums.GroovyExceptionEnum;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyShell;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * groovy动态脚本操作实现
 *
 * @author fengshuonan
 * @date 2021/1/22 16:28
 */
@Slf4j
public class GroovyOperator implements GroovyApi {

    @Override
    public Class<?> textToClass(String javaClassCode) {
        GroovyClassLoader groovyClassLoader = new GroovyClassLoader();
        return (Class<?>) groovyClassLoader.parseClass(javaClassCode);
    }

    @Override
    public Object executeShell(String javaCode) {
        GroovyShell shell = new GroovyShell();
        return shell.evaluate(javaCode);
    }

    @Override
    public Object executeClassMethod(String javaClassCode, String method, Class<?>[] parameterTypes, Object[] args) {
        try {
            Class<?> clazz = this.textToClass(javaClassCode);
            Method clazzMethod = clazz.getMethod(method, parameterTypes);
            Object obj = clazz.newInstance();
            return clazzMethod.invoke(obj, args);
        } catch (Exception e) {
            log.error("执行groovy类中方法出错！", e);
            throw new GroovyException(GroovyExceptionEnum.GROOVY_EXE_ERROR);
        }
    }

}
