package cn.stylefeng.roses.kernel.system.modular.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.stylefeng.roses.kernel.system.modular.user.entity.SysUser;
import cn.stylefeng.roses.kernel.system.pojo.user.request.SysUserRequest;
import cn.stylefeng.roses.kernel.system.modular.user.pojo.response.SysUserResponse;
import org.apache.ibatis.annotations.Param;

/**
 * 系统用户mapper接口
 *
 * @author luojie
 * @date 2020/11/6 14:50
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 查询用户列表
     *
     * @param page           分页参数
     * @param sysUserRequest 查询条件信息
     * @author fengshuonan
     * @date 2020/11/21 15:16
     */
    Page<SysUserResponse> findUserPage(@Param("page") Page<SysUser> page, @Param("sysUserRequest") SysUserRequest sysUserRequest);

}
