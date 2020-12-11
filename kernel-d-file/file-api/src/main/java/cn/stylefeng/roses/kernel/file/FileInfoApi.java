package cn.stylefeng.roses.kernel.file;

import cn.stylefeng.roses.kernel.file.pojo.response.SysFileInfoResponse;

/**
 * 获取文件信息的api
 *
 * @author fengshuonan
 * @date 2020/11/29 16:21
 */
public interface FileInfoApi {

    /**
     * 获取文件详情
     *
     * @param fileId 文件id，在文件信息表的id
     * @return 文件的信息，不包含文件本身的字节信息
     * @author fengshuonan
     * @date 2020/11/29 16:26
     */
    SysFileInfoResponse getFileInfoWithoutContent(Long fileId);

}
