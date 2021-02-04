package cn.stylefeng.roses.kernel.system;

import java.util.Set;

/**
 * 系统角色数据范围service接口实现类
 *
 * @author chenjinlong
 * @date 2021/2/4 16:01
 */
public interface RoleDataScopeServiceApi {

    /**
     * 根据机构id集合删除
     *
     * @param orgIds 结构id集合
     * @return
     * @author chenjinlong
     * @date 2021/2/4 15:56
     */
    void delByOrgIds(Set<Long> orgIds);
}
