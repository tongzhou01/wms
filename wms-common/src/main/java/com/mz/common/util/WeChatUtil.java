package com.mz.common.util;

import com.mz.web.base.JsApiTicket;
import com.mz.web.base.Token;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;

public class WeChatUtil extends BaseAPI {


    /**
     * 获取access_token
     *
     * @param appid  appid
     * @param secret secret
     * @return Token
     */
    public static Token token(String appid, String secret) {
        HttpUriRequest httpUriRequest = RequestBuilder.get()
                .setUri(BASE_URI + "/cgi-bin/token")
                .addParameter("grant_type", "client_credential")
                .addParameter("appid", appid)
                .addParameter("secret", secret)
                .build();
        return LocalHttpClient.executeJsonResult(httpUriRequest, Token.class);
    }

    /***
     * 获取jsapiTicket
     * @return
     */
    public static JsApiTicket getJSApiTicket(String access_token) {
        //获取token
        HttpUriRequest httpUriRequest = RequestBuilder.get()
                .setUri(BASE_URI + "/cgi-bin/ticket/getticket")
                .addParameter("type", "jsapi")
                .addParameter("access_token", access_token)
                .build();
        return LocalHttpClient.executeJsonResult(httpUriRequest, JsApiTicket.class);

    }
}
