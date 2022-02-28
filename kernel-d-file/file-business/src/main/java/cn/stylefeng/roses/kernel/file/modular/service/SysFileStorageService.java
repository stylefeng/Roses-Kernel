package cn.stylefeng.roses.kernel.file.modular.service;

import cn.stylefeng.roses.kernel.file.modular.entity.SysFileStorage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 文件存储信息 服务类
 *
 * @author fengshuonan
 * @date 2022/01/08 15:53
 */
public interface SysFileStorageService extends IService<SysFileStorage> {

    /**
     * 将文件存储在库中
     *
     * @author fengshuonan
     * @date 2022/1/8 16:08
     */
    void saveFile(Long fileId, byte[] bytes);

    /**
     * 获取文件的访问url
     *
     * @param fileId 文件id
     * @author fengshuonan
     * @date 2022/1/8 16:12
     */
    String getFileAuthUrl(String fileId);

    /**
     * 获取文件不带鉴权的访问url
     *
     * @param fileId 文件id
     * @author fengshuonan
     * @date 2022/1/8 16:12
     */
    String getFileUnAuthUrl(String fileId);

}