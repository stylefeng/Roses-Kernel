package cn.stylefeng.roses.kernel.timer.modular.wrapper;

import cn.hutool.extra.spring.SpringUtil;
import cn.stylefeng.roses.kernel.system.api.UserServiceApi;
import cn.stylefeng.roses.kernel.system.api.pojo.user.SysUserDTO;
import cn.stylefeng.roses.kernel.timer.modular.entity.SysTimers;
import cn.stylefeng.roses.kernel.wrapper.api.BaseWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * 定时任务查询结果包装
 *
 * @author fengshuonan
 * @date 2021/3/1 23:45
 */
public class TimerWrapper implements BaseWrapper<SysTimers> {

    @Override
    public Map<String, Object> doWrap(SysTimers beWrappedModel) {

        HashMap<String, Object> resultMap = new HashMap<>();
        UserServiceApi userServiceApi = SpringUtil.getBean(UserServiceApi.class);

        if (beWrappedModel.getCreateUser() != null) {
            SysUserDTO sysUserDTO = userServiceApi.getUserInfoByUserId(beWrappedModel.getCreateUser());
            if (sysUserDTO != null) {
                resultMap.put("createUserName", sysUserDTO.getRealName());
            }
        }

        if (beWrappedModel.getUpdateUser() != null) {
            SysUserDTO sysUserDTO = userServiceApi.getUserInfoByUserId(beWrappedModel.getUpdateUser());
            if (sysUserDTO != null) {
                resultMap.put("updateUserName", sysUserDTO.getRealName());
            }
        }

        return resultMap;
    }

}
