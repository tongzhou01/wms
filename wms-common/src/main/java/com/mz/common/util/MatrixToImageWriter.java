package com.mz.common.util;

import com.google.zxing.*;
import com.google.zxing.common.BitMatrix;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;

/**
 * 二维码的生成需要借助MatrixToImageWriter类，该类是由Google提供的，可以将该类直接拷贝到源码中使用，当然你也可以自己写个
 * 生产条形码的基类
 */
public class MatrixToImageWriter {
    private static Logger logger = Logger.getLogger(MatrixToImageWriter.class);
    private static final int BLACK = 0xFF000000;//用于设置图案的颜色
    private static final int WHITE = 0xFFFFFFFF; //用于背景色


    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, (matrix.get(x, y) ? BLACK : WHITE));
//              image.setRGB(x, y,  (matrix.get(x, y) ? Color.YELLOW.getRGB() : Color.CYAN.getRGB()));
            }
        }
        return image;
    }

    public static void writeToFile(BitMatrix matrix, String format, File file, String logUri) throws IOException {

        System.out.println("write to file");
        BufferedImage image = toBufferedImage(matrix);
        //设置logo图标
        QRCodeFactory logoConfig = new QRCodeFactory();
        image = logoConfig.setMatrixLogo(image, logUri);

        if (!ImageIO.write(image, format, file)) {
            System.out.println("生成图片失败");
            throw new IOException("Could not write an image of format " + format + " to " + file);
        } else {
            System.out.println("图片生成成功！");
        }
    }

    public static void writeToStream(BitMatrix matrix, String format, OutputStream stream) throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        //image = BarcodeUtil.setBarcodeNumber(image,"321341546466");
        if (!ImageIO.write(image, format, stream)) {
            throw new IOException("Could not write an image of format " + format);
        }
    }
    public static void writeToStream(BufferedImage bufferedImage, String format, OutputStream stream) throws IOException {
        if (!ImageIO.write(bufferedImage, format, stream)) {
            throw new IOException("Could not write an image of format " + format);
        }
    }

    public static void createQr(String qrCode, int width, int height, BarcodeFormat barFormat, String format, HttpServletResponse response) {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.MARGIN,1);
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = new MultiFormatWriter().encode(qrCode, barFormat, width, height, hints);
            MatrixToImageWriter.writeToStream(bitMatrix, format, response.getOutputStream());
        } catch (IOException e) {
            logger.error("IO异常");
        } catch (WriterException e) {
            logger.error("WriterException");
        }
    }
}
