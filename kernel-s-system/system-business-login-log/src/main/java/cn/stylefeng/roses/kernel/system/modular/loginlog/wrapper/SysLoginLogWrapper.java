package cn.stylefeng.roses.kernel.system.modular.loginlog.wrapper;

import cn.hutool.extra.spring.SpringUtil;
import cn.stylefeng.roses.kernel.system.api.UserServiceApi;
import cn.stylefeng.roses.kernel.system.api.pojo.user.SysUserDTO;
import cn.stylefeng.roses.kernel.system.modular.loginlog.entity.SysLoginLog;
import cn.stylefeng.roses.kernel.wrapper.api.BaseWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * 日志结果进行包装
 *
 * @author fengshuonan
 * @date 2021/2/28 10:59
 */
public class SysLoginLogWrapper implements BaseWrapper<SysLoginLog> {

    @Override
    public Map<String, Object> doWrap(SysLoginLog sysLoginLog) {

        if (sysLoginLog.getUserId() == null) {
            return new HashMap<>();
        }
        UserServiceApi userServiceApi = SpringUtil.getBean(UserServiceApi.class);
        SysUserDTO sysUserDTO = userServiceApi.getUserInfoByUserId(sysLoginLog.getUserId());

        HashMap<String, Object> map = new HashMap<>();
        map.put("realName", sysUserDTO.getRealName());
        return map;
    }

}
