package cn.stylefeng.roses.kernel.file.modular.mapper;

import cn.stylefeng.roses.kernel.file.api.pojo.request.SysFileInfoRequest;
import cn.stylefeng.roses.kernel.file.api.pojo.response.SysFileInfoListResponse;
import cn.stylefeng.roses.kernel.file.api.pojo.response.SysFileInfoResponse;
import cn.stylefeng.roses.kernel.file.modular.entity.SysFileInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 文件信息表 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @date 2020/6/7 22:15
 */
public interface SysFileInfoMapper extends BaseMapper<SysFileInfo> {

    /**
     * 根据附件IDS查询附件信息
     *
     * @param fileIdList 文件ID列表
     * @author majianguo
     * @date 2020/12/27 12:57
     */
    List<SysFileInfoResponse> getFileInfoListByFileIds(@Param("fileIdList") List<Long> fileIdList);

    /**
     * 附件列表（有分页）
     *
     * @author majianguo
     * @date 2020/12/27 12:57
     */
    List<SysFileInfoListResponse> fileInfoList(@Param("page") Page<SysFileInfoListResponse> page, @Param("sysFileInfoRequest") SysFileInfoRequest sysFileInfoRequest);

    /**
     * 获取所有附件信息的code集合
     *
     * @param fileIdList 文件ID列表
     * @author majianguo
     * @date 2020/12/27 12:57
     */
    List<Long> getFileCodeByFileIds(@Param("fileIdList") List<Long> fileIdList);

    /**
     * 修改fielCodes下所有附件状态
     *
     * @param fileCodeList 文件CODE列表
     * @param delFlag      是否删除
     * @author majianguo
     * @date 2020/12/27 12:56
     */
    void updateDelFlagByFileCodes(@Param("fileCodeList") List<Long> fileCodeList, @Param("delFlag") String delFlag);

    /**
     * 修改fileIds下所有附件状态
     *
     * @param fileIdList 文件ID列表
     * @param delFlag    是否删除
     * @author majianguo
     * @date 2020/12/27 12:56
     */
    void updateDelFlagByFileIds(@Param("fileIdList") List<Long> fileIdList, @Param("delFlag") String delFlag);

}
