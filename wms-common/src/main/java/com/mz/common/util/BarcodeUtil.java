package com.mz.common.util;

import org.jbarcode.JBarcode;
import org.jbarcode.encode.Code128Encoder;
import org.jbarcode.encode.InvalidAtributeException;
import org.jbarcode.paint.BaseLineTextPainter;
import org.jbarcode.paint.WidthCodedPainter;

import javax.servlet.ServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 条形码工具
 *
 * @author tongzhou
 * @date 2018-03-14 11:23
 **/
public class BarcodeUtil {

    public static BufferedImage setBarcodeNumber(BufferedImage matrixImage, String str) {
        /**
         * 读取图片，并构建绘图对象
         */
        Graphics2D g2 = matrixImage.createGraphics();
        int matrixWidth = matrixImage.getWidth();
        int matrixHeigh = matrixImage.getHeight();
        g2.drawImage(matrixImage, 500, 200, null);
        Font f = new Font("宋体", Font.PLAIN, 20);
        Color color = Color.BLACK;
        g2.setColor(color);
        g2.setFont(f);
        g2.drawString(str, 50, 60);
        g2.dispose();
        matrixImage.flush();
        return matrixImage;
    }

    public static void getQrImage(String code, ServletResponse response, String format) {
        JBarcode localJBarcode = new JBarcode(Code128Encoder.getInstance(), WidthCodedPainter.getInstance(), BaseLineTextPainter.getInstance());
        try {
            BufferedImage bufferedImage;
            bufferedImage = localJBarcode.createBarcode(code);
            MatrixToImageWriter.writeToStream(bufferedImage, format, response.getOutputStream());
        } catch (InvalidAtributeException e) {
        } catch (IOException e) {
        }
    }
}
