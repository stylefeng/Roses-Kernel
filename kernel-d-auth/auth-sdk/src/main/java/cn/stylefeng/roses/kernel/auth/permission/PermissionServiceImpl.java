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

import static cn.stylefeng.roses.kernel.auth.api.exception.enums.AuthExceptionEnum.PERMISSION_RES_VALIDATE_ERROR;
import static cn.stylefeng.roses.kernel.auth.api.exception.enums.AuthExceptionEnum.TOKEN_ERROR;

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

        // 1. 获取url对应的资源信息ResourceDefinition
        ResourceUrlParam resourceUrlReq = new ResourceUrlParam();
        resourceUrlReq.setUrl(requestUrl);
        ResourceDefinition resourceDefinition = resourceServiceApi.getResourceByUrl(resourceUrlReq);

        // 2. 如果此接口不需要权限校验或者查询到资源为空，则放开过滤
        if (resourceDefinition == null || !resourceDefinition.getRequiredPermission()) {
            return;
        }

        // 3. 获取token对应的用户信息
        LoginUser session = sessionManagerApi.getSession(token);
        if (session == null) {
            throw new AuthException(TOKEN_ERROR);
        }

        // 4. 如果需要权限认证，验证用户有没有当前url的权限
        if (resourceDefinition.getRequiredPermission()) {
            Set<String> resourceUrls = session.getResourceUrls();
            if (resourceUrls == null || resourceUrls.size() == 0) {
                throw new AuthException(PERMISSION_RES_VALIDATE_ERROR);
            } else {
                if (!resourceUrls.contains(requestUrl)) {
                    throw new AuthException(PERMISSION_RES_VALIDATE_ERROR);
                }
            }
        }
    }

}
