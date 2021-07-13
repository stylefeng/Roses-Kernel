package cn.stylefeng.roses.kernel.demo.util;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;

import java.util.Date;

/**
 * 开始时间的计时工具
 *
 * @author fengshuonan
 * @date 2021/7/13 17:34
 */
public class StartCalcUtil {

    /**
     * 项目开始启动时间
     */
    public static Date startDate = null;

    /**
     * 初始化项目开始时间
     *
     * @author fengshuonan
     * @date 2021/7/13 17:42
     */
    public static void init(Date date) {
        startDate = date;
    }

    /**
     * 计算是否项目已经启动
     *
     * @author fengshuonan
     * @date 2021/7/13 17:43
     */
    public static boolean calcEnable(Date date, long startInterValSeconds) {
        return DateUtil.between(startDate, date, DateUnit.SECOND) > startInterValSeconds;
    }

}
