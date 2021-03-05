package cn.stylefeng.roses.kernel.monitor.prometheus.mapper;



/**
 * 关闭或者打开prometheus菜单接口
 *
 * @author chenli
 * @date 2021/3/3 16:12
 */
public interface PrometheusMenuMapper {

    /***
     * 功能描述:   关闭或者打开prometheus菜单
     * 创建时间:  2021/3/3 16:12
     * @author  chenli
     */
    /**
     * 关闭或者打开prometheus菜单
     * @param statusFlag 1启用2关闭
     * @author chenli
     * @date 2021/3/3 16:12
     */
    void displayOrClosePrometheusMenu(int statusFlag);

}
