package com.mz.common.util;

import java.awt.*;
import java.awt.image.BufferedImage;

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
        g2.drawImage(matrixImage,500,200,null);
        Font f = new Font("宋体",Font.PLAIN,20);
        Color color = Color.BLACK;
        g2.setColor(color);
        g2.setFont(f);
        g2.drawString(str,50,60);
        g2.dispose();
        matrixImage.flush();
        return matrixImage;
    }

}
