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

    /**
     * 获取文件的下载地址（带鉴权的），生成外网地址
     *
     * @param fileId 文件id
     * @return 外部系统可以直接访问的url
     * @author fengshuonan
     * @date 2020/10/26 10:40
     */
    String getFileAuthUrl(Long fileId);

    /**
     * 获取文件的下载地址（带鉴权的），生成外网地址
     *
     * @param fileId 文件id
     * @param token  用户的token
     * @return 外部系统可以直接访问的url
     * @author fengshuonan
     * @date 2020/10/26 10:40
     */
    String getFileAuthUrl(Long fileId, String token);

}
