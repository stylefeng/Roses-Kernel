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
