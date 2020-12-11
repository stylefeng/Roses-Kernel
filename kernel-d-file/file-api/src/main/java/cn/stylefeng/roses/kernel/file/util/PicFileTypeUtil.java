package cn.stylefeng.roses.kernel.file.util;

import cn.hutool.core.util.StrUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件类型识别工具
 *
 * @author fengshuonan
 * @date 2020/11/29 14:00
 */
public class PicFileTypeUtil {

    private static final List<String> PIC_TYPES;

    static {
        PIC_TYPES = new ArrayList<>();
        PIC_TYPES.add("jpg");
        PIC_TYPES.add("png");
        PIC_TYPES.add("jpeg");
        PIC_TYPES.add("tif");
        PIC_TYPES.add("gif");
        PIC_TYPES.add("bmp");
    }

    /**
     * 根据文件名称获取文件是否为图片类型
     *
     * @param fileName 文件名称
     * @return boolean true-是图片类型，false-不是图片类型
     * @author fengshuonan
     * @date 2020/11/29 14:04
     */
    public static boolean getFileImgTypeFlag(String fileName) {
        if (StrUtil.isEmpty(fileName)) {
            return false;
        }

        for (String picType : PIC_TYPES) {
            if (fileName.toLowerCase().endsWith(picType)) {
                return true;
            }
        }

        return false;
    }

}
