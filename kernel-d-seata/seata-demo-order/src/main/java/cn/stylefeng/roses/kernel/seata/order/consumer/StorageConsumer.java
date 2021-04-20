package cn.stylefeng.roses.kernel.seata.order.consumer;

import cn.stylefeng.roses.kernel.seata.api.StorageApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 仓储api
 * @author wangyl
 * @Date 2021-04-10 9:25:13
 */
@FeignClient(name = "seata-demo-storage")
public interface StorageConsumer extends StorageApi {
}
