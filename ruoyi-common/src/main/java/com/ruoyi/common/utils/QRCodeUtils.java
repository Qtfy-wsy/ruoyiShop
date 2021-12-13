package com.ruoyi.common.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.util.ObjectUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;

/**
 * Created by 伊甸园商城 on 17/3/13.
 * 二维码生成器
 */
public final class QRCodeUtils {

    /**
     * 黑色
     */
    private static final int BLACK = 0xFF000000;

    /**
     * 白色
     */
    private static final int WHITE = 0xFFFFFFFF;

    private QRCodeUtils() {

    }

    /**
     * 创建二维码图片
     *
     * @param url    链接地址
     * @param width  宽
     * @param height 高
     */
    public static void createQRImg(HttpServletResponse response, String url, Integer width, Integer height) {
        OutputStream outputStream = null;
        try {
            //如果宽高没有赋值，则给一个默认值
            if (ObjectUtils.isEmpty(width)) {
                width = 200;
            }
            if (ObjectUtils.isEmpty(height)) {
                height = 200;
            }
            outputStream = response.getOutputStream();
            QRCodeUtils.createQrCode(url, width, height, "jpg", outputStream);
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    /**
     * 生成二维码
     *
     * @param content 内容
     * @param width   宽
     * @param height  高
     */
    public static void createQrCode(String content, int width, int height, String format, OutputStream stream) throws Exception {

        Hashtable<EncodeHintType, String> hints = new Hashtable<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);

        BufferedImage bufferedImage = toBufferedImage(deleteWhite(bitMatrix));

        ImageIO.write(bufferedImage, format, stream);
    }

    /**
     * 生成二维码
     *
     * @param content 内容
     * @param width   宽
     * @param height  高
     * @param format  图片格式
     * @return 返回图片字节
     */
    public static byte[] createQrCode(String content, int width, int height, String format) throws Exception {

        Hashtable<EncodeHintType, String> hints = new Hashtable<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);

        return imageToBytes(toBufferedImage(bitMatrix), format);
    }


    /**
     * 生成图片
     */
    private static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }


    /**
     * 去白边
     */
    private static BitMatrix deleteWhite(BitMatrix matrix) {
        int[] rec = matrix.getEnclosingRectangle();
        int resWidth = rec[2] + 1;
        int resHeight = rec[3] + 1;

        BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
        resMatrix.clear();
        for (int i = 0; i < resWidth; i++) {
            for (int j = 0; j < resHeight; j++) {
                if (matrix.get(i + rec[0], j + rec[1])) {
                    resMatrix.set(i, j);
                }
            }
        }

        int width = resMatrix.getWidth();
        int height = resMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, resMatrix.get(x, y) ? 0 : 255);
            }
        }

        return resMatrix;

    }

    /**
     * 转换BufferedImage 数据为byte数组
     *
     * @param bImage
     * @param format image格式字符串.如"gif","png"
     * @return byte数组
     */
    public static byte[] imageToBytes(BufferedImage bImage, String format) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] bytes = null;
        try {
            ImageIO.write(bImage, format, out);
            bytes = out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }


}
