package cn.stylefeng.roses.kernel.mongodb.integration.controller;

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
 * @author huziyang
 * @create 2021-03-31 17:28
 */
@RestController
@ApiResource(name = "Mongodb文件接口控制器")
public class MongoFileController {

    @Resource
    private MongoFileApi<MongoFileEntity,String> mongoFileApi;

    @PostResource(name = "Mongodb文件新增", path = "/view/mongodb/file/add")
    public ResponseData mongodbFileAdd(@RequestPart("file")  MultipartFile file) {
        return new SuccessResponseData(mongoFileApi.saveFile(file));
    }

    @PostResource(name = "Mongodb文件删除", path = "/view/mongodb/file/del")
    public ResponseData mongodbFileDel(@RequestParam String id) {
        mongoFileApi.removeFile(id);
        return new SuccessResponseData();
    }

    @GetResource(name = "Mongodb文件列表", path = "/view/mongodb/file/list")
    public ResponseData mongodbFileList(MongoFileEntity mongoFileEntity) {
        return new SuccessResponseData(mongoFileApi.getFilesByPage(mongoFileEntity));
    }

    @GetResource(name = "Mongodb文件下载", path = "/view/mongodb/file/down")
    public ResponseEntity mongodbFileDown(@RequestParam String id) throws UnsupportedEncodingException {
        Optional<MongoFileEntity> file = mongoFileApi.getFileById(id);
        if(file.isPresent()){
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=" + URLEncoder.encode(file.get().getName() , "utf-8"))
                    .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
                    .body(file.get().getContent());
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not found");
        }
    }

}
