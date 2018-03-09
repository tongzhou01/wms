package com.mz.common.util;


import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.*;

;
;


/**
 * 通用httpclient工具类
 * 包含get请求和post请求
 * httpclient4.3.5
 *
 * @author
 */
public class SSLHttpsClientUtil {

    private static final String CHARSET = "UTF-8";
    private static final String KEYSTORE_TYPE = "PKCS12";

    private static CloseableHttpClient getCloseableHttpClient(String certPath, String password) {
        FileInputStream instream = null;
        try {
            KeyStore keyStore = KeyStore.getInstance(KEYSTORE_TYPE);
            instream = new FileInputStream(new File(certPath));
            keyStore.load(instream, password.toCharArray());
            SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, password.toCharArray()).build();
            // Allow TLSv1 protocol only
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslcontext,
                    new String[]{"TLSv1"},
                    null,
                    SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
            CloseableHttpClient httpClient = HttpClients.custom()
                    .setSSLSocketFactory(sslsf)
                    .build();
            return httpClient;
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } finally {
            try {
                instream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String doPost(String certPath, String password, String url, Map<String, String> headers, Map<String, String> params, String body) {
        return doPost(certPath, password, url, headers, params, body, CHARSET);
    }

    /**
     * HTTP Post 获取内容(支持https)
     *
     * @param url     请求的url地址 ?之前的地址
     * @param params  请求的参数
     * @param charset 编码格式
     * @return 页面内容
     */
    public static String doPost(String certPath, String password, String url, Map<String, String> headers, Map<String, String> params, String body, String charset) {
        if (StringUtils.isBlank(url)) {
            return null;
        }

        try {
            List<NameValuePair> pairs = null;
            if (params != null && !params.isEmpty()) {
                pairs = new ArrayList<NameValuePair>(params.size());
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    String value = entry.getValue();
                    if (value != null) {
                        pairs.add(new BasicNameValuePair(entry.getKey(), value));
                    }
                }
            }
            HttpPost httpPost = new HttpPost(url);
            if (headers != null && !headers.isEmpty()) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    String value = entry.getValue();
                    if (value != null) {
                        httpPost.setHeader(entry.getKey(), value);
                    }
                }
            }
            if (StringUtils.isNotEmpty(body)) {
                httpPost.setEntity(new StringEntity(body, charset));
            }
            if (pairs != null && pairs.size() > 0) {
                httpPost.setEntity(new UrlEncodedFormEntity(pairs, charset));
            }
            CloseableHttpResponse response = getCloseableHttpClient(certPath, password).execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpPost.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, "utf-8");
            }
            EntityUtils.consume(entity);
            response.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        String url = "https://api.mch.weixin.qq.com/mmpaymkttransfers/send_coupon";
        Map<String, String> map = new TreeMap<>();
        map.put("appid", "wxca9a2455f3bb44b2");
        map.put("coupon_stock_id", "3258202");
        map.put("mch_id", "1487952172");
        map.put("nonce_str", WXPayUtil.generateNonceStr());
        map.put("openid", "oPg2ZwgwuALccM_V8UIW4qmhkOwo");
        //map.put("openid", "oPg2ZwiH1ASA_EiAj10XHcB2qgcM");
        map.put("openid_count", "1");
        map.put("partner_trade_no", "14879521722" + DateUtil.getDateFormat(new Date(), "yyyyMMdd") + new Random().nextInt(8999) + 1000);
        String sign = WXPayUtil.generateSignature(map, "guoji007mingztechco8909isjski182");
        map.put("sign", sign);
        String body = WXPayUtil.mapToXml(map);
        System.out.println("body=" + body);
        String str = doPost("D:/CERT/common/apiclient_cert.p12", "1487952172", url, null, null, body);
        System.out.println("result=" + str);

        //String result_code = jsonObject.get("result_code").toString();


    }

}
