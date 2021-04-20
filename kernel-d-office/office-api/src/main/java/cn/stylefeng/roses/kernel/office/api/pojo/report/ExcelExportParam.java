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
