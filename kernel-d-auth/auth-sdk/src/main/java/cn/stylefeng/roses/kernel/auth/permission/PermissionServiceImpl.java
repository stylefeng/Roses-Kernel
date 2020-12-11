package cn.stylefeng.roses.kernel.auth.permission;

import cn.stylefeng.roses.kernel.auth.api.PermissionServiceApi;
import cn.stylefeng.roses.kernel.auth.api.SessionManagerApi;
import cn.stylefeng.roses.kernel.auth.api.exception.AuthException;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.LoginUser;
import cn.stylefeng.roses.kernel.resource.api.pojo.resource.ResourceDefinition;
import cn.stylefeng.roses.kernel.resource.api.pojo.resource.ResourceUrlParam;
import cn.stylefeng.roses.kernel.system.ResourceServiceApi;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

import static cn.stylefeng.roses.kernel.auth.api.exception.enums.AuthExceptionEnum.*;

/**
 * 权限相关的service
 *
 * @author fengshuonan
 * @date 2020/10/22 15:49
 */
@Service
public class PermissionServiceImpl implements PermissionServiceApi {

    @Resource
    private ResourceServiceApi resourceServiceApi;

    @Resource
    private SessionManagerApi sessionManagerApi;

    @Override
    public void checkPermission(String token, String requestUrl) {

        // 获取token对应的用户信息
        LoginUser session = sessionManagerApi.getSession(token);
        if (session == null) {
            throw new AuthException(TOKEN_ERROR);
        }

        // 获取url对应的资源信息ResourceDefinition
        ResourceUrlParam resourceUrlReq = new ResourceUrlParam();
        resourceUrlReq.setUrl(requestUrl);
        ResourceDefinition resourceDefinition = resourceServiceApi.getResourceByUrl(resourceUrlReq);

        // 资源为空，直接响应异常，禁止用户访问
        if (resourceDefinition == null) {
            throw new AuthException(RESOURCE_DEFINITION_ERROR);
        }

        // 检查接口是否需要权限验证
        Boolean requiredPermission = resourceDefinition.getRequiredPermission();

        // 需要权限认证，验证用户有没有当前url的权限
        if (requiredPermission) {
            Set<String> resourceUrls = session.getResourceUrls();
            if (resourceUrls == null || resourceUrls.size() == 0) {
                throw new AuthException(PERMISSION_RES_VALIDATE_ERROR);
            }
        }
    }

}
