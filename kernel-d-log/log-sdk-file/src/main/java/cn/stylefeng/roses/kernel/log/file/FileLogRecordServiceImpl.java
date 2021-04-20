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

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.stylefeng.roses.kernel.log.api.LogRecordApi;
import cn.stylefeng.roses.kernel.log.api.constants.LogFileConstants;
import cn.stylefeng.roses.kernel.log.api.pojo.record.LogRecordDTO;
import cn.stylefeng.roses.kernel.log.api.threadpool.LogManagerThreadPool;
import com.alibaba.fastjson.JSON;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import static cn.stylefeng.roses.kernel.log.api.constants.LogFileConstants.FILE_CONTRACT_SYMBOL;
import static cn.stylefeng.roses.kernel.log.api.constants.LogFileConstants.FILE_SUFFIX;

/**
 * 文件存储方式的日志记录器
 *
 * @author fengshuonan
 * @date 2020/10/28 14:52
 */
public class FileLogRecordServiceImpl implements LogRecordApi {

    private final LogManagerThreadPool logManagerThreadPool;

    private final LogRefreshManager logRefreshManager;

    private final String fileSavePath;

    public FileLogRecordServiceImpl(String fileSavePath, LogManagerThreadPool logManagerThreadPool) {
        this.fileSavePath = fileSavePath;
        this.logManagerThreadPool = logManagerThreadPool;
        this.logRefreshManager = new LogRefreshManager();
        this.logRefreshManager.start();
    }

    public FileLogRecordServiceImpl(String fileSavePath, LogManagerThreadPool logManagerThreadPool, long sleepTime, int maxCount) {
        this.fileSavePath = fileSavePath;
        this.logManagerThreadPool = logManagerThreadPool;
        this.logRefreshManager = new LogRefreshManager(sleepTime, maxCount);
        this.logRefreshManager.start();
    }

    /**
     * 日志文件的组成形式应为appName-年-月-日.log
     *
     * @author fengshuonan
     * @date 2020/10/28 15:53
     */
    @Override
    public void add(LogRecordDTO logRecordDTO) {

        if (logRecordDTO == null) {
            return;
        }

        // 输出日志
        addBatch(CollectionUtil.list(false, logRecordDTO));
    }

    /**
     * 批量输出日志
     *
     * @param list 待输出日志列表
     * @author majianguo
     * @date 2020/11/2 下午2:59
     */
    @Override
    public void addBatch(List<LogRecordDTO> list) {

        if (ObjectUtil.isEmpty(list)) {
            return;
        }

        // 获取appName
        String appName = list.get(0).getAppName();
        if (StrUtil.isBlank(appName)) {
            appName = LogFileConstants.DEFAULT_LOG_FILE_NAME;
        }

        // 获取日志记录的日期
        Date dateTime = list.get(0).getDateTime();
        if (dateTime == null) {
            dateTime = new Date();
        }
        String dateStr = DateUtil.formatDate(dateTime);

        // 拼接文件名
        String fileName = appName + FILE_CONTRACT_SYMBOL + dateStr + FILE_SUFFIX;

        // 文件绝对路径生成，带文件名的完整路径
        String fileAbsolutePath = fileSavePath + File.separator + fileName;

        // 判断文件是否存在，不存在则创建
        boolean existFlag = FileUtil.exist(fileAbsolutePath);
        if (!existFlag) {
            FileUtil.touch(fileAbsolutePath);
        }

        // 将对象转换成JSON输出
        List<String> outList = new ArrayList<>();
        for (LogRecordDTO recordDTO : list) {
            outList.add(JSON.toJSONString(recordDTO));
        }

        // 追加日志内容
        FileUtil.appendLines(outList, fileAbsolutePath, StandardCharsets.UTF_8);
    }

    @Override
    public void addAsync(LogRecordDTO logRecordDTO) {
        logManagerThreadPool.executeLog(new TimerTask() {
            @Override
            public void run() {
                logRefreshManager.putLog(logRecordDTO);
            }
        });
    }

    /**
     * 日志刷新管理器
     * <p>
     * 该类暂存所有将要写出到磁盘的日志，使用内存缓冲区减少对磁盘IO的操作
     * <p>
     * 该类维护一个最大日志数和一个刷新日志间隔，满足任意一个条件即可触发从内存写出日志到磁盘的操作
     *
     * @author majianguo
     * @date 2020/10/31 15:05
     */
    class LogRefreshManager extends Thread {

        /**
         * Hutool日志对象
         */
        private final Log log = LogFactory.get();

        /**
         * 刷新日志间隔(默认3秒),单位毫秒
         */
        private final long sleepTime;

        /**
         * 满足多少条就强制刷新一次（默认300条）,该值只是一个大约值，实际记录并不会一定等于该值（可能会大于该值，不可能小于该值）
         */
        private final int maxCount;

