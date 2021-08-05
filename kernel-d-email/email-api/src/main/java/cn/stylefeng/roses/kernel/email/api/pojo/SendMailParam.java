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
package cn.stylefeng.roses.kernel.email.api.pojo;

import lombok.Data;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 发送邮件的请求参数
 *
 * @author fengshuonan
 * @date 2018-07-05 21:19
 */
@Data
public class SendMailParam {

    /**
     * 收件人列表
     */
    private List<String> tos;

    /**
     * 抄送人列表,可以为null或空
     */
    private List<String> ccsTos;

    /**
     * 密送人列表,可以为null或空
     */
    private List<String> bccsTos;

    /**
     * 邮件标题
     */
    private String title;

    /**
     * 邮件内容
     */
    private String content;

    /**
     * 附件列表
     */
    private File[] files;

    /**
     * 图片与占位符，占位符格式为cid:${cid}
     * <p>
     * 注意:只有发送html邮件，图片才可正常显示
     * 如:测试图片1:<img src='cid:image01'>
     */
    private Map<String, InputStream> imageMap;

}
