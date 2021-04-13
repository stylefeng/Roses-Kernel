package cn.stylefeng.roses.kernel.mongodb.service.impl;

import cn.stylefeng.roses.kernel.mongodb.api.MongodbApi;
import cn.stylefeng.roses.kernel.mongodb.entity.GunsMapEntity;
import cn.stylefeng.roses.kernel.mongodb.mapper.GunsMapRepository;
import cn.stylefeng.roses.kernel.mongodb.service.GunsMapService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @author huziyang
 * @create 2021-03-20 16:24
 */
@Service
public class GunsMapServiceImpl implements GunsMapService, MongodbApi<GunsMapEntity,String> {


    @Resource
    private GunsMapRepository gunsMapRepository;


    @Override
    public GunsMapEntity insert(GunsMapEntity gunsMapEntity){
        return gunsMapRepository.insert(gunsMapEntity);
    }

    @Override
    public GunsMapEntity update(GunsMapEntity gunsMapEntity){
        return gunsMapRepository.save(gunsMapEntity);
    }

    @Override
    public void deleteById(String id){
        gunsMapRepository.deleteById(id);
    }

    @Override
    public Optional<GunsMapEntity> findById(String id){
        return gunsMapRepository.findById(id);
    }

    @Override
    public List<GunsMapEntity> findAll(){
        return gunsMapRepository.findAll();
    }

}
