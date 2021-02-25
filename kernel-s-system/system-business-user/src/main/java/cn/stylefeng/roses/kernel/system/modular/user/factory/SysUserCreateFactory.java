package cn.stylefeng.roses.kernel.system.modular.user.factory;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.stylefeng.roses.kernel.auth.api.password.PasswordStoredEncryptApi;
import cn.stylefeng.roses.kernel.rule.enums.SexEnum;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import cn.stylefeng.roses.kernel.system.api.enums.UserStatusEnum;
import cn.stylefeng.roses.kernel.system.api.expander.SystemConfigExpander;
import cn.stylefeng.roses.kernel.system.modular.user.entity.SysUser;
import cn.stylefeng.roses.kernel.system.api.pojo.user.request.SysUserRequest;

/**
 * 用户信息填充，用于创建和修改用户时，添加一些基础信息
 *
 * @author fengshuonan
 * @date 2020/11/21 12:55
 */
public class SysUserCreateFactory {

    /**
     * 新增用户时候的用户信息填充
     *
     * @author fengshuonan
     * @date 2020/11/21 12:56
     */
    public static void fillAddSysUser(SysUser sysUser) {

        // 默认设置为非超级管理员
        sysUser.setSuperAdminFlag(YesOrNotEnum.N.getCode());

        // 添加用户时，设置为启用状态
        sysUser.setStatusFlag(UserStatusEnum.ENABLE.getCode());

        // 密码为空则设置为默认密码
        PasswordStoredEncryptApi passwordStoredEncryptApi = SpringUtil.getBean(PasswordStoredEncryptApi.class);
        if (ObjectUtil.isEmpty(sysUser.getPassword())) {
            String defaultPassword = SystemConfigExpander.getDefaultPassWord();
            sysUser.setPassword(passwordStoredEncryptApi.encrypt(defaultPassword));
        } else {
            // 密码不为空，则将密码加密存储到库中
            sysUser.setPassword(passwordStoredEncryptApi.encrypt(sysUser.getPassword()));
        }

        // 用户头像为空
        if (ObjectUtil.isEmpty(sysUser.getAvatar())) {
            sysUser.setAvatar(null);
        }

        // 用户性别为空，则默认设置成男
        if (ObjectUtil.isEmpty(sysUser.getSex())) {
            sysUser.setSex(SexEnum.M.getCode());
        }
    }

    /**
     * 编辑用户时候的用户信息填充
     *
     * @author fengshuonan
     * @date 2020/11/21 12:56
     */
    public static void fillEditSysUser(SysUser sysUser) {

        // 编辑用户不修改用户状态
        sysUser.setStatusFlag(null);

        // 不能修改原密码，通过重置密码或修改密码来修改
        sysUser.setPassword(null);

    }

    /**
     * 编辑用户时候的用户信息填充
     *
     * @author fengshuonan
     * @date 2020/11/21 12:56
     */
    public static void fillUpdateInfo(SysUserRequest sysUserRequest, SysUser sysUser) {

        // 性别（M-男，F-女）
        sysUser.setSex(sysUserRequest.getSex());

        // 邮箱
        sysUser.setEmail(sysUserRequest.getEmail());

        // 姓名
        sysUser.setRealName(sysUserRequest.getRealName());

        // 生日
        sysUser.setBirthday(DateUtil.parse(sysUserRequest.getBirthday()));

        // 手机
        sysUser.setPhone(sysUserRequest.getPhone());
    }

}
