package cn.stylefeng.roses.kernel.security.captcha.util;

import cn.hutool.core.codec.Base64;
import cn.stylefeng.roses.kernel.security.api.pojo.DragCaptchaImageDTO;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 生成拖拽验证码的工具
 *
 * @author fengshuonan
 * @date 2021/7/5 14:06
 */
public class DragCaptchaImageUtil {

    /**
     * 验证码图片的base64编码
     */
    public static final String IMAGE_BASE64 = "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";

    /**
     * 源文件宽度
     */
    private static final int ORI_WIDTH = 300;

    /**
     * 源文件高度
     */
    private static final int ORI_HEIGHT = 150;

    /**
     * 模板图宽度
     */
    private static final int CUT_WIDTH = 50;

    /**
     * 模板图高度
     */
    private static final int CUT_HEIGHT = 50;

    /**
     * 抠图凸起圆心
     */
    private static final int CIRCLE_R = 5;

    /**
     * 抠图内部矩形填充大小
     */
    private static final int RECTANGLE_PADDING = 8;

    /**
     * 抠图的边框宽度
     */
    private static final int SLIDER_IMG_OUT_PADDING = 1;

    /**
     * 根据传入的路径生成指定验证码图片
     *
     * @author fengshuonan
     * @date 2021/7/5 14:08
     */
    public static DragCaptchaImageDTO getVerifyImage(InputStream inputStream) throws IOException {
        BufferedImage srcImage = ImageIO.read(inputStream);
        int locationX = CUT_WIDTH + new Random().nextInt(srcImage.getWidth() - CUT_WIDTH * 3);
        int locationY = CUT_HEIGHT + new Random().nextInt(srcImage.getHeight() - CUT_HEIGHT) / 2;
        BufferedImage markImage = new BufferedImage(CUT_WIDTH, CUT_HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);
        int[][] data = getBlockData();
        cutImgByTemplate(srcImage, markImage, data, locationX, locationY);
        return new DragCaptchaImageDTO(getImageBASE64(srcImage), getImageBASE64(markImage), locationX, locationY);
    }

    /**
     * 生成随机滑块形状
     * <p>
     * 0 透明像素
     * 1 滑块像素
     * 2 阴影像素
     *
     * @return int[][]
     */
    private static int[][] getBlockData() {
        int[][] data = new int[CUT_WIDTH][CUT_HEIGHT];
        Random random = new Random();
        //(x-a)²+(y-b)²=r²
        //x中心位置左右5像素随机
        double x1 = RECTANGLE_PADDING + (CUT_WIDTH - 2 * RECTANGLE_PADDING) / 2.0 - 5 + random.nextInt(10);
        //y 矩形上边界半径-1像素移动
        double y1_top = RECTANGLE_PADDING - random.nextInt(3);
        double y1_bottom = CUT_HEIGHT - RECTANGLE_PADDING + random.nextInt(3);
        double y1 = random.nextInt(2) == 1 ? y1_top : y1_bottom;


        double x2_right = CUT_WIDTH - RECTANGLE_PADDING - CIRCLE_R + random.nextInt(2 * CIRCLE_R - 4);
        double x2_left = RECTANGLE_PADDING + CIRCLE_R - 2 - random.nextInt(2 * CIRCLE_R - 4);
        double x2 = random.nextInt(2) == 1 ? x2_right : x2_left;
        double y2 = RECTANGLE_PADDING + (CUT_HEIGHT - 2 * RECTANGLE_PADDING) / 2.0 - 4 + random.nextInt(10);

        double po = Math.pow(CIRCLE_R, 2);
        for (int i = 0; i < CUT_WIDTH; i++) {
            for (int j = 0; j < CUT_HEIGHT; j++) {
                //矩形区域
                boolean fill;
                if ((i >= RECTANGLE_PADDING && i < CUT_WIDTH - RECTANGLE_PADDING)
                        && (j >= RECTANGLE_PADDING && j < CUT_HEIGHT - RECTANGLE_PADDING)) {
                    data[i][j] = 1;
                    fill = true;
                } else {
                    data[i][j] = 0;
                    fill = false;
                }
                //凸出区域
                double d3 = Math.pow(i - x1, 2) + Math.pow(j - y1, 2);
                if (d3 < po) {
                    data[i][j] = 1;
                } else {
                    if (!fill) {
                        data[i][j] = 0;
                    }
                }
                //凹进区域
                double d4 = Math.pow(i - x2, 2) + Math.pow(j - y2, 2);
                if (d4 < po) {
                    data[i][j] = 0;
                }
            }
        }
        //边界阴影
        for (int i = 0; i < CUT_WIDTH; i++) {
            for (int j = 0; j < CUT_HEIGHT; j++) {
                //四个正方形边角处理
                for (int k = 1; k <= SLIDER_IMG_OUT_PADDING; k++) {
                    //左上、右上
                    if (i >= RECTANGLE_PADDING - k && i < RECTANGLE_PADDING
                            && ((j >= RECTANGLE_PADDING - k && j < RECTANGLE_PADDING)
                            || (j >= CUT_HEIGHT - RECTANGLE_PADDING - k && j < CUT_HEIGHT - RECTANGLE_PADDING + 1))) {
                        data[i][j] = 2;
                    }

                    //左下、右下
                    if (i >= CUT_WIDTH - RECTANGLE_PADDING + k - 1 && i < CUT_WIDTH - RECTANGLE_PADDING + 1) {
                        for (int n = 1; n <= SLIDER_IMG_OUT_PADDING; n++) {
                            if (((j >= RECTANGLE_PADDING - n && j < RECTANGLE_PADDING)
                                    || (j >= CUT_HEIGHT - RECTANGLE_PADDING - n && j <= CUT_HEIGHT - RECTANGLE_PADDING))) {
                                data[i][j] = 2;
                            }
                        }
                    }
                }

                if (data[i][j] == 1 && j - SLIDER_IMG_OUT_PADDING > 0 && data[i][j - SLIDER_IMG_OUT_PADDING] == 0) {
                    data[i][j - SLIDER_IMG_OUT_PADDING] = 2;
                }
                if (data[i][j] == 1 && j + SLIDER_IMG_OUT_PADDING > 0 && j + SLIDER_IMG_OUT_PADDING < CUT_HEIGHT && data[i][j + SLIDER_IMG_OUT_PADDING] == 0) {
                    data[i][j + SLIDER_IMG_OUT_PADDING] = 2;
                }
                if (data[i][j] == 1 && i - SLIDER_IMG_OUT_PADDING > 0 && data[i - SLIDER_IMG_OUT_PADDING][j] == 0) {
                    data[i - SLIDER_IMG_OUT_PADDING][j] = 2;
                }
                if (data[i][j] == 1 && i + SLIDER_IMG_OUT_PADDING > 0 && i + SLIDER_IMG_OUT_PADDING < CUT_WIDTH && data[i + SLIDER_IMG_OUT_PADDING][j] == 0) {
                    data[i + SLIDER_IMG_OUT_PADDING][j] = 2;
                }
            }
        }
        return data;
    }

