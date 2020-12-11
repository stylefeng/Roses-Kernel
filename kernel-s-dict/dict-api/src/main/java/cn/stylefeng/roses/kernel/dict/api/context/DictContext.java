package cn.stylefeng.roses.kernel.dict.api.context;

import cn.hutool.extra.spring.SpringUtil;
import cn.stylefeng.roses.kernel.dict.api.DictApi;

/**
 * 字典模块，对外的api
 *
 * @author fengshuonan
 * @date 2020/10/29 11:39
 */
public class DictContext {

    /**
     * 获取字典相关操作接口
     *
     * @author fengshuonan
     * @date 2020/10/29 11:55
     */
    public static DictApi me() {
        return SpringUtil.getBean(DictApi.class);
    }

}
