package cn.stylefeng.roses.kernel.seata.wallet.api;

/**
 * 用户钱包 api
 *
 * @author wangyl
 * @date 2021/04/10 16:42
 */
public interface WalletApi {

    /**
     * 从用户账户中借出
     */
    void debit(String userId, int money);

}
