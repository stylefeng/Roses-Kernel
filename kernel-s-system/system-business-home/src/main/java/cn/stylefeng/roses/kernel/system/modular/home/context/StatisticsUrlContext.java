package cn.stylefeng.roses.kernel.system.modular.home.context;

import cn.stylefeng.roses.kernel.system.modular.home.entity.SysStatisticsUrl;
import cn.stylefeng.roses.kernel.system.modular.home.service.SysStatisticsUrlService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 需要被首页常用功能统计的url集合
 *
 * @author fengshuonan
 * @date 2022/2/10 21:35
 */
@Component
public class StatisticsUrlContext implements CommandLineRunner {

    @Resource
    private SysStatisticsUrlService sysStatisticsUrlService;

    /**
     * 需要被统计的url
     */
    private static List<SysStatisticsUrl> STATISTICS_URLS = new ArrayList<>(10);

    /**
     * 需要被统计的url集合，key是url，value是stat_url_id
     */
    private static final Map<String, Long> STATISTICS_KEY_VALUES = new HashMap<>(10);

    /**
     * 获取需要统计的url集合
     *
     * @author fengshuonan
     * @date 2022/2/10 21:37
     */
    public static List<SysStatisticsUrl> getUrls() {
        return STATISTICS_URLS;
    }

    /**
     * 获取url对应的stat_url_id
     *
     * @author fengshuonan
     * @date 2022/2/10 21:37
     */
    public static Long getStatUrlId(String url) {
        return STATISTICS_KEY_VALUES.get(url);
    }

    /**
     * 初始化被统计的url集合
     *
     * @author fengshuonan
     * @date 2022/2/10 21:37
     */
    @Override
    public void run(String... args) throws Exception {
        STATISTICS_URLS = sysStatisticsUrlService.list();
        for (SysStatisticsUrl statisticsUrl : STATISTICS_URLS) {
            STATISTICS_KEY_VALUES.put(statisticsUrl.getStatUrl(), statisticsUrl.getStatUrlId());
        }
    }

}
