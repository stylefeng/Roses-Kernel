package cn.stylefeng.roses.kernel.seata.order.mapper;

import cn.stylefeng.roses.kernel.seata.order.entity.Order;

/**
 * 数据层
 *
 * @author wangyl
 */
public interface OrderMapper {

    void insertOrder(Order order);

    Order selectById(Long orderId);

}
