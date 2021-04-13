package cn.stylefeng.roses.kernel.mongodb.file.service;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.mongodb.file.entity.MongoFileEntity;
import org.springframework.web.multipart.MultipartFile;
import java.util.Optional;

/**
 * @author huziyang
 * @create 2021-03-26 17:28
 */
public interface MongoFileService {

    /**
     * 保存文件
     * @param file
     * @return
     */
    MongoFileEntity saveFile(MultipartFile file);


    /**
     * 删除文件
     * @param id
     */
    void removeFile(String id);

    /**
     * 根据id获取文件
     * @param id
     * @return
     */
    Optional<MongoFileEntity> getFileById(String id);


    /**
     * 分页获取文件
     * @param fileDocument 查询条件
     * @return
     */
    PageResult<MongoFileEntity> getFilesByPage(MongoFileEntity fileDocument);


}
