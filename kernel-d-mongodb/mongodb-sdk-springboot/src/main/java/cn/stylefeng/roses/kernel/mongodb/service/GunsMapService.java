package cn.stylefeng.roses.kernel.mongodb.service;

import cn.stylefeng.roses.kernel.mongodb.entity.GunsMapEntity;

import java.util.List;
import java.util.Optional;

/**
 * @author huziyang
 * @create 2021-03-20 16:24
 */
public interface GunsMapService {

    /**
     * 新增操作
     * @param gunsMapEntity
     * @return
     */
    GunsMapEntity insert(GunsMapEntity gunsMapEntity);

    /**
     * 修改
     * @param gunsMapEntity
     * @return
     */
    GunsMapEntity update(GunsMapEntity gunsMapEntity);

    /**
     * 根据id删除
     * @param id
     */
    void deleteById(String id);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    Optional<GunsMapEntity> findById(String id);

    /**
     * 查询所有
     * @return
     */
    List<GunsMapEntity> findAll();

}