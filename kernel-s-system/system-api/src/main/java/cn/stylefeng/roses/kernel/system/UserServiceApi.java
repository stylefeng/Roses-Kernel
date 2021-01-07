package cn.stylefeng.roses.kernel.system;

import cn.stylefeng.roses.kernel.system.pojo.user.SysUserDTO;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.LoginUser;
import cn.stylefeng.roses.kernel.system.pojo.user.UserLoginInfoDTO;
import cn.stylefeng.roses.kernel.system.pojo.user.request.SysUserRequest;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 用户管理服务接口
 *
 * @author fengshuonan
 * @date 2020/10/20 13:20
 */
public interface UserServiceApi {

    /**
     * 获取用户登录需要的详细信息
     *
     * @param account 账号
     * @return 用户登录需要的详细信息
     * @author fengshuonan
     * @date 2020/10/20 16:59
     */
    UserLoginInfoDTO getUserLoginInfo(String account);

    /**
     * 更新用户的登录信息，一般为更新用户的登录时间和ip
     *
     * @param userId 用户id
     * @param date   用户登录时间
     * @param ip     用户登录的ip
     * @author fengshuonan
     * @date 2020/10/21 14:13
     */
    void updateUserLoginInfo(Long userId, Date date, String ip);

    /**
     * 根据机构id集合删除对应的用户-数据范围关联信息
     *
     * @param organizationIds 组织架构id集合
     * @author fengshuonan
     * @date 2020/10/20 16:59
     */
    void deleteUserDataScopeListByOrgIdList(Set<Long> organizationIds);

    /**
     * 获取用户的角色id集合
     *
     * @param userId 用户id
     * @return 角色id集合
     * @author majianguo
     * @date 2020/11/5 下午3:57
     */
    List<Long> getUserRoleIdList(Long userId);

    /**
     * 根据角色id删除对应的用户-角色表关联信息
     *
     * @param roleId 角色id
     * @author majianguo
     * @date 2020/11/5 下午3:58
     */
    void deleteUserRoleListByRoleId(Long roleId);

    /**
     * 获取用户单独绑定的数据范围，sys_user_data_scope表中的数据范围
     *
     * @param userId 用户id
     * @return 用户数据范围
     * @author fengshuonan
     * @date 2020/11/21 12:15
     */
    List<Long> getUserBindDataScope(Long userId);

    /**
     * 获取在线用户列表
     * @return
     */
    List<LoginUser> onlineUserList();

    /**
     * 查询全部用户ID(剔除管理员，和不允许登录)
     *
     * @param sysUserRequest 查询参数
     * @return List<Long> 用户id 集合
     * @author liuhanqing
     * @date 2021/1/4 22:09
     */
    List<Long> queryAllUserIdList(SysUserRequest sysUserRequest);



    /**
     * 获取用户信息
     *
     * @param userId 用户id
     * @return 用户信息
     * @author liuhanqing
     * @date 2021/1/4 22:09
     */
    SysUserDTO getUserInfo(Long userId);


    /**
     * 根据用户id 判断用户是否存在
     *
     * @param userId 用户id
     * @return 用户信息
     * @author liuhanqing
     * @date 2021/1/4 22:55
     */
    Boolean userExist(Long userId);

}
