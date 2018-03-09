package com.mz.common.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Stack;

public class Util {
    /**
     * 大于注册发送验证码
     *
     * @param ranCode
     * @param phone
     * @param smsID
     * @throws Exception
     */
    public static void ranCodeSend(String ranCode, String phone) throws Exception {
        String url = "http://gw.api.taobao.com/router/rest";// 大鱼平台发送短信请求地址
        //String url = "http://baidu.com";// 大鱼平台发送短信请求地址
        String appkey = "23438640";// 发送短信所需要的appkey23438640
        String secret = "0bb516820676d4d01bed55282bf48820";// 与appkey对应的密码
        TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        req.setExtend("");
        req.setSmsType("normal");
        req.setSmsFreeSignName("明彰");
        req.setSmsParamString("{code:'" + ranCode + "',product:'明彰'}");
        req.setRecNum(phone);
        req.setSmsTemplateCode("SMS_13230957");//SMS_14245647：您好，你的验证码为${code}，请不要告诉任何人！
        AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
//		System.out.println(rsp.getBody());
//		JSONObject obj=JSON.parseObject(rsp.getBody());
//		System.out.println(obj.get("error_response"));
        /*JSONObject obj1=JSON.parseObject(obj.get("error_response")+"");
		if(obj1.get("sub_msg").equals("触发业务流控")){
			System.out.println(" 短 信 发 送 成 功 ");
		}else{
			System.out.println(obj1.get("sub_msg"));
		}*/
    }

    /**
     * 发送短信验证码
     *
     * @param mobile
     * @return
     */
    public static String MessageCode(String mobile) {
        String ranCode = "false";
        ranCode = "" + (Math.random() * 9000 + 1000);
        ranCode = ranCode.substring(0, 4);
        try {
            System.out.println("短 信 验 证 码 是 ：" + ranCode);
            ranCodeSend(ranCode, mobile);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ranCode;
    }

    /**
     * 冒泡排序 int[] a={1,4,2,7,5,9,6};
     *
     * @param a
     * @return
     */
    public static int[] sort(int[] a) {
        for (int i = 0; i < a.length; i++) {
            for (int j = i; j < a.length; j++) {
                if (a[i] > a[j]) {
                    int temp = a[i];
                    a[i] = a[j];
                    a[j] = temp;
                }
            }
        }
        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i]);
        }

        return a;
    }

    /**
     * 将json字符串转换为map
     *
     * @param data
     * @return
     */
    public static Map<String, Object> jsonmap(String data) {
        GsonBuilder gb = new GsonBuilder();
        Gson g = gb.create();
        Map<String, Object> map = g.fromJson(data, new TypeToken<Map<String, Object>>() {
        }.getType());
        return map;
    }

    public static Map<String, String> jsonStr(String data) {
        GsonBuilder gb = new GsonBuilder();
        Gson g = gb.create();
        Map<String, String> map = g.fromJson(data, new TypeToken<Map<String, Object>>() {
        }.getType());
        return map;
    }

    /**
     * 获取当前时间的毫秒数（13位时间戳）String
     *
     * @return
     */
    public static String TheCurrentTime() {
        String time = "";
        time = System.currentTimeMillis() + "";
        return time;
    }

    public static final char[] array = {'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's', 'd', 'f', 'g', 'h',
            'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'Q',
            'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P', 'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'Z', 'X', 'C',
            'V', 'B', 'N', 'M'};

    /**
     * 手机号转邀请码
     *
     * @param number
     * @return
     */
    public static String _10_to_62(long number) {
        Long rest = number;
        Stack<Character> stack = new Stack<Character>();
        StringBuilder result = new StringBuilder(0);
        while (rest != 0) {
            stack.add(array[new Long((rest - (rest / 62) * 62)).intValue()]);
            rest = rest / 62;
        }
        for (; !stack.isEmpty(); ) {
            result.append(stack.pop());
        }
        return result.toString();
    }

    /**
     * 根据输入模板从13位时间戳转化时间 toDate(1468377253000L, "YYYY/MM/dd HH:mm:ss")
     *
     * @param time    13位时间戳
     * @param formate 时间格式"yy/MM/dd h:m:s"
     * @return
     */
    public static String toDate(Long time, String formate) {
        SimpleDateFormat sdf = new SimpleDateFormat(formate);
        String date = sdf.format(new Date(time));
        return date;
    }

    // sha1加密
    public static String sha1Encrypt(String Passwd) throws Exception {
        MessageDigest alg = MessageDigest.getInstance("SHA-1");
        alg.update(Passwd.getBytes());
        byte[] bts = alg.digest();
        String result = "";
        String tmp = "";
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1)
                result += "0";
            result += tmp;
        }
        return result;
    }

    /***
     * MD5加密 生成32位md5码
     * @param 待加密字符串
     * @return 返回32位md5码
     */
    public static String md5Encode(String inStr) throws Exception {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }

        byte[] byteArray = inStr.getBytes("UTF-8");
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }


}