    /**
     * 裁剪区块
     * 根据生成的滑块形状，对原图和裁剪块进行变色处理
     *
     * @param oriImage    原图
     * @param targetImage 裁剪图
     * @param blockImage  滑块
     * @param x           裁剪点x
     * @param y           裁剪点y
     */
    private static void cutImgByTemplate(BufferedImage oriImage, BufferedImage targetImage, int[][] blockImage, int x, int y) {
        for (int i = 0; i < CUT_WIDTH; i++) {
            for (int j = 0; j < CUT_HEIGHT; j++) {
                int _x = x + i;
                int _y = y + j;
                int rgbFlg = blockImage[i][j];
                int rgb_ori = oriImage.getRGB(_x, _y);
                // 原图中对应位置变色处理
                if (rgbFlg == 1) {
                    //抠图上复制对应颜色值
                    targetImage.setRGB(i, j, rgb_ori);
                    //原图对应位置颜色变化
                    oriImage.setRGB(_x, _y, Color.LIGHT_GRAY.getRGB());
                } else if (rgbFlg == 2) {
                    targetImage.setRGB(i, j, Color.WHITE.getRGB());
                    oriImage.setRGB(_x, _y, Color.GRAY.getRGB());
                } else if (rgbFlg == 0) {
                    //int alpha = 0;
                    targetImage.setRGB(i, j, rgb_ori & 0x00ffffff);
                }
            }

        }
    }

    /**
     * 随机获取一张图片对象
     *
     * @author fengshuonan
     * @date 2021/7/5 14:07
     */
    public static BufferedImage getRandomImage(String path) throws IOException {
        File files = new File(path);
        File[] fileList = files.listFiles();
        List<String> fileNameList = new ArrayList<>();
        if (fileList != null && fileList.length != 0) {
            for (File tempFile : fileList) {
                if (tempFile.isFile() && tempFile.getName().endsWith(".jpg")) {
                    fileNameList.add(tempFile.getAbsolutePath().trim());
                }
            }
        }
        Random random = new Random();
        File imageFile = new File(fileNameList.get(random.nextInt(fileNameList.size())));
        return ImageIO.read(imageFile);
    }

    /**
     * 将IMG输出为文件
     *
     * @author fengshuonan
     * @date 2021/7/5 14:07
     */
    public static void writeImg(BufferedImage image, String file) throws Exception {
        byte[] imagedata = null;
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        ImageIO.write(image, "png", bao);
        imagedata = bao.toByteArray();
        FileOutputStream out = new FileOutputStream(new File(file));
        out.write(imagedata);
        out.close();
    }

    /**
     * 将图片转换为BASE64
     *
     * @author fengshuonan
     * @date 2021/7/5 14:07
     */
    public static String getImageBASE64(BufferedImage image) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(image, "png", out);
        //转成byte数组
        byte[] bytes = out.toByteArray();
        //生成BASE64编码
        return Base64.encode(bytes);
    }

}