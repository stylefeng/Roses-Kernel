package cn.stylefeng.roses.kernel.mongodb.api;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import org.springframework.web.multipart.MultipartFile;
import java.util.Optional;

/**
 * @author huziyang
 * @create 2021-03-30 11:06
 */
public interface MongoFileApi<T,ID> {

    /**
     * 保存文件
     * @param file
     * @return
     */
    T saveFile(MultipartFile file);


    /**
     * 删除文件
     * @param id
     */
    void removeFile(ID id);

    /**
     * 根据id获取文件
     * @param id
     * @return
     */
    Optional<T> getFileById(ID id);


    /**
     * 分页获取文件
     * @param fileDocument 查询条件
     * @return
     */
    PageResult<T> getFilesByPage(T fileDocument);


}
