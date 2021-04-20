package cn.stylefeng.roses.kernel.seata.api;

/**
 * 仓储 api
 *
 * @author wangyl
 * @date 2021/04/10 16:42
 */
public interface StorageApi {

    /**
     * 扣除存储数量
     */
    void deduct(String commodityCode, int count);

}
