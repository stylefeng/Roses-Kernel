package cn.stylefeng.roses.kernel.mongodb.api;



import java.util.List;
import java.util.Optional;

/**
 * @author huziyang
 * @create 2021-03-20 16:24
 */
public interface MongodbApi<T,ID> {

    /**
     * 新增操作
     * @param gunsMapEntity
     * @return
     */
    T insert(T gunsMapEntity);

    /**
     * 修改
     * @param gunsMapEntity
     * @return
     */
    T update(T gunsMapEntity);

    /**
     * 根据id删除
     * @param id
     */
    void deleteById(ID id);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    Optional<T> findById(ID id);

    /**
     * 查询所有
     * @return
     */
    List<T> findAll();
}
