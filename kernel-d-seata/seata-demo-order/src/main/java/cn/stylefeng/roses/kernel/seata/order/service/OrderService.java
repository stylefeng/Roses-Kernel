package cn.stylefeng.roses.kernel.seata.order.service;

import cn.stylefeng.roses.kernel.seata.order.entity.Order;

public interface OrderService {

    Order create(String userId, String commodityCode, int orderCount);

}