        /**
         * 刷新数据时间标志,每次刷新都记录当前的时间戳，方便定时刷新准确判断上次刷新和本次刷新的时间间隔
         */
        private final AtomicLong refreshMark = new AtomicLong();

        public LogRefreshManager() {
            this.sleepTime = 3000;
            this.maxCount = 300;
        }

        public LogRefreshManager(long sleepTime) {
            this.sleepTime = sleepTime;
            this.maxCount = 300;
        }

        public LogRefreshManager(int maxCount) {
            this.sleepTime = 3000;
            this.maxCount = maxCount;
        }

        public LogRefreshManager(long sleepTime, int maxCount) {
            this.sleepTime = sleepTime;
            this.maxCount = maxCount;
        }

        /**
         * 未处理日志的消息队列
         */
        private final Queue<LogRecordDTO> queue = new ConcurrentLinkedQueue<>();

        /**
         * 消息总数,队列的size方法会遍历一遍队列，所以自己维护大小
         */
        public AtomicInteger count = new AtomicInteger(0);

        /**
         * 往队列内新增一条日志数据
         *
         * @param logRecordDTO 日志对象
         * @author majianguo
         * @date 2020/10/31 14:59
         */
        public void putLog(LogRecordDTO logRecordDTO) {

            int queueDataCount = count.get();

            // 如果是第一条消息，刷新一次refreshMark
            if (queueDataCount == 0) {
                refreshMark.getAndSet(System.currentTimeMillis());
            }

            // 如果后续写入磁盘的操作有异常(磁盘满了)，为了防止日志刷不出去导致OOM,内存中最大只保留maxCount数一倍的日志，后续日志将丢弃
            if (queueDataCount >= (maxCount * 2)) {
                return;
            }

            queue.offer(logRecordDTO);
            count.incrementAndGet();
        }

        /**
         * 刷新日志到磁盘的操作
         *
         * @author majianguo
         * @date 2020/10/31 15:48
         */
        private void refresh() {
            // 让睡眠线程本次不要再调本方法，睡眠至下次看refreshMark的值再决定要不要调用本方法
            refreshMark.getAndSet(System.currentTimeMillis());

            // 获取总数
            int num = count.getAndSet(0);

            // 缓冲队列中所有的数据
            List<LogRecordDTO> cacheAll = new ArrayList<>(num);

            try {
                // 在队列中读取num条数据，放入list
                for (int i = 0; i < num; i++) {
                    LogRecordDTO item = queue.poll();
                    if (null == item) {
                        break;
                    }
                    cacheAll.add(item);
                }

                // 调用方法刷新到磁盘
                addBatch(cacheAll);

            } catch (Exception e) {

                // 有异常把日志刷回队列，不要丢掉(这里可能会导致日志顺序错乱)
                for (LogRecordDTO recordDTO : cacheAll) {
                    queue.offer(recordDTO);
                }

                // 打印日志
                if (log.isDebugEnabled()) {
                    e.printStackTrace();
                }
                log.error(e.getMessage());
            }

        }

        /**
         * 日志数据定时执行器
         * <p>
         * 用于定时检测日志数据是否可以写入数据
         *
         * @author majianguo
         * @date 2020/10/31 15:57
         */
        private void timing() {
            try {
                // 如果是激活状态，且消息数大于零，且符合上次调用refresh方法到目前时间的间隔，那就调用一次refresh方法
                if ((refreshMark.get() + sleepTime) <= System.currentTimeMillis() && count.get() > 0) {
                    refresh();
                }
            } catch (Exception e) {
                if (log.isDebugEnabled()) {
                    e.printStackTrace();
                }
                log.error(e.getMessage());
            }
        }

        /**
         * 日志数据监听器
         * <p>
         * 用于监听日志消息队列，达到设定的数就开始执行刷入硬盘的操作
         *
         * @author majianguo
         * @date 2020/11/2 9:32
         */
        private void listener() {
            try {
                // 判断如果队列里面的数据大于等于设定的最大消息数，就调用refresh方法刷新一次数据
                if (count.get() >= maxCount) {
                    refresh();
                }
            } catch (Exception e) {
                if (log.isDebugEnabled()) {
                    e.printStackTrace();
                }
                log.error(e.getMessage());
            }
        }

        @Override
        @SuppressWarnings("InfiniteLoopStatement")
        public void run() {
            try {
                for (; ; ) {
                    // 消息监听器
                    listener();
                    // 定时任务监听器
                    timing();
                    TimeUnit.MILLISECONDS.sleep(10);
                }
            } catch (InterruptedException e) {
                if (log.isDebugEnabled()) {
                    e.printStackTrace();
                }
                log.error(e.getMessage());
            }
        }
    }

}
