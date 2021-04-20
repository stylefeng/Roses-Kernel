package cn.stylefeng.roses.kernel.seata.order.consumer;

import cn.stylefeng.roses.kernel.seata.wallet.api.WalletApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 用户钱包 api
 *
 * @author wangyl
 * @date 2021/04/10 16:42
 */
@FeignClient(name = "seata-demo-wallet")
public interface WalletConsumer extends WalletApi {
}
