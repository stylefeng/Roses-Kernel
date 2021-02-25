package cn.stylefeng.roses.kernel.system.api;

import cn.stylefeng.roses.kernel.rule.pojo.dict.SimpleDict;

import java.util.Set;

/**
 * 应用相关api
 *
 * @author fengshuonan
 * @date 2020/11/24 21:37
 */
public interface AppServiceApi {

    /**
     * 通过app编码获取app的详情
     *
     * @param appCodes 应用的编码
     * @return 应用的信息
     * @author fengshuonan
     * @date 2020/11/29 20:06
     */
    Set<SimpleDict> getAppsByAppCodes(Set<String> appCodes);

    /**
     * 通过app编码获取app的中文名
     *
     * @param appCode 应用的编码
     * @return 应用的中文名
     * @author fengshuonan
     * @date 2020/11/29 20:06
     */
    String getAppNameByAppCode(String appCode);

    /**
     * 获取当前激活的应用编码
     *
     * @return 激活的应用编码
     * @author fengshuonan
     * @date 2021/1/8 19:01
     */
    String getActiveAppCode();

}
