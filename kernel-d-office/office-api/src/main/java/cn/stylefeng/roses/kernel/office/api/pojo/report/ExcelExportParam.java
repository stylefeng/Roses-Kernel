package cn.stylefeng.roses.kernel.office.api.pojo.report;

import com.alibaba.excel.support.ExcelTypeEnum;
import lombok.Data;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Excel 导出参数
 *
 * @author luojie
 * @date 2020/11/4 10:02
 */
@Data
public class ExcelExportParam {

    /**
     * 需要导出的数据列表
     */
    List<?> dataList;

    /**
     * Excel每行数据转换成的对象类
     */
    Class<?> clazz;

    /**
     * 工作簿名称 导出/写入文件用
     */
    String sheetName;

    /**
     * 下载提示的文件名 无需带上xls、xlsx后缀
     */
    String fileName;

    /**
     * Excel类型 xls、xlsx
     */
    ExcelTypeEnum excelTypeEnum;

    /**
     * http 响应
     */
    HttpServletResponse response;

    /**
     * 文件写入绝对路径 写入到服务器磁盘用
     */
    String excelFileWriteAbsolutePath;

}
