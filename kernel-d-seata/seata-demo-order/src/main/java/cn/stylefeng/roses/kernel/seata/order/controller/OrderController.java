package cn.stylefeng.roses.kernel.seata.order.controller;

import cn.stylefeng.roses.kernel.seata.order.entity.Order;
import cn.stylefeng.roses.kernel.seata.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 订单接口
 * @author wangyl
 * @Date 2021-04-10 9:25:13
 */
public class OrderController {

    @Autowired
    OrderService orderService;

    /**
     * 创建订单
     */
    @GetMapping("/create")
    Order create(String userId, String commodityCode, int orderCount){
        return orderService.create(userId,commodityCode,orderCount);
    }

}
