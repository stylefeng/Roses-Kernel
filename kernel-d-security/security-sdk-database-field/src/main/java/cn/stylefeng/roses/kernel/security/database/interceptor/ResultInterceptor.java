package cn.stylefeng.roses.kernel.security.database.interceptor;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kernel.security.database.algorithm.EncryptAlgorithmApi;
import cn.stylefeng.roses.kernel.security.database.annotation.ProtectedData;
import cn.stylefeng.roses.kernel.security.database.annotation.ProtectedField;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.*;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

/**
 * Mybatis拦截器，拦截返回参数
 *
 * @author majianguo
 * @date 2021/7/3 11:58
 */
@Slf4j
@Component
@Intercepts({@Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class})})
public class ResultInterceptor implements Interceptor {

    @Resource
    private EncryptAlgorithmApi encryptAlgorithmApi;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        //取出查询的结果
        Object resultObject = invocation.proceed();
        if (Objects.isNull(resultObject)) {
            return null;
        }

        // 判断结果是List还是对象
        if (resultObject instanceof List) {
            List resultList = (List) resultObject;
            // 判断是否为空
            if (ObjectUtil.isNotNull(resultList)) {
                // 处理数据
                for (Object result : resultList) {
                    // 对象处理
                    this.objectProcessing(result);
                }
            }
        } else {
            // 处理单个对象
            this.objectProcessing(resultObject);
        }

        return resultObject;
    }

    /**
     * 对象处理
     *
     * @return
     * @author majianguo
     * @date 2021/7/5 9:52
     **/
    private void objectProcessing(Object result) throws IllegalAccessException {
        Class<?> resultClass = result.getClass();
        // 是否加注解了
        ProtectedData annotation = AnnotationUtils.findAnnotation(resultClass, ProtectedData.class);

        // 加注解就去处理
        if (ObjectUtil.isNotNull(annotation)) {
            Field[] declaredFields = resultClass.getDeclaredFields();
            for (Field field : declaredFields) {
                // 处理字段
                this.fieldProcessing(result, field);
            }
        }
    }

    /**
     * @param result
     * @param field
     * @return
     * @author majianguo
     * @date 2021/7/5 9:52
     **/
    private void fieldProcessing(Object result, Field field) throws IllegalAccessException {
        if (this.isTag(field)) {
            field.setAccessible(true);
            Object object = field.get(result);
            //String的解密
            if (object instanceof String) {
                String value = (String) object;
                //对注解的字段进行逐一解密
                try {
                    String decrypt = encryptAlgorithmApi.decrypt(value);
                    field.set(result, decrypt);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 是否注解标记了
     *
     * @param field 被判断字段
     * @return {@link boolean}
     * @author majianguo
     * @date 2021/7/5 9:35
     **/
    private boolean isTag(Field field) {
        // 包含其中任意一个即可
        ProtectedField protectedField = field.getAnnotation(ProtectedField.class);
        if (ObjectUtil.isNotNull(protectedField)) {
            return true;
        }
        return false;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
