package cn.stylefeng.roses.kernel.db.api.context;

import cn.hutool.extra.spring.SpringUtil;
import cn.stylefeng.roses.kernel.db.api.DbOperatorApi;

/**
 * 获取sql操作器
 *
 * @author fengshuonan
 * @date 2020/11/4 15:07
 */
public class DbOperatorContext {

    public static DbOperatorApi me() {
        return SpringUtil.getBean(DbOperatorApi.class);
    }

}
