package cn.stylefeng.roses.kernel.system;

import cn.stylefeng.roses.kernel.system.pojo.organization.DataScopeResponse;

/**
 * 数据范围的获取接口
 *
 * @author fengshuonan
 * @date 2020/11/6 11:54
 */
public interface DataScopeApi {

    /**
     * 获取用户的数据范围
     * <p>
     * 目前不考虑一个用户多角色多部门下的数据范围，只考虑一个用户只对应一个主部门下的数据范围
     * <p>
     * 此方法用在非超级管理员用户的获取数据范围
     *
     * @param userId 用户id
     * @return 数据范围内容
     * @author majianguo
     * @date 2020/11/5 上午11:44
     */
    DataScopeResponse getDataScope(Long userId);

}
