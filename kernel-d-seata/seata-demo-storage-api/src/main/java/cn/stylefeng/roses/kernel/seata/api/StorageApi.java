package cn.stylefeng.roses.kernel.seata.api;

/**
 * 仓储api
 *
 * @author wangyl
 * @date 2021-04-11 9:52:31
 */
public interface StorageApi {

    /**
     * 扣除存储数量
     */
    void deduct(String commodityCode, int count);

}
