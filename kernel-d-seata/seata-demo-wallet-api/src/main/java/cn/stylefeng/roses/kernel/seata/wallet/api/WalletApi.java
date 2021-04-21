package cn.stylefeng.roses.kernel.seata.wallet.api;

/**
 * 用户钱包 api
 *
 * @author wangyl
 * @date 2021/04/10 16:42
 */
public interface WalletApi {

    /**
     * 从用户账户中扣除余额
     *
     * @param userId 用户ID
     * @param money  消费金额
     * @author wangyl
     * @date 2021/4/21 9:44
     */
    void debit(String userId, Integer money);

}
