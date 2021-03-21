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
