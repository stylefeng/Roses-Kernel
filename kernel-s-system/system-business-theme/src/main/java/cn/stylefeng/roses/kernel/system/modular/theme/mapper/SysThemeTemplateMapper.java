package cn.stylefeng.roses.kernel.system.modular.theme.mapper;

import cn.stylefeng.roses.kernel.system.api.pojo.theme.SysThemeTemplateDataDTO;
import cn.stylefeng.roses.kernel.system.modular.theme.entity.SysThemeTemplate;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统主题模板mapper接口
 *
 * @author xixiaowei
 * @date 2021/12/17 10:11
 */
public interface SysThemeTemplateMapper extends BaseMapper<SysThemeTemplate> {

    /**
     * 系统主题模板详细查询
     *
     * @author xixiaowei
     * @date 2021/12/17 15:36
     */
    List<SysThemeTemplateDataDTO> sysThemeTemplateDetail(@Param("id") Long id);
}
