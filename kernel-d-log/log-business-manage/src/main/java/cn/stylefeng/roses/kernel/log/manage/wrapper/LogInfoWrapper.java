package cn.stylefeng.roses.kernel.log.manage.wrapper;

import cn.hutool.extra.spring.SpringUtil;
import cn.stylefeng.roses.kernel.log.api.pojo.record.LogRecordDTO;
import cn.stylefeng.roses.kernel.system.api.UserServiceApi;
import cn.stylefeng.roses.kernel.system.api.pojo.user.SysUserDTO;
import cn.stylefeng.roses.kernel.wrapper.api.BaseWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * 日志结果进行包装
 *
 * @author fengshuonan
 * @date 2021/2/28 10:59
 */
public class LogInfoWrapper implements BaseWrapper<LogRecordDTO> {

    @Override
    public Map<String, Object> doWrap(LogRecordDTO beWrappedModel) {

        if (beWrappedModel.getUserId() == null) {
            return new HashMap<>();
        }
        UserServiceApi userServiceApi = SpringUtil.getBean(UserServiceApi.class);
        SysUserDTO sysUserDTO = userServiceApi.getUserInfoByUserId(beWrappedModel.getUserId());

        HashMap<String, Object> map = new HashMap<>();
        map.put("realName", sysUserDTO.getRealName());
        return map;
    }

}
