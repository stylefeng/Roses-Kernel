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
package cn.stylefeng.roses.kernel.log.db;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.log.api.LogRecordApi;
import cn.stylefeng.roses.kernel.log.api.constants.LogConstants;
import cn.stylefeng.roses.kernel.log.api.pojo.record.LogRecordDTO;
import cn.stylefeng.roses.kernel.log.api.threadpool.LogManagerThreadPool;
import cn.stylefeng.roses.kernel.log.db.entity.SysLog;
import cn.stylefeng.roses.kernel.log.db.service.SysLogService;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * 数据库存储方式的日志记录器
 *
 * @author luojie
 * @date 2020/11/2 15:50
 */
@Slf4j
public class DbLogRecordServiceImpl implements LogRecordApi {

    /**
     * 日志记录 service接口
     */
    private final SysLogService sysLogService;

    /**
     * 异步记录日志用的线程池
     */
    private final LogManagerThreadPool logManagerThreadPool;

    /**
     * 日志刷新管理器
     */
    private final LogRefreshManager logRefreshManager;


    public DbLogRecordServiceImpl(LogManagerThreadPool logManagerThreadPool, SysLogService sysLogService) {
        this.logManagerThreadPool = logManagerThreadPool;
        this.sysLogService = sysLogService;
        this.logRefreshManager = new LogRefreshManager();
        this.logRefreshManager.start();
    }

    public DbLogRecordServiceImpl(LogManagerThreadPool logManagerThreadPool, SysLogService sysLogService, long sleepTime, int maxCount) {
        this.logManagerThreadPool = logManagerThreadPool;
        this.sysLogService = sysLogService;
        this.logRefreshManager = new LogRefreshManager(sleepTime, maxCount);
        this.logRefreshManager.start();
    }

    @Override
    public void add(LogRecordDTO logRecordDTO) {
        if (logRecordDTO == null) {
            return;
        }

        // 输出日志
        addBatch(CollectionUtil.list(false, logRecordDTO));
    }

    @Override
    public void addBatch(List<LogRecordDTO> logRecords) {

        if (ObjectUtil.isEmpty(logRecords)) {
            return;
        }

        List<SysLog> sysLogList = logRecords.stream().map(logRecordDTO -> {
            SysLog sysLog = new SysLog();
            // 复制logRecordDTO对象属性到sysLog
            BeanUtil.copyProperties(logRecordDTO, sysLog);

            // 日志名称为空的话则获取默认日志名称
            if (StrUtil.isEmpty(sysLog.getLogName())) {
                sysLog.setLogName(LogConstants.LOG_DEFAULT_NAME);
            }

            // 服务名称为空的话则获取默认服务名称
            if (StrUtil.isEmpty(sysLog.getAppName())) {
                sysLog.setAppName(LogConstants.LOG_DEFAULT_APP_NAME);
            }

            return sysLog;
        }).collect(Collectors.toList());

        // 插入到数据库
        sysLogService.saveBatch(sysLogList);
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

            // 再队列中读取num条数据，放入list
            List<LogRecordDTO> cacheAll = new ArrayList<>(num);
            for (int i = 0; i < num; i++) {
                LogRecordDTO item = queue.poll();
                if (null == item) {
                    break;
                }
                cacheAll.add(item);
            }

            // 调用方法刷新到磁盘
            addBatch(cacheAll);
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
            long currentTimeMillis = System.currentTimeMillis();

            // 如果是激活状态，且消息数大于零，且符合上次调用refresh方法到目前时间的间隔，那就调用一次refresh方法
            if ((refreshMark.get() + sleepTime) <= currentTimeMillis && count.get() > 0) {
                refresh();
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
            // 判断如果队列里面的数据大于等于设定的最大消息数，就调用refresh方法刷新一次数据
            if (count.get() >= maxCount) {
                refresh();
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
