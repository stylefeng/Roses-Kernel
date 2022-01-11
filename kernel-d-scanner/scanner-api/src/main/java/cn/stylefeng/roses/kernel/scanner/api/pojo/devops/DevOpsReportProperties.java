package cn.stylefeng.roses.kernel.scanner.api.pojo.devops;

import lombok.Data;

/**
 * 资源向DevOps平台汇总，需要进行的配置
 *
 * @author fengshuonan
 * @date 2022/1/11 14:29
 */
@Data
public class DevOpsReportProperties {

    /**
     * 当前项目在DevOps平台的唯一标识，由DevOps平台颁发
     */
    private String projectUniqueCode;

    /**
     * 当前项目和DevOps平台的交互秘钥（jwt秘钥）
     */
    private String projectInteractionKey;

    /**
     * DevOps平台的IP
     */
    private String devOpsServerHost;

    /**
     * DevOps平台的端口
     */
    private String devOpsServerPort;

}
