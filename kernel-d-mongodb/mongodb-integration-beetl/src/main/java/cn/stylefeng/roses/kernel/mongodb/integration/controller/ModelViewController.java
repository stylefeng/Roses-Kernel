package cn.stylefeng.roses.kernel.mongodb.integration.controller;

import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.GetResource;
import org.springframework.stereotype.Controller;

/**
 * @author huziyang
 * @create 2021-03-30 15:21
 */
@Controller
@ApiResource(name = "MongoDB文件管理界面渲染控制器")
public class ModelViewController {


    @GetResource(name = "Mongodb文件列表视图", path = "/view/mongodb/file")
    public String mongodbFile() {
        return "/modular/mongodb/fileList.html";
    }

}
