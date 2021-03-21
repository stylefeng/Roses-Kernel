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
package cn.stylefeng.roses.kernel.office.excel;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.office.excel.listener.SimpleDataListener;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import cn.stylefeng.roses.kernel.office.api.OfficeExcelApi;
import cn.stylefeng.roses.kernel.office.api.constants.OfficeConstants;
import cn.stylefeng.roses.kernel.office.api.exception.OfficeException;
import cn.stylefeng.roses.kernel.office.api.exception.enums.OfficeExceptionEnum;
import cn.stylefeng.roses.kernel.office.api.pojo.report.ExcelExportParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Excel 常用操作接口实现
 *
 * @author luojie
 * @date 2020/11/3 16:45
 */
@Slf4j
@Service
public class OfficeExcel implements OfficeExcelApi {

    @Override
    public <T> List<T> easyReadToList(InputStream inputStream, Class<T> clazz) {
        if (inputStream == null) {
            return new ArrayList<T>();
        }

        // 创建一个简单的数据监听器
        SimpleDataListener<T> readListener = new SimpleDataListener<T>();

        // 读取文件
        try {
            EasyExcel.read(inputStream, clazz, readListener).sheet().doRead();
        } catch (Exception e) {
            log.error(e.getMessage());

            // 组装提示信息
            String userTip = OfficeExceptionEnum.OFFICE_ERROR.getUserTip();
            String finalUserTip = StrUtil.format(userTip, e.getMessage());
            throw new OfficeException(OfficeExceptionEnum.OFFICE_ERROR.getErrorCode(), finalUserTip);
        }

        return readListener.getDataList();
    }

    @Override
    public void easyWriteToFile(ExcelExportParam excelExportParam) {

        // 默认值
        createDefaultValue(excelExportParam);

        ExcelTypeEnum excelTypeEnum = excelExportParam.getExcelTypeEnum();
        String excelFileWriteAbsolutePath = excelExportParam.getExcelFileWriteAbsolutePath();

        try {
            EasyExcel.write(excelFileWriteAbsolutePath, excelExportParam.getClazz()).excelType(excelTypeEnum).sheet(excelExportParam.getSheetName()).doWrite(excelExportParam.getDataList());
        } catch (Exception e) {
            log.error(e.getMessage());

            // 组装提示信息
            String userTip = OfficeExceptionEnum.OFFICE_ERROR.getUserTip();
            String finalUserTip = StrUtil.format(userTip, e.getMessage());
            throw new OfficeException(OfficeExceptionEnum.OFFICE_ERROR.getErrorCode(), finalUserTip);
        }
    }

    @Override
    public void easyExportDownload(ExcelExportParam excelExportParam) {
        if (ObjectUtil.isEmpty(excelExportParam)) {
            return;
        }

        try {
            HttpServletResponse response = excelExportParam.getResponse();
            if (response == null) {
                throw new OfficeException(OfficeExceptionEnum.OFFICE_EXCEL_EXPORT_RESPONSE_ISNULL);
            }

            if (excelExportParam.getClazz() == null) {
                throw new OfficeException(OfficeExceptionEnum.OFFICE_EXCEL_EXPORT_ENTITY_CLASS_ISNULL);
            }

            // 默认值
            createDefaultValue(excelExportParam);

            ExcelTypeEnum excelTypeEnum = excelExportParam.getExcelTypeEnum();

            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode(excelExportParam.getFileName(), "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", String.format("%s%s", "attachment;filename*=utf-8''", fileName, excelTypeEnum.getValue()));

            EasyExcel.write(response.getOutputStream(), excelExportParam.getClazz()).excelType(excelTypeEnum).sheet(excelExportParam.getSheetName()).doWrite(excelExportParam.getDataList());

        } catch (Exception e) {
            log.error(e.getMessage());

            // 组装提示信息
            String userTip = OfficeExceptionEnum.OFFICE_ERROR.getUserTip();
            String finalUserTip = StrUtil.format(userTip, e.getMessage());
            throw new OfficeException(OfficeExceptionEnum.OFFICE_ERROR.getErrorCode(), finalUserTip);
        }

    }

    /**
     * excel导出文件的默认属性
     *
     * @param param Excel导出参数
     * @author luojie
     * @date 2020/11/4 11:45
     */
    private void createDefaultValue(ExcelExportParam param) {

        if (StrUtil.isEmpty(param.getSheetName())) {
            param.setSheetName(OfficeConstants.OFFICE_EXCEL_DEFAULT_SHEET_NAME);
        }

        if (StrUtil.isEmpty(param.getFileName())) {
            param.setFileName(OfficeConstants.OFFICE_EXCEL_EXPORT_DEFAULT_FILE_NAME);
        }

        if (param.getExcelTypeEnum() == null) {
            param.setExcelTypeEnum(ExcelTypeEnum.XLSX);
        }
    }

}
