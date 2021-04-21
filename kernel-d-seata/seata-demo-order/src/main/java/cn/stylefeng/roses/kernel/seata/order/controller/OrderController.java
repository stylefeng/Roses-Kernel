package cn.stylefeng.roses.kernel.seata.order.controller;

import cn.stylefeng.roses.kernel.seata.order.entity.Order;
import cn.stylefeng.roses.kernel.seata.order.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

/**
 * 订单接口
 *
 * @author wangyl
 * @date 2021/04/10 16:42
 */
public class OrderController {

    @Resource
    private OrderService orderService;

    /**
     * 创建订单
     *
     * @author wangyl
     * @date 2021/4/20 20:11
     */
    @GetMapping("/create")
    public Order create(@RequestParam("userId") String userId, @RequestParam("commodityCode") String commodityCode, @RequestParam("orderCount") Integer orderCount){
        return orderService.create(userId,commodityCode,orderCount);
    }

}
