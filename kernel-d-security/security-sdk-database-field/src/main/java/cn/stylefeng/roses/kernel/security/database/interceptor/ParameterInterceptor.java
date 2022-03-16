package cn.stylefeng.roses.kernel.security.database.interceptor;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kernel.security.database.algorithm.EncryptAlgorithmApi;
import cn.stylefeng.roses.kernel.security.database.annotation.EncryptField;
import cn.stylefeng.roses.kernel.security.database.annotation.ProtectedData;
import cn.stylefeng.roses.kernel.security.database.annotation.ProtectedField;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.plugin.*;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.util.Properties;

/**
 * Mybatis拦截器，拦截入库参数
 *
 * @author majianguo
 * @date 2021/7/3 11:58
 */
@Slf4j
@Component
@Intercepts({@Signature(type = ParameterHandler.class, method = "setParameters", args = PreparedStatement.class),})
public class ParameterInterceptor implements Interceptor {

    @Resource
    private EncryptAlgorithmApi encryptAlgorithmApi;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        // 若指定ResultSetHandler ，这里则能强转为ResultSetHandler
        ParameterHandler parameterHandler = (ParameterHandler) invocation.getTarget();

        // 获取参数对像，即 mapper 中 paramsType 的实例
        Field parameterField = parameterHandler.getClass().getDeclaredField("parameterObject");
        parameterField.setAccessible(true);

        // 取出实例
        Object parameterObject = parameterField.get(parameterHandler);
        if (parameterObject != null) {
            Class<?> parameterObjectClass = parameterObject.getClass();

            // 校验该实例的类是否被@ProtectedData所注解
            ProtectedData protectedData = AnnotationUtils.findAnnotation(parameterObjectClass, ProtectedData.class);
            if (ObjectUtil.isNotNull(protectedData)) {

                //取出当前当前类所有字段
                Field[] declaredFields = parameterObjectClass.getDeclaredFields();

                // 处理需要加密的字段
                for (Field declaredField : declaredFields) {

                    // 包含其中任意一个即可
                    ProtectedField protectedField = declaredField.getAnnotation(ProtectedField.class);
                    EncryptField encryptField = declaredField.getAnnotation(EncryptField.class);
                    if (ObjectUtil.isNotNull(protectedField) || ObjectUtil.isNotNull(encryptField)) {
                        declaredField.setAccessible(true);
                        Object fieldData = declaredField.get(parameterObject);
                        // 如果是String就处理
                        if (fieldData instanceof String) {
                            String value = (String) fieldData;
                            try {
                                String encrypt = encryptAlgorithmApi.encrypt(value);
                                declaredField.set(parameterObject, encrypt);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
