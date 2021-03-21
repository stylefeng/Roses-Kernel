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
package cn.stylefeng.roses.kernel.log.file;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kernel.auth.api.context.LoginContext;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.log.api.LogManagerApi;
import cn.stylefeng.roses.kernel.log.api.exception.LogException;
import cn.stylefeng.roses.kernel.log.api.pojo.manage.LogManagerRequest;
import cn.stylefeng.roses.kernel.log.api.pojo.record.LogRecordDTO;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static cn.stylefeng.roses.kernel.log.api.constants.LogConstants.DEFAULT_BEGIN_PAGE_NO;
import static cn.stylefeng.roses.kernel.log.api.constants.LogConstants.DEFAULT_PAGE_SIZE;
import static cn.stylefeng.roses.kernel.log.api.constants.LogFileConstants.FILE_CONTRACT_SYMBOL;
import static cn.stylefeng.roses.kernel.log.api.constants.LogFileConstants.FILE_SUFFIX;
import static cn.stylefeng.roses.kernel.log.api.exception.enums.LogExceptionEnum.*;

/**
 * 文件日志读取管理实现类
 *
 * @author majianguo
 * @date 2020/11/3 上午10:56
 */
@Slf4j
public class FileLogManagerServiceImpl implements LogManagerApi {

    private final String fileSavePath;

    private Integer total;

    /**
     * 构造函数
     *
     * @param fileSavePath 文件保存路径
     */
    public FileLogManagerServiceImpl(String fileSavePath) {
        this.fileSavePath = fileSavePath;
    }

    @Override
    public List<LogRecordDTO> findList(LogManagerRequest logManagerParam) {
        PageResult<LogRecordDTO> pageResult = findPage(logManagerParam);
        return pageResult.getRows();
    }

    @Override
    public PageResult<LogRecordDTO> findPage(LogManagerRequest logManagerParam) {

        // 文件日志，必须有AppName,否则文件太多太大
        if (ObjectUtil.isEmpty(logManagerParam.getAppName())) {
            throw new LogException(APP_NAME_NOT_EXIST);
        }

        // 文件日志，必须有开始时间,否则文件太多太大
        if (ObjectUtil.isEmpty(logManagerParam.getBeginDate())) {
            throw new LogException(BEGIN_DATETIME_NOT_EXIST);
        }

        // 获取文件路径
        String filePath = getLogPath(logManagerParam.getAppName(), logManagerParam.getBeginDate());

        // 文件当前指针
        long filePointer = 0L;
        if (logManagerParam.getPageNo() == null) {
            logManagerParam.setPageNo(DEFAULT_BEGIN_PAGE_NO);
        } else {
            // 如果页数不等于1,则根据当前登陆用户信息取出上次读取文件的位置
            if (!DEFAULT_BEGIN_PAGE_NO.equals(logManagerParam.getPageNo())) {
                Object pointer = LoginContext.me().getLoginUser().getOtherInfos().get("filePointer");
                if (ObjectUtil.isNotEmpty(pointer)) {
                    filePointer = (long) pointer;
                }
            }
        }
        if (logManagerParam.getPageSize() == null) {
            logManagerParam.setPageSize(DEFAULT_PAGE_SIZE);
        }
        // 返回分页结果
        PageResult<LogRecordDTO> pageResult = new PageResult<>();
        pageResult.setPageSize(logManagerParam.getPageSize());

        // 读取日志
        List<LogRecordDTO> dtos = readLog(filePath, filePointer, logManagerParam.getPageSize());
        pageResult.setRows(dtos);
        pageResult.setTotalRows(total);
        return pageResult;
    }

    @Override
    public void del(LogManagerRequest logManagerParam) {

        // 删除操作,必须有appName
        if (ObjectUtil.isEmpty(logManagerParam.getAppName())) {
            throw new LogException(APP_NAME_NOT_EXIST);
        }

        // 删除操作,必须有appName
        if (ObjectUtil.isEmpty(logManagerParam.getBeginDate())) {
            throw new LogException(BEGIN_DATETIME_NOT_EXIST);
        }

        // 文件日志，必须有结束时间,否则文件太多太大
        if (ObjectUtil.isEmpty(logManagerParam.getEndDate())) {
            throw new LogException(END_DATETIME_NOT_EXIST);
        }

        // 计算开始和结束两个时间之间的所有日期
        List<String> dates = getIntervalDate(logManagerParam.getBeginDate(), logManagerParam.getEndDate());

        // 查找每一天的日志
        for (String date : dates) {

            // 拼接文件名称
            String logPath = getLogPath(logManagerParam.getAppName(), date);

            // 删除日志
            if (FileUtil.exist(logPath)) {
                FileUtil.del(logPath);
            }
        }
    }

    @Override
    public LogRecordDTO detail(LogManagerRequest logManagerRequest) {

        // 文件日志，必须有AppName,否则文件太多太大
        if (ObjectUtil.isEmpty(logManagerRequest.getAppName())) {
            throw new LogException(APP_NAME_NOT_EXIST);
        }

        // 文件日志，必须有开始时间,否则文件太多太大
        if (ObjectUtil.isEmpty(logManagerRequest.getBeginDate())) {
            throw new LogException(BEGIN_DATETIME_NOT_EXIST);
        }

        // 获取文件路径
        String filePath = getLogPath(logManagerRequest.getAppName(), logManagerRequest.getBeginDate());
        return this.readLog(filePath, logManagerRequest.getLogId());
    }

