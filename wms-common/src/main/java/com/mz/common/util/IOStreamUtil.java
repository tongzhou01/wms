package com.mz.common.util;

import org.apache.log4j.Logger;

import java.io.*;

public class IOStreamUtil {
    private static Logger logger = Logger.getLogger(IOStreamUtil.class);

    private static void createFile(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            if (file.isDirectory()) {
                file.mkdirs();
            } else {
                file.getParentFile().mkdirs();
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String readFileStream(String filePath, String toPath) {
        try {
            File file = new File(filePath);
            File tofile = new File(toPath);
            if (!file.exists()) {
                logger.info("系统找不到指定源文件路径");
                return "系统找不到指定源文件路径";
            }
            if (!tofile.exists()) {
                createFile(toPath);
            }
            InputStream inputStream = new FileInputStream(file);
            //BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            FileOutputStream outputStream = new FileOutputStream(tofile);
            int byteSize = 1024;
            byte[] bytes = new byte[byteSize];
            int temp = 0;
            while ((temp = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, temp);
            }
            outputStream.close();
            //outputStream.toString();
        } catch (FileNotFoundException e) {
            logger.error("系统找不到指定路径-FileNotFoundException", e);
        } catch (IOException e) {
            logger.error("IO错误-IOException", e);
            e.printStackTrace();
        }
        return "success";
    }

    public static void main(String[] args) {
        readFileStream("F:/test/vmware.log", "F:/test/hahaheihei.log");
    }
}
