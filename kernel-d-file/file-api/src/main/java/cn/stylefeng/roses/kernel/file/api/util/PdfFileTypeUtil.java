package cn.stylefeng.roses.kernel.file.api.util;

import cn.hutool.core.util.StrUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * pdf文件类型识别工具
 *
 * @author majianguo
 * @date 2020/12/27 13:06
 */
public class PdfFileTypeUtil {

    private static final List<String> PDF_TYPES;

    static {
        PDF_TYPES = new ArrayList<>();
        PDF_TYPES.add("pdf");
    }

    /**
     * 根据文件名称获取文件是否为PDF类型
     *
     * @param fileName 文件名称
     * @return boolean true-是图片类型，false-不是图片类型
     * @author fengshuonan
     * @date 2020/11/29 14:04
     */
    public static boolean getFilePdfTypeFlag(String fileName) {
        if (StrUtil.isEmpty(fileName)) {
            return false;
        }

        for (String picType : PDF_TYPES) {
            if (fileName.toLowerCase().endsWith(picType)) {
                return true;
            }
        }

        return false;
    }

}
