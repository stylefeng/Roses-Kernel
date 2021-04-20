/*
 * Copyright [2020-2030] [https://www.stylefeng.cn]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Guns采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Guns源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/stylefeng/guns
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/stylefeng/guns
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */
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
