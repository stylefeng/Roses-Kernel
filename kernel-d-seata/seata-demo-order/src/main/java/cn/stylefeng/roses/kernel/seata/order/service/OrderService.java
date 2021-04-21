package cn.stylefeng.roses.kernel.seata.order.service;

import cn.stylefeng.roses.kernel.seata.order.entity.Order;

/**
 * 订单 业务层
 *
 * @author wangyl
 * @date 2021/04/21 08:33
 */
public interface OrderService {

    /**
     * 创建订单
     *
     * @param userId        用户ID
     * @param commodityCode 商品编码
     * @param orderCount    购买数量
     * @author wangyl
     * @date 2021/4/21 9:43
     */
    Order create(String userId, String commodityCode, int orderCount);

}