    /**
     * 根据app名称和日期获取日志文件全路径
     *
     * @param appName APP名称
     * @param date    那一天的日志
     * @return 文件全路径名称
     * @author majianguo
     * @date 2020/11/3 下午4:29
     */
    private String getLogPath(String appName, String date) {

        // 根据传入的AppName和时间来定位确定一个唯一的文件名称
        String fileName = appName + FILE_CONTRACT_SYMBOL + DateUtil.parse(date).toDateStr() + FILE_SUFFIX;

        // 文件绝对路径生成，带文件名的完整路径
        String fileAbsolutePath = fileSavePath + File.separator;
        return fileAbsolutePath + fileName;
    }

    /**
     * 根据id获取日志记录
     *
     * @author chenjinlong
     * @date 2021/2/1 19:54
     */
    private LogRecordDTO readLog(String path, Long logId) {

        // 判断文件是否存在,不存在直接返回Null
        if (!FileUtil.exist(path)) {
            return null;
        }

        LogRecordDTO logRecordDTO = new LogRecordDTO();
        RandomAccessFile file;
        try {
            // 创建随机读文件流
            file = new RandomAccessFile(path, "r");

            while (true) {

                String str = file.readLine();

                // 读取到的字符串不为空
                if (ObjectUtil.isNotEmpty(str)) {
                    logRecordDTO = parseObject(str);
                    if (logRecordDTO.getLogId().equals(logId)) {
                        return logRecordDTO;
                    }
                }
            }

        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                e.printStackTrace();
            }
            log.error(e.getMessage());
        }
        return logRecordDTO;
    }

    /**
     * 从指定的文件指针处,开始读取指定行数的数据
     *
     * @param path        文件全路径
     * @param filePointer 开始读取文件指针位置
     * @param lineNum     读取行数
     * @author majianguo
     * @date 2020/11/3 下午1:36
     */
    private List<LogRecordDTO> readLog(String path, long filePointer, int lineNum) {
        // 判断文件是否存在,不存在直接返回Null
        if (!FileUtil.exist(path)) {
            return null;
        }

        // 返回结果集
        List<LogRecordDTO> readLines = new ArrayList<>();

        RandomAccessFile file;
        try {
            // 创建随机读文件流
            file = new RandomAccessFile(path, "r");

            // 设置文件指针
            file.seek(filePointer);

            // 从设置的指针处开始读取文件
            for (int i = 0; i < lineNum; ++i) {
                String str = file.readLine();

                // 读取到的字符串不为空
                if (ObjectUtil.isNotEmpty(str)) {
                    LogRecordDTO recordDTO = parseObject(str);

                    // 格式转换没有问题就加入到列表中
                    if (ObjectUtil.isNotEmpty(recordDTO)) {
                        readLines.add(recordDTO);
                    }
                }
            }

            //获取总行数

            total = getTotalLines(new File(path));

            // 在用户信息中记录当前用户读取的文件指针
            LoginContext.me().getLoginUser().setOtherInfos(Dict.create().set("filePointer", file.getFilePointer()));

        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                e.printStackTrace();
            }
            log.error(e.getMessage());
        }
        return readLines;
    }

    /**
     * 根据传入的JSON字符串转换成JAVA对象
     *
     * @param jsonStr json字符串
     * @return 返回格式化后的Java对象 如果格式错误,则返回Null
     * @author majianguo
     * @date 2020/11/3 下午2:16
     */
    private LogRecordDTO parseObject(String jsonStr) {
        LogRecordDTO logRecordDTO = null;
        try {

            // 这里接受到的jsonStr是乱码的,需要通过getBytes方法转换成字节数组,然后通过newString的方式再转换回来
            logRecordDTO = JSON.parseObject(new String(getBytes(jsonStr.toCharArray())), LogRecordDTO.class);
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                e.printStackTrace();
            }
            log.error(e.getMessage());
        }
        return logRecordDTO;
    }

    /**
     * 解决readLine读取中文乱码问题
     *
     * @param chars 乱码的字符串转数组
     * @author majianguo
     * @date 2020/11/3 下午4:03
     */
    public static byte[] getBytes(char[] chars) {
        byte[] result = new byte[chars.length];
        for (int i = 0; i < chars.length; i++) {
            result[i] = (byte) chars[i];
        }
        return result;
    }

    /**
     * 计算两个日期之间所有的日期
     *
     * @param start 开始日期 间隔
     * @param end   结束日期
     * @author majianguo
     * @date 2020/11/3 下午4:23
     */
    public static List<String> getIntervalDate(String start, String end) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // 保存日期集合
        List<String> list = new ArrayList<>();
        try {

            Date dateStart = sdf.parse(start);
            Date dateEnd = sdf.parse(end);
            Date date = dateStart;

            // 用Calendar 进行日期比较判断
            Calendar cd = Calendar.getInstance();
            while (date.getTime() <= dateEnd.getTime()) {
                list.add(sdf.format(date));
                cd.setTime(date);

                // 增加一天 放入集合
                cd.add(Calendar.DATE, 1);
                date = cd.getTime();
            }

        } catch (ParseException e) {
            if (log.isDebugEnabled()) {
                e.printStackTrace();
            }
            log.error(e.getMessage());
        }
        return list;
    }

    /**
     * 获取总行数
     *
     * @param file 日志文件路径
     * @return 日志总行数
     * @author chenjinlong
     * @date 2021/2/1 19:45
     */
    public int getTotalLines(File file) throws IOException {
        FileReader in = new FileReader(file);
        LineNumberReader reader = new LineNumberReader(in);
        reader.skip(Long.MAX_VALUE);
        int lines = reader.getLineNumber();
        reader.close();
        return lines;
    }

}
