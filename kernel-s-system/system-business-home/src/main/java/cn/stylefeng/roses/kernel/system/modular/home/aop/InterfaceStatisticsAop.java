package cn.stylefeng.roses.kernel.system.modular.home.aop;

import cn.stylefeng.roses.kernel.cache.api.CacheOperatorApi;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 接口统计的AOP
 *
 * @author xixiaowei
 * @date 2022/2/10 9:56
 */
@Slf4j
@Aspect
public class InterfaceStatisticsAop {

    @Resource(name = "interCacheApi")
    private CacheOperatorApi<String> interCacheApi;

    @Pointcut(value =  "@annotation(cn.stylefeng.roses.kernel.scanner.api.annotation.GetResource) ||" +
        "@annotation(cn.stylefeng.roses.kernel.scanner.api.annotation.PostResource) ")
    public void flowControl() {

    }

    @Around(value = "flowControl()")
    public Object flowControl(ProceedingJoinPoint joinPoint) {
        Object proceed = null;
        try {
            proceed = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            //方法执行后
            saveFlowControl(joinPoint);
        }
        return proceed;
    }

    private void saveFlowControl(ProceedingJoinPoint joinPoint) {
        String name = joinPoint.getSignature().getName();

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        // 获取URL
        String url = request.getRequestURI();

        // 存放到缓存中
        interCacheApi.put(name, url, 600L);
    }
}
