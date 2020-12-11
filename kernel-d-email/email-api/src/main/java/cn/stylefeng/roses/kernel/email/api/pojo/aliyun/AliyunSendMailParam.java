package cn.stylefeng.roses.kernel.email.api.pojo.aliyun;

import lombok.Data;

/**
 * 发送邮件的请求参数
 *
 * @author fengshuonan
 * @date 2018-07-05 21:19
 */
@Data
public class AliyunSendMailParam {

    /**
     * 发送给某人的邮箱
     * <p>
     * 发送给单个用户的邮箱时候用，只能写一个邮箱
     */
    private String to;

    /**
     * 邮件标题
     */
    private String title;

    /**
     * 邮件内容
     */
    private String content;

    /**
     * 发信人昵称
     */
    private String fromAlias;

    /**
     * 阿里云控制台，创建的标签名称
     */
    private String tagName;

    /**
     * 预先创建且上传了收件人的收件人列表名称，在阿里云控制台创建
     */
    private String receiversName;

    /**
     * 预先创建且通过审核的模板名称，在阿里云控制台创建
     */
    private String templateName;

}
