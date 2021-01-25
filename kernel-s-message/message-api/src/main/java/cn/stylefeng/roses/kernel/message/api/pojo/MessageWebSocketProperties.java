package cn.stylefeng.roses.kernel.message.api.pojo;

import lombok.Data;

/**
 * 日志配置信息
 *
 * @author liuhanqing
 * @date 2021/1/25 9:25
 */
@Data
public class MessageWebSocketProperties {

    /**
     * websocket开关
     */
    private Boolean open = true ;

    /**
     * websocket地址
     */
    private String wsUrl = "";

}
