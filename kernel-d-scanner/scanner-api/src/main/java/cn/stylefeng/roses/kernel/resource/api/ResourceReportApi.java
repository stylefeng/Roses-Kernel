package cn.stylefeng.roses.kernel.resource.api;

import cn.stylefeng.roses.kernel.resource.api.pojo.resource.ReportResourceParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 资源持久化服务api，将扫描的资源汇报给系统管理用
 *
 * @author fengshuonan
 * @date 2018-02-06 14:30
 */
public interface ResourceReportApi {

    /**
     * 持久化资源集合到某个服务中
     * <p>
     * 如果是单体项目，则吧资源汇报给本服务
     * <p>
     * 如果是微服务项目，则会有个consumer会将本服务的资源发送给资源管理者（一般为system服务）
     *
     * @param reportResourceReq 资源汇报接口
     * @author fengshuonan
     * @date 2020/10/19 22:02
     */
    @RequestMapping(value = "/resourceService/reportResources", method = RequestMethod.POST)
    void reportResources(@RequestBody ReportResourceParam reportResourceReq);

}
