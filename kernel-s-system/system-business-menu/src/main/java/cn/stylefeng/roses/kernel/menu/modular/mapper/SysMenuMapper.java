package cn.stylefeng.roses.kernel.menu.modular.mapper;

import cn.stylefeng.roses.kernel.menu.modular.entity.SysMenu;
import cn.stylefeng.roses.kernel.system.pojo.menu.response.SysMenuResponse;
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
     * 获取系统所有菜单
     *
     * @author majianguo
     * @date 2021/1/7 15:27
     */
    List<SysMenuResponse> getSystemAllMenus();
}
