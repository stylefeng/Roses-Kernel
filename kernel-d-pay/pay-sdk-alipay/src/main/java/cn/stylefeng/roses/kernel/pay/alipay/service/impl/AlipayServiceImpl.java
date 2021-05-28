/*
 * Copyright [2020-2030] [https://www.stylefeng.cn]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Guns采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Guns源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/stylefeng/guns
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/stylefeng/guns
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */
package cn.stylefeng.roses.kernel.pay.alipay.service.impl;

import cn.stylefeng.roses.kernel.pay.alipay.constants.AlipayConstants;
import cn.stylefeng.roses.kernel.pay.alipay.service.AlipayService;
import cn.stylefeng.roses.kernel.pay.api.PayApi;
import cn.stylefeng.roses.kernel.pay.api.constants.PayConstants;
import cn.stylefeng.roses.kernel.pay.api.pojo.TradeRefundResponse;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.payment.common.models.AlipayTradeRefundResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 阿里支付接口实现
 *
 * @author huziyang
 * @date 2021/04/20 20:43
 */
@Service
@Slf4j
public class AlipayServiceImpl implements AlipayService, PayApi {


    @Override
    public String page(String orderName, String outTradeNo, String total, String returnUrl) {
        try {
            return Factory.Payment.Page().pay(orderName, outTradeNo, total, returnUrl).body;
        } catch (Exception e) {
            log.error(e.getMessage());
            return e.getMessage();
        }
    }

    @Override
    public String wap(String orderName, String outTradeNo, String total, String quitUrl, String returnUrl) {
        try {
           return Factory.Payment.Wap().pay(orderName, outTradeNo, total, quitUrl, returnUrl).body;
        } catch (Exception e) {
            log.error(e.getMessage());
            return e.getMessage();
        }
    }

    @Override
    public TradeRefundResponse refund(String outTradeNo, String refundAmount) {
        try {
            AlipayTradeRefundResponse response = Factory.Payment.Common().refund(outTradeNo, refundAmount);
            if (AlipayConstants.ALIPAY_REFUND_SUCCESS_CODE.equals(response.getCode())){
                return TradeRefundResponse.builder()
                        .code(PayConstants.REFUND_SUCCESS_CODE)
                        .msg(response.getMsg())
                        .outTradeNo(response.getOutTradeNo())
                        .refundFee(response.getRefundFee())
                        .tradeNo(response.getTradeNo())
                        .gmtRefundPay(response.getGmtRefundPay())
                        .buyerLogonId(response.buyerLogonId)
                        .buyerUserId(response.buyerUserId)
                        .data(response)
                        .build();
            }
            return TradeRefundResponse.error(response.msg,response);
        } catch (Exception e) {
            log.error(e.getMessage());
            return TradeRefundResponse.error(e.getMessage());
        }
    }
}
