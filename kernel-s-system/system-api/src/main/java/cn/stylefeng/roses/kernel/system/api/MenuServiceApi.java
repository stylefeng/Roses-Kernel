package cn.stylefeng.roses.kernel.system.api;

/**
 * 菜单api
 *
 * @author fengshuonan
 * @date 2020/11/24 21:37
 */
public interface MenuServiceApi {

    /**
     * 根据应用编码判断该机构下是否有状态为正常的菜单
     *
     * @param appCode 应用编码
     * @return 该应用下是否有正常菜单，true是，false否
     * @author fengshuonan
     * @date 2020/11/24 21:37
     */
    boolean hasMenu(String appCode);

}
