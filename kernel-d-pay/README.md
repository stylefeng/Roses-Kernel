# 支付模块

## 接入支付宝支付

### application.yml添加支付宝支付appid等信息

```yml
alipay:
  appId: 2021000117660206
  gatewayHost: openapi.alipaydev.com
  notifyUrl: http://101.132.1.2:8001/pay/notify_url
  merchantPrivateKey: MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC+g1/v3Z968
  alipayPublicKey: MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIB
```

注：openapi.alipaydev.com 是沙盒环境 ，正式环境需要更换网关

gatewayHost：必须是外网穿透host，否则阿里无法回调成功

### 使用Demo

```java
package com.alipay.controller;

import cn.hutool.core.lang.UUID;
import cn.stylefeng.roses.kernel.pay.api.PayApi;
import cn.stylefeng.roses.kernel.pay.api.pojo.TradeRefundResponse;
import com.alipay.easysdk.factory.Factory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 支付模块测试
 *
 * @author huziyang
 * @date 2021/05/29 21:38
 */
@RestController
@RequestMapping("/test")
@Slf4j
public class AlipayTest {


    @Resource
    private PayApi payApi;

    /**
     * PC支付
     *
     * @return 支付页面
     * @author huziyang
     * @date 2021/04/20 20:43
     */
    @GetMapping("/page")
    public String page(){
        return payApi.page("xx全屋定制", "eb58cd5c-7613-41ce-93ef-fcf0ad4284f9","12.5",null);
    }

    /**
     * 手机支付
     *
     * @return 支付页面
     * @author huziyang
     * @date 2021/04/20 20:43
     */
    @GetMapping("/wap")
    public String wap(){
        return payApi.wap("xx全屋定制", "eb58cd5c-7613-41ce-93ef-fcf0ad4284f8","12.5",null,null);
    }

    /**
     * 退款
     * 
     * @return 退款实体
     * @author huziyang
     * @date 2021/04/20 20:43
     */
    @PostMapping("/refund")
    public TradeRefundResponse refund() {
        return payApi.refund("eb58cd5c-7613-41ce-93ef-fcf0ad4284f8", "12.5");
    }


    /**
     * 支付宝回调
     *
     * @param request
     * @throws Exception
     * @author huziyang
     * @date 2021/04/20 20:43
     */
    @PostMapping("/notify_url")
    public void notify(HttpServletRequest request) throws Exception {
        if (request.getParameter("trade_status").equals("TRADE_SUCCESS")) {
            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (String name : requestParams.keySet()) {
                params.put(name, request.getParameter(name));
            }
            if (Factory.Payment.Common().verifyNotify(params)) {
                log.info("支付宝异步回调成功");
                log.info("订单名称: " + params.get("subject"));
                log.info("交易状态: " + params.get("trade_status"));
                log.info("支付宝交易凭证号: " + params.get("trade_no"));
                log.info("商家订单号: " + params.get("out_trade_no"));
                log.info("交易金额: " + params.get("total_amount"));
                log.info("支付宝唯一id: " + params.get("buyer_id"));
                log.info("付款时间: " + params.get("gmt_payment"));
                log.info("付款金额: " + params.get("buyer_pay_amount"));
            }
        }
    }
}
```

### pom中引用支付依赖

```xml
<dependency>
  <groupId>cn.stylefeng.roses</groupId>
  <artifactId>pay-spring-boot-starter</artifactId>
  <version>7.1.1</version>
</dependency>
```

至此支付宝支付就接入成功啦！



如果需要配置应用公钥证书文件路径等信息，参照如下yml（具体配置参数可以查看支付宝支付官方文档）

```yml
alipay:
  appId:
  gatewayHost:
  notifyUrl:
  merchantPrivateKey:
  alipayPublicKey:
  encryptKey:
  merchantCertPath:
  alipayCertPath:
  alipayRootCertPath:
```

