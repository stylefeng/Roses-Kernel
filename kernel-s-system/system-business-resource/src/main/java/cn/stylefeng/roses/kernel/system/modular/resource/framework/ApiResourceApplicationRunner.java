package cn.stylefeng.roses.kernel.system.modular.resource.framework;

import cn.stylefeng.roses.kernel.system.modular.resource.service.ApiResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 刷新表中的数据
 * <p>
 * 把表中的resource_code中的app code和当前启动的app code对应
 *
 * @author majianguo
 * @date 2021/12/2 11:58
 */
@Slf4j
@Component
@Order(Integer.MAX_VALUE - 1)
public class ApiResourceApplicationRunner implements ApplicationRunner {

    @Resource
    private ApiResourceService apiResourceService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        apiResourceService.refreshTableData();
    }
}
