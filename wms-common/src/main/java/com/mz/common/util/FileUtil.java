package com.mz.common.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;

public class FileUtil {
    /**
     * @param
     * @param args
     * @throws IOException
     * @description 读取文件解码转换为string
     */
    public String getBase64FileToString(InputStream inputStream) throws IOException {
        //InputStream inputStream = new FileInputStream(filepath);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuffer buffer = new StringBuffer();
        String line = "";
        while ((line = bufferedReader.readLine()) != null) {
            buffer.append(line);
        }
        return buffer.toString();
    }

    /**
     * @param string
     * @param args
     * @throws IOException
     * @description 将string格式的base64转换为解码后的字节流
     */
    public InputStream getBase64FileToStream(String base64Content) throws IOException {
        //System.out.println("base64Content="+base64Content);
        BASE64Decoder decoder = new BASE64Decoder();
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        byte[] bytes = decoder.decodeBuffer(base64Content);
        ByteArrayInputStream byteInputStream = new ByteArrayInputStream(bytes);
        bis = new BufferedInputStream(byteInputStream);
        return bis;
    }

    /**
     * @throws IOException
     * @throws 将inputstream写入filepath下
     */
    public static void InputToFile(InputStream inputStream, String filepath) throws IOException {
        FileOutputStream newPdf = null;
        try {
            newPdf = new FileOutputStream(filepath);
            int ch = 0;
            while ((ch = inputStream.read()) != -1) {
                newPdf.write(ch);
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            newPdf.close();
            inputStream.close();
        }

    }

    /**
     * Description: 将pdf文件转换为Base64编码
     *
     * @param 要转的的pdf文件
     * @Author fuyuwei
     * Create Date: 2015年8月3日 下午9:52:30
     */
    public static String getPDfToBase4(InputStream inputStream) {
        BASE64Encoder encoder = new BASE64Encoder();
        //FileInputStream fin =null;
        BufferedInputStream bin = null;
        ByteArrayOutputStream baos = null;
        BufferedOutputStream bout = null;
        try {
            bin = new BufferedInputStream(inputStream);
            baos = new ByteArrayOutputStream();
            bout = new BufferedOutputStream(baos);
            byte[] buffer = new byte[1024];
            int len = bin.read(buffer);
            while (len != -1) {
                bout.write(buffer, 0, len);
                len = bin.read(buffer);
            }
            //刷新此输出流并强制写出所有缓冲的输出字节
            bout.flush();
            byte[] bytes = baos.toByteArray();
            return encoder.encodeBuffer(bytes).trim();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                //fin.close();
                bin.close();
                bout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


}
