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
package cn.stylefeng.roses.kernel.file.modular.service;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.file.api.pojo.request.SysFileInfoRequest;
import cn.stylefeng.roses.kernel.file.api.pojo.response.SysFileInfoListResponse;
import cn.stylefeng.roses.kernel.file.api.pojo.response.SysFileInfoResponse;
import cn.stylefeng.roses.kernel.file.modular.entity.SysFileInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 文件信息表 服务类
 *
 * @author stylefeng
 * @date 2020/6/7 22:15
 */
public interface SysFileInfoService extends IService<SysFileInfo> {

    /**
     * 获取文件信息结果集
     *
     * @param fileId 文件id
     * @return 文件信息结果集
     * @author fengshuonan
     * @date 2020/11/29 14:16
     */
    SysFileInfoResponse getFileInfoResult(Long fileId);

    /**
     * 上传文件，返回文件的唯一标识
     *
     * @param file 要上传的文件
     * @return 文件上传信息体
     * @author majianguo
     * @date 2020/12/16 15:47
     */
    SysFileInfoResponse uploadFile(MultipartFile file, SysFileInfoRequest sysFileInfoRequest);

    /**
     * 更新文件，返回文件的唯一标识
     *
     * @param file               要上传的文件
     * @param sysFileInfoRequest 如果是替换，需带上code以便版本升级
     * @return 文件上传返回体
     * @author majianguo
     * @date 2020/12/16 15:46
     */
    SysFileInfoResponse updateFile(MultipartFile file, SysFileInfoRequest sysFileInfoRequest);

    /**
     * 文件下载
     *
     * @param sysFileInfoRequest 文件下载参数
     * @param response           响应结果
     * @author fengshuonan
     * @date 2020/11/29 13:39
     */
    void download(SysFileInfoRequest sysFileInfoRequest, HttpServletResponse response);

    /**
     * 删除文件信息（真删除文件信息）
     *
     * @param sysFileInfoRequest 删除参数
     * @author fengshuonan
     * @date 2020/11/29 13:44
     */
    void deleteReally(SysFileInfoRequest sysFileInfoRequest);

    /**
     * 分页查询文件信息表
     *
     * @param sysFileInfoRequest 查询参数
     * @return 查询分页结果
     * @author fengshuonan
     * @date 2020/11/29 14:09
     */
    PageResult<SysFileInfoListResponse> fileInfoListPage(SysFileInfoRequest sysFileInfoRequest);

    /**
     * 打包下载文件
     *
     * @param fileIds    文件ID集合，使用,号分割
     * @param secretFlag 是否私有文件
     * @param response   响应结果
     * @author majianguo
     * @date 2020/12/7 下午4:47
     */
    void packagingDownload(String fileIds, String secretFlag, HttpServletResponse response);

    /**
     * 根据附件IDS查询附件信息
     *
     * @param fileIds 附件IDS
     * @author majianguo
     * @date 2020/12/27 12:52
     */
    List<SysFileInfoResponse> getFileInfoListByFileIds(String fileIds);

    /**
     * 文件预览
     *
     * @author fengshuonan
     * @date 2020/11/29 11:29
     */
    void preview(SysFileInfoRequest sysFileInfoRequest, HttpServletResponse response);

    /**
     * 版本回退
     *
     * @author majianguo
     * @date 2020/12/27 13:49
     */
    SysFileInfoResponse versionBack(SysFileInfoRequest sysFileInfoRequest);

    /**
     * 文件预览，通过参数中传递fileBucket和fileObjectName
     *
     * @param sysFileInfoRequest 文件预览参数
     * @param response           响应结果
     * @author fengshuonan
     * @date 2020/11/29 13:45
     */
    void previewByBucketAndObjName(SysFileInfoRequest sysFileInfoRequest, HttpServletResponse response);

    /**
     * 查看详情文件信息表
     *
     * @param sysFileInfoRequest 查看参数
     * @return 文件信息
     * @author fengshuonan
     * @date 2020/11/29 14:08
     */
    SysFileInfo detail(SysFileInfoRequest sysFileInfoRequest);

    /**
     * 根据附件IDS查询附件信息
     *
     * @param fileIdList 文件ID列表
     * @author majianguo
     * @date 2020/12/27 12:57
     */
    List<SysFileInfoResponse> getFileInfoListByFileIds(List<Long> fileIdList);

}
