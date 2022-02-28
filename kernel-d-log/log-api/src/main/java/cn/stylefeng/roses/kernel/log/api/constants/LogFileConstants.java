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
package cn.stylefeng.roses.kernel.log.api.constants;

/**
 * 日志文件的常量
 *
 * @author fengshuonan
 * @date 2020/10/28 15:56
 */
public interface LogFileConstants {

    /**
     * 记录日志文件名前缀，如果没有appName的话
     * <p>
     * 例如：app-logs-2020-10-28.log
     */
    String DEFAULT_LOG_FILE_NAME = "app-logs";

    /**
     * 文件拼接符号
     */
    String FILE_CONTRACT_SYMBOL = "-";

    /**
     * 日志文件的后缀名
     */
    String FILE_SUFFIX = ".log";

    /**
     * 默认文件存储路径（windows的）
     */
    String DEFAULT_FILE_SAVE_PATH_WINDOWS = "d:/logfiles";

    /**
     * 默认文件存储路径（linux和windows的的）
     */
    String DEFAULT_FILE_SAVE_PATH_LINUX = "/tmp/logfiles";

    /**
     * 默认api日志记录的aop的顺序
     */
    Integer DEFAULT_API_LOG_AOP_SORT = 500;

    /**
     * 默认全局记录日志的开关
     */
    Boolean DEFAULT_GLOBAL_LOG_FLAG = false;

}
