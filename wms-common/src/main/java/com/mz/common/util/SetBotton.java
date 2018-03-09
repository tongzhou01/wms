package com.mz.common.util;

import com.google.gson.Gson;
import com.mz.web.base.Button;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SetBotton {


    public static void main(String[] args) {

        Button botton1 = new Button();
        botton1.setName("11111");
        botton1.setType("view");
        botton1.setUrl("");
        //botton1.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxddd3ecf13e8fca82&redirect_uri=http://api.didalive.net/OAuth&response_type=code&scope=snsapi_userinfo&state=sfsdfsdsd13213241#wechat_redirect");

        Button botton2 = new Button();
        botton2.setName("寄件");
        botton2.setType("view");
        botton2.setUrl("http://guoji.didalive.net/wechat/#/send");

        Button botton3 = new Button();
        botton3.setName("个人中心");
        botton3.setType("view");
        botton3.setUrl("http://guoji.didalive.net/wechat/#/usercenter");


        List<Button> button = new ArrayList<Button>();
        button.add(botton1);
        button.add(botton2);
        button.add(botton3);

        Map<String, List<Button>> map = new HashMap<String, List<Button>>();
        map.put("button", button);

        Gson gson = new Gson();
        String json = gson.toJson(map);
        System.out.println(json);
        String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=RXNJ7rt-G1uWxX-6C-2h56SF-6bJw4JF2_Jdru3GwLsOpRPobdQCmcK1XealNp-KNPr0fZuzpv2HdsJTBM0uuVW0IoJF4lwUoT2q21IYlAhGziowNeZyvm2fUmKJksyoPNPcADAFDF";
        String result = HttpClientUtil.doPost(url, null, null, json);
        System.out.println(result);
    }

}
