package cn.stylefeng.roses.kernel.log.api.pojo.log;

import lombok.Data;

/**
 * 日志配置信息
 *
 * @author liuhanqing
 * @date 2020-12-20 13:53
 **/
@Data
public class SysLogProperties {

    /**
     * 日志存储类型：db-数据库，file-文件，默认存储在数据库中
     */
    private String type = "db";

    /**
     * file存储类型日志文件的存储位置
     */
    private String fileSavePath = "_sys_log";
    
}
