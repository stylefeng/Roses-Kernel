package cn.stylefeng.roses.kernel.system.modular.resource.service;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.system.api.pojo.resource.ApiGroupRequest;
import cn.stylefeng.roses.kernel.system.api.pojo.resource.ApiGroupTreeWrapper;
import cn.stylefeng.roses.kernel.system.modular.resource.entity.ApiGroup;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 接口分组 服务类
 *
 * @author majianguo
 * @date 2021/05/21 15:03
 */
public interface ApiGroupService extends IService<ApiGroup> {

    /**
     * 新增
     *
     * @param apiGroupRequest
     * @author majianguo
     * @date 2021/05/21 15:03
     */
    ApiGroup add(ApiGroupRequest apiGroupRequest);

    /**
     * 删除
     *
     * @param apiGroupRequest
     * @author majianguo
     * @date 2021/05/21 15:03
     */
    void del(ApiGroupRequest apiGroupRequest);

    /**
     * 编辑
     *
     * @param apiGroupRequest
     * @author majianguo
     * @date 2021/05/21 15:03
     */
    void edit(ApiGroupRequest apiGroupRequest);

    /**
     * 查询详情
     *
     * @param apiGroupRequest
     * @author majianguo
     * @date 2021/05/21 15:03
     */
    ApiGroup detail(ApiGroupRequest apiGroupRequest);

    /**
     * 获取列表
     *
     * @param apiGroupRequest
     * @return List<ApiGroup>
     * @author majianguo
     * @date 2021/05/21 15:03
     */
    List<ApiGroup> findList(ApiGroupRequest apiGroupRequest);

    /**
     * 获取列表（带分页）
     *
     * @param apiGroupRequest
     * @return PageResult<ApiGroup>
     * @author majianguo
     * @date 2021/05/21 15:03
     */
    PageResult<ApiGroup> findPage(ApiGroupRequest apiGroupRequest);

    /**
     * 获取树
     *
     * @return {@link List< ApiGroupTreeWrapper>}
     * @author majianguo
     * @date 2021/5/22 上午11:11
     **/
    List<ApiGroupTreeWrapper> tree(ApiGroupRequest apiGroupRequest);

    /**
     * 获取树
     *
     * @return {@link List< ApiGroupTreeWrapper>}
     * @author majianguo
     * @date 2021/5/22 上午11:11
     **/
    List<ApiGroupTreeWrapper> peersTree(ApiGroupRequest apiGroupRequest);

    /**
     * 获取分组树
     *
     * @return {@link List< ApiGroupTreeWrapper>}
     * @author majianguo
     * @date 2021/5/27 下午2:49
     **/
    List<ApiGroupTreeWrapper> groupTree(ApiGroupRequest apiGroupRequest);
}