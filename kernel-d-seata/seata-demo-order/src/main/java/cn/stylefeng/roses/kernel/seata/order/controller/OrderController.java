package cn.stylefeng.roses.kernel.seata.order.controller;

import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.seata.order.entity.Order;
import cn.stylefeng.roses.kernel.seata.order.service.OrderService;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

/**
 * 订单接口
 *
 * @author wangyl
 * @date 2021/04/10 16:42
 */
@ApiResource(name = "订单接口（测试seata）")
public class OrderController {

    @Resource
    private OrderService orderService;

    /**
     * 创建订单
     *
     * @author wangyl
     * @date 2021/4/20 20:11
     */
    @GetResource(name = "创建订单", path = "/order/create", requiredPermission = false, requiredLogin = false)
    public Order create(@RequestParam("userId") String userId, @RequestParam("commodityCode") String commodityCode, @RequestParam("orderCount") Integer orderCount) {
        return orderService.create(userId, commodityCode, orderCount);
    }

}
