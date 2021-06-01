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
package cn.stylefeng.roses.kernel.pay.api.pojo;

import cn.stylefeng.roses.kernel.pay.api.constants.PayConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 退款响应
 *
 * @author huziyang
 * @date 2021/04/20 20:43
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TradeRefundResponse {

    /**
     * 退款状态码
     */
    private String code;

    /**
     * 状态描述
     */
    private String msg;

    /**
     * 商家订单号
     */
    private String outTradeNo;

    /**
     * 退款金额
     */
    private String refundFee;

    /**
     * 各厂商系统中的交易流水号
     */
    private String tradeNo;

    /**
     * 退款实际的发生时间
     */
    private String gmtRefundPay;

    /**
     * 买家账号
     */
    private String buyerLogonId;

    /**
     * 买家在各厂商系统中的用户id
     */
    private String buyerUserId;

    /**
     * 各厂商响应值
     */
    private Object data;

    /**
     * 初始化一个新创建的 TradeRefundResponse对象
     *
     * @param code 状态码
     * @param msg  描述
     * @param data 对象值
     * @author huziyang
     * @date 2021/04/20 20:43
     */
    public TradeRefundResponse(String code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 返回错误信息
     *
     * @param msg  描述
     * @param data 对象值
     * @return TradeRefundResponse对象
     * @author huziyang
     * @date 2021/04/20 20:43
     */
    public static TradeRefundResponse error(String msg, Object data) {
        return new TradeRefundResponse(PayConstants.REFUND_ERROR_CODE, msg, data);
    }

    /**
     * 返回错误信息
     *
     * @param msg 描述
     * @return TradeRefundResponse对象
     * @author huziyang
     * @date 2021/04/20 20:43
     */
    public static TradeRefundResponse error(String msg) {
        return error(msg, null);
    }

}
