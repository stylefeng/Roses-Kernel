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
    private static void append() {
        String directoryPath = "D:\\temp";
        List<File> files = FileUtil.loopFiles(directoryPath);
        for (File file : files) {
            if (file.getName().endsWith(".java")) {
                List<String> strings = FileUtil.readLines(file, CharsetUtil.UTF_8);
                if (!strings.get(0).equals("/*")) {
                    ArrayList<Object> newLines = new ArrayList<>();
                    newLines.add(getTitle());
                    newLines.addAll(strings);
                    FileUtil.writeUtf8Lines(newLines, file);
                }
            }
        }
    }

    private static String getTitle() {
        return "/*\n" +
                " * Copyright [2020-2030] [https://www.stylefeng.cn]\n" +
                " *\n" +
                " * Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                " * you may not use this file except in compliance with the License.\n" +
                " * You may obtain a copy of the License at\n" +
                " *\n" +
                " * http://www.apache.org/licenses/LICENSE-2.0\n" +
                " *\n" +
                " * Unless required by applicable law or agreed to in writing, software\n" +
                " * distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                " * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                " * See the License for the specific language governing permissions and\n" +
                " * limitations under the License.\n" +
                " *\n" +
                " * Guns采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：\n" +
                " *\n" +
                " * 1.请不要删除和修改根目录下的LICENSE文件。\n" +
                " * 2.请不要删除和修改Guns源码头部的版权声明。\n" +
                " * 3.请保留源码和相关描述文件的项目出处，作者声明等。\n" +
                " * 4.分发源码时候，请注明软件出处 https://gitee.com/stylefeng/guns\n" +
                " * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/stylefeng/guns\n" +
                " * 6.若您的项目无法满足以上几点，可申请商业授权\n" +
                " */";
    }

}
