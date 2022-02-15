package cn.stylefeng.roses.kernel.security.api.pojo;

import cn.stylefeng.roses.kernel.rule.annotation.ChineseDescription;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 剪裁图片dto传输实体
 *
 * @author fengshuonan
 * @date 2021/7/5 14:10
 */
@Data
@AllArgsConstructor
public class DragCaptchaImageDTO {

    /**
     * 本次验证码缓存的key
     */
    @ChineseDescription("本次验证码缓存的key")
    private String key;

    /**
     * 剪裁后的源图片（base64编码）
     */
    @ChineseDescription("剪裁后的源图片（base64编码）")
    private String srcImage;

    /**
     * 剪裁的小拼图图片（base64编码）
     */
    @ChineseDescription("剪裁的小拼图图片（base64编码）")
    private String cutImage;

    /**
     * x轴坐标
     */
    @ChineseDescription("x轴坐标")
    private Integer locationX;

    /**
     * y轴坐标
     */
    @ChineseDescription("y轴坐标")
    private Integer locationY;

    public DragCaptchaImageDTO(String srcImage, String cutImage, int locationX, int locationY) {
        this.srcImage = srcImage;
        this.cutImage = cutImage;
        this.locationX = locationX;
        this.locationY = locationY;
    }

}