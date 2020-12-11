package cn.stylefeng.roses.kernel.pinyin.api.context;

import cn.hutool.extra.spring.SpringUtil;
import cn.stylefeng.roses.kernel.pinyin.api.PinYinApi;

/**
 * 拼音工具类快速获取
 *
 * @author fengshuonan
 * @date 2020/12/4 9:31
 */
public class PinyinContext {

    /**
     * 获取拼音工具类
     *
     * @author fengshuonan
     * @date 2020/12/4 9:36
     */
    public static PinYinApi me() {
        return SpringUtil.getBean(PinYinApi.class);
    }

}
