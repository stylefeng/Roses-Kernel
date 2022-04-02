package cn.stylefeng.roses.kernel.scanner;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.stylefeng.roses.kernel.jwt.JwtTokenOperator;
import cn.stylefeng.roses.kernel.jwt.api.pojo.config.JwtConfig;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.scanner.api.DevOpsReportApi;
import cn.stylefeng.roses.kernel.scanner.api.exception.ScannerException;
import cn.stylefeng.roses.kernel.scanner.api.exception.enums.DevOpsExceptionEnum;
import cn.stylefeng.roses.kernel.scanner.api.pojo.devops.DevOpsReportProperties;
import cn.stylefeng.roses.kernel.scanner.api.pojo.devops.DevOpsReportResourceParam;
import cn.stylefeng.roses.kernel.scanner.api.pojo.resource.SysResourcePersistencePojo;
import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.List;

import static cn.stylefeng.roses.kernel.scanner.api.constants.ScannerConstants.*;

/**
 * 运维平台资源汇报过程
 *
 * @author fengshuonan
 * @date 2022/4/2 14:38
 */
public class DevOpsReportImpl implements DevOpsReportApi {

    @Override
    public void reportResources(DevOpsReportProperties devOpsReportProperties, List<SysResourcePersistencePojo> sysResourcePersistencePojoList) {

        // 去掉请求地址结尾的左斜杠
        String serverHost = devOpsReportProperties.getServerHost();
        if (StrUtil.endWith(serverHost, "/")) {
            serverHost = StrUtil.removeSuffix(serverHost, "/");
        }

        // 组装请求DevOps平台的地址
        String devopsReportUrl = serverHost + DEVOPS_REQUEST_PATH;

        // jwt token生成
        String projectInteractionSecretKey = devOpsReportProperties.getProjectInteractionSecretKey();
        Long tokenValidityPeriodSeconds = devOpsReportProperties.getTokenValidityPeriodSeconds();
        JwtConfig jwtConfig = new JwtConfig();
        jwtConfig.setJwtSecret(projectInteractionSecretKey);
        jwtConfig.setExpiredSeconds(ObjectUtil.isNotEmpty(tokenValidityPeriodSeconds) ? tokenValidityPeriodSeconds : DEVOPS_REPORT_TIMEOUT_SECONDS);
        JwtTokenOperator jwtTokenOperator = new JwtTokenOperator(jwtConfig);
        String jwtToken = jwtTokenOperator.generateToken(new HashMap<>());

        // 组装请求参数
        DevOpsReportResourceParam devOpsReportResourceParam = new DevOpsReportResourceParam(
                devOpsReportProperties.getProjectUniqueCode(), jwtToken, sysResourcePersistencePojoList, devOpsReportProperties.getFieldMetadataClassPath());

        // 进行post请求，汇报资源
        HttpRequest httpRequest = HttpUtil.createPost(devopsReportUrl);
        httpRequest.body(JSON.toJSONString(devOpsReportResourceParam));
        httpRequest.setConnectionTimeout(Convert.toInt(DEVOPS_REPORT_CONNECTION_TIMEOUT_SECONDS * 1000));
        ResponseData<?> responseData = null;
        HttpResponse execute = httpRequest.execute();
        String body = execute.body();
        responseData = JSON.parseObject(body, ResponseData.class);
        // 返回结果为空
        if (responseData == null) {
            throw new ScannerException(DevOpsExceptionEnum.HTTP_RESPONSE_EMPTY);
        }
        // 返回失败
        if (!responseData.getSuccess()) {
            throw new ScannerException(DevOpsExceptionEnum.HTTP_RESPONSE_ERROR, responseData.getMessage());
        }
    }

}
