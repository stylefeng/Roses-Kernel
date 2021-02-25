package cn.stylefeng.roses.kernel.system.modular.menu.mapper;

import cn.stylefeng.roses.kernel.system.modular.menu.entity.SysMenu;
import cn.stylefeng.roses.kernel.system.api.pojo.menu.antd.AntdSysMenuDTO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * 系统菜单mapper接口
 *
 * @author fengshuonan
 * @date 2020/3/13 16:05
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 获取系统所有菜单（适用于登录后获取左侧菜单）（适配antd vue版本）
     * <p>
     * 返回信息携带：菜单可以被那些角色访问
     *
     * @return 系统所有菜单
     * @author majianguo
     * @date 2021/1/7 15:27
     */
    List<AntdSysMenuDTO> getSystemAllMenus();

}
