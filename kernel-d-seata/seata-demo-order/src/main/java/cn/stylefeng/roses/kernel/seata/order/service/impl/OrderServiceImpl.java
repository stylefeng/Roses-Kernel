package cn.stylefeng.roses.kernel.seata.order.service.impl;

import cn.stylefeng.roses.kernel.seata.order.consumer.StorageConsumer;
import cn.stylefeng.roses.kernel.seata.order.consumer.WalletConsumer;
import cn.stylefeng.roses.kernel.seata.order.entity.Order;
import cn.stylefeng.roses.kernel.seata.order.mapper.OrderMapper;
import cn.stylefeng.roses.kernel.seata.order.service.OrderService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private StorageConsumer storageConsumer;
    @Autowired
    private WalletConsumer walletConsumer;
    @Autowired
    private OrderMapper orderMapper;

    /**
     * 分布式事务跨库操作，起始位置，使用@GlobalTransactional注解修饰
     * @param userId
     * @param commodityCode
     * @param orderCount
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public Order create(String userId, String commodityCode, int orderCount) {
        Order order = new Order();
        //保存订单
        orderMapper.insertOrder(order);
        //扣减商品库存
        storageConsumer.deduct(commodityCode,orderCount);
        //扣用户钱
        walletConsumer.debit(userId,order.getTotalAmount());
        return order;
    }
}
