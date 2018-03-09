package com.mz.common.util;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.StringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.util.Map;


public class SignatureUtil {

    /**
     * 生成 package 字符串
     *
     * @param map         map
     * @param paternerKey paternerKey
     * @return package_str
     */
    public static String generatePackage(Map<String, String> map, String paternerKey) {
        String sign = generateSign(map, paternerKey);
        Map<String, String> tmap = MapUtil.order(map);
        String s2 = MapUtil.mapJoin(tmap, false, true);
        return s2 + "&sign=" + sign;
    }

    /**
     * 生成sign HMAC-SHA256和MD5 加密 toUpperCase
     *
     * @param map         map
     * @param paternerKey paternerKey
     * @return sign
     */
    public static String generateSign(Map<String, String> map, String paternerKey) {
        return generateSign(map, null, paternerKey);
    }

    /**
     * 生成sign HMAC-SHA256和MD5 加密 toUpperCase
     *
     * @param map         map
     * @param sign_type   HMAC-SHA256和MD5 加密 toUpperCase
     * @param paternerKey paternerKey
     * @return sign
     */
    public static String generateSign(Map<String, String> map, String sign_type, String paternerKey) {
        Map<String, String> tmap = MapUtil.order(map);
        if (tmap.containsKey("sign")) {
            tmap.remove("sign");
        }
        String str = MapUtil.mapJoin(tmap, false, false);
        if (sign_type == null) {
            sign_type = tmap.get("sign_type");
        }
        if ("HMAC-SHA256".equalsIgnoreCase(sign_type)) {
            try {
                Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
                SecretKeySpec secret_key = new SecretKeySpec(paternerKey.getBytes("UTF-8"), "HmacSHA256");
                sha256_HMAC.init(secret_key);
                return Hex.encodeHexString(sha256_HMAC.doFinal((str + "&key=" + paternerKey).getBytes("UTF-8"))).toUpperCase();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        } else {//default MD5
            return DigestUtils.md5Hex(str + "&key=" + paternerKey).toUpperCase();
        }
    }


    /**
     * 生成 paySign
     *
     * @param map        map
     * @param paySignKey paySignKey
     * @return pay sign
     */
    public static String generatePaySign(Map<String, String> map, String paySignKey) {
        if (paySignKey != null) {
            map.put("appkey", paySignKey);
        }
        Map<String, String> tmap = MapUtil.order(map);
        String str = MapUtil.mapJoin(tmap, true, false);
        return DigestUtils.shaHex(str);
    }


    /**
     * 生成事件消息接收签名
     *
     * @param token     token
     * @param timestamp timestamp
     * @param nonce     nonce
     * @return str
     */
    public static String generateEventMessageSignature(String token, String timestamp, String nonce) {
        String[] array = new String[]{token, timestamp, nonce};
        Arrays.sort(array);
        String s = StringUtils.arrayToDelimitedString(array, "");
        return DigestUtils.shaHex(s);
    }


    /**
     * mch 支付、代扣API调用签名验证
     *
     * @param map       参与签名的参数
     * @param sign_type sign_type
     * @param key       mch key
     * @return boolean
     */
    public static boolean validateSign(Map<String, String> map, String sign_type, String key) {
        if (map.get("sign") == null) {
            return false;
        }
        return map.get("sign").equals(generateSign(map, sign_type, key));
    }


}
