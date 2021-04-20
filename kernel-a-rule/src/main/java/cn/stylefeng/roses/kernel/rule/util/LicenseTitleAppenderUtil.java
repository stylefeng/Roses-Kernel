package cn.stylefeng.roses.kernel.rule.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 一键添加开源协议头
 *
 * @author fengshuonan
 * @date 2021/3/21 20:24
 */
public class LicenseTitleAppenderUtil {

    /**
     * 添加开源协议
     *
     * @author fengshuonan
     * @date 2021/3/21 20:25
     */
    public static void append(String codeDirectory, String licenseHeader) {
        List<File> files = FileUtil.loopFiles(codeDirectory);
        for (File file : files) {
            if (file.getName().endsWith(".java")) {
                List<String> strings = FileUtil.readLines(file, CharsetUtil.UTF_8);
                if (!strings.get(0).equals("/*")) {
                    ArrayList<Object> newLines = new ArrayList<>();
                    newLines.add(licenseHeader);
                    newLines.addAll(strings);
                    FileUtil.writeUtf8Lines(newLines, file);
                }
            }
        }
    }

}
