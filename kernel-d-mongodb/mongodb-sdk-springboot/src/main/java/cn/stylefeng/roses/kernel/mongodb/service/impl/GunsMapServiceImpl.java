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
 * Mongodb 数据存储实现类
 *
 * @author huziyang
 * @date 2021/03/20 16:24
 */
@Service
public class GunsMapServiceImpl implements GunsMapService, MongodbApi<GunsMapEntity, String> {

    @Resource
    private GunsMapRepository gunsMapRepository;

    @Override
    public GunsMapEntity insert(GunsMapEntity gunsMapEntity) {
        return gunsMapRepository.insert(gunsMapEntity);
    }

    @Override
    public GunsMapEntity update(GunsMapEntity gunsMapEntity) {
        return gunsMapRepository.save(gunsMapEntity);
    }

    @Override
    public void deleteById(String id) {
        gunsMapRepository.deleteById(id);
    }

    @Override
    public Optional<GunsMapEntity> findById(String id) {
        return gunsMapRepository.findById(id);
    }

    @Override
    public List<GunsMapEntity> findAll() {
        return gunsMapRepository.findAll();
    }

}
