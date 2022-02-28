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
package cn.stylefeng.roses.kernel.mongodb.integration.controller;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.mongodb.api.MongoFileApi;
import cn.stylefeng.roses.kernel.mongodb.file.entity.MongoFileEntity;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.PostResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Optional;

/**
 * Mongodb文件管理接口控制器
 *
 * @author huziyang
 * @date 2021/03/31 17:28
 */
@RestController
@ApiResource(name = "Mongodb文件接口控制器")
public class MongoFileController {

    @Resource
    private MongoFileApi<MongoFileEntity, String> mongoFileApi;

    /**
     * 新增文件
     *
     * @author huziyang
     * @date 2021/03/31 17:28
     */
    @PostResource(name = "Mongodb文件新增", path = "/view/mongodb/file/add")
    public ResponseData<?> mongodbFileAdd(@RequestPart("file") MultipartFile file) {
        return new SuccessResponseData<>(mongoFileApi.saveFile(file));
    }

    /**
     * 根据id删除文件
     *
     * @author huziyang
     * @date 2021/03/31 17:28
     */
    @PostResource(name = "Mongodb文件删除", path = "/view/mongodb/file/del")
    public ResponseData<?> mongodbFileDel(@RequestParam String id) {
        mongoFileApi.removeFile(id);
        return new SuccessResponseData<>();
    }

    /**
     * 获取分页文件列表
     *
     * @author huziyang
     * @date 2021/03/31 17:28
     */
    @GetResource(name = "Mongodb文件列表", path = "/view/mongodb/file/list")
    public ResponseData<PageResult<?>> mongodbFileList(MongoFileEntity mongoFileEntity) {
        return new SuccessResponseData<>(mongoFileApi.getFilesByPage(mongoFileEntity));
    }

    /**
     * 根据id下载文件
     *
     * @author huziyang
     * @date 2021/03/31 17:28
     */
    @GetResource(name = "Mongodb文件下载", path = "/view/mongodb/file/down")
    public ResponseEntity<?> mongodbFileDown(@RequestParam String id) throws UnsupportedEncodingException {
        Optional<MongoFileEntity> file = mongoFileApi.getFileById(id);
        if (file.isPresent()) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=" + URLEncoder.encode(file.get().getName(), "utf-8"))
                    .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
                    .body(file.get().getContent());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not found");
        }
    }

}
