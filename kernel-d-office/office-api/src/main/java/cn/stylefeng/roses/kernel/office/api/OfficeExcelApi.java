package cn.stylefeng.roses.kernel.office.api;

import cn.stylefeng.roses.kernel.office.api.pojo.report.ExcelExportParam;

import java.io.InputStream;
import java.util.List;

/**
 * Excel 常用操作接口
 *
 * @author luojie
 * @date 2020/11/3 16:42
 */
public interface OfficeExcelApi {

    /**
     * 简单的导出Excel下载
     *
     * @param excelExportParam Excel导出参数
     * @author luojie
     * @date 2020/11/4 10:11
     */
    void easyExportDownload(ExcelExportParam excelExportParam);

    /**
     * 简单的写入Excel文件到指定路径
     *
     * @param excelExportParam Excel导出参数
     * @author luojie
     * @date 2020/11/4 11:31
     */
    void easyWriteToFile(ExcelExportParam excelExportParam);

    /**
     * 简单的读取Excel文件并返回实体类List集合
     *
     * @param inputStream 流输入Excel文件的流对象
     * @param clazz       每行数据转换成的对象类
     * @return 对象类List集合
     *
     * @author luojie
     * @date 2020/11/4 13:54
     */
    <T> List<T> easyReadToList(InputStream inputStream, Class<T> clazz);

}
