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
package cn.stylefeng.roses.kernel.file.api.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.URLUtil;
import cn.stylefeng.roses.kernel.file.api.exception.FileException;
import cn.stylefeng.roses.kernel.file.api.exception.enums.FileExceptionEnum;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * web文件下载工具封装
 *
 * @author fengshuonan
 * @date 2020/11/29 11:36
 */
@Slf4j
public class DownloadUtil {

    /**
     * 根据文件名和文件的字节数组下载文件
     *
     * @param fileName  文件真实名称,最终返回给用户的
     * @param fileBytes 文件的真实字节数组
     * @param response  servlet response对象
     * @author fengshuonan
     * @date 2020/11/29 13:52
     */
    public static void download(String fileName, byte[] fileBytes, HttpServletResponse response) {
        try {
            response.reset();
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + URLUtil.encode(fileName) + "\"");
            response.addHeader("Content-Length", "" + fileBytes.length);
            response.setContentType("application/octet-stream;charset=UTF-8");
            IoUtil.write(response.getOutputStream(), true, fileBytes);
        } catch (IOException e) {
            throw new FileException(FileExceptionEnum.DOWNLOAD_FILE_ERROR, e.getMessage());
        }
    }

    /**
     * 下载文件
     *
     * @param file     要下载的文件
     * @param response 响应
     * @author fengshuonan
     * @date 2020/11/29 13:53
     */
    public static void download(File file, HttpServletResponse response) {

        // 获取文件字节
        byte[] fileBytes = FileUtil.readBytes(file);

        //获取文件名称
        String fileName;
        try {
            fileName = URLEncoder.encode(file.getName(), CharsetUtil.UTF_8);
        } catch (UnsupportedEncodingException e) {
            throw new FileException(FileExceptionEnum.DOWNLOAD_FILE_ERROR, e.getMessage());
        }

        //下载文件
        download(fileName, fileBytes, response);
    }

    /**
     * 渲染被预览的文件到servlet的response流中
     *
     * @author fengshuonan
     * @date 2020/11/29 17:13
     */
    public static void renderPreviewFile(HttpServletResponse response, byte[] fileBytes) {
        try {
            // 设置contentType
            response.setContentType("image/png");

            // 获取outputStream
            ServletOutputStream outputStream = response.getOutputStream();

            // 输出字节流
            IoUtil.write(outputStream, true, fileBytes);
        } catch (IOException e) {
            throw new FileException(FileExceptionEnum.WRITE_BYTES_ERROR, e.getMessage());
        }
    }

}
