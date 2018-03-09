package com.mz.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.mz.web.base.TaskRequest;
import com.mz.web.base.TaskResponse;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;


public class OrderUtil {

    private static Logger logger = Logger.getLogger(OrderUtil.class);
    public static final String KEY_TEST = "AEFE95473768854EC7";
    public static final String COMPANY_ID_TEST = "MZDQJ1308307E108";
    public static final String URL_ADD_TEST = "http://intltest.zto.cn/api/Order/init";
    public static final String URL_QUERY_TEST = "http://intltest.zto.cn/api/Query/init";
    public static final String MSG_TYPE_QUERY = "zto.logisticsInfo.select";
    public static final String MSG_TYPE_ADD = "export.order.insert";
    public static final String MSG_TYPE_GETNO = "zto.intl.logisticno.giveout";
    /*正式环境*/
    public static final String KEY = "3D4CA918BCF52CDADC";
    public static final String COMPANY_ID = "MZ0923537E10825";
    public static final String URL_ADD = "https://gjapi.zt-express.com/api/Order/init";
    public static final String URL_QUERY = "https://gjapi.zt-express.com/api/Query/init";
    /*快递100参数
     * 实时查询接口：
  （1）key:nsIHKufx4718
  （2）customer :9A2008F5D5D3B89B2FD6DD4FDDB13BE0*/
    public static final String KUAIDI100_URL_QUERY = "http://poll.kuaidi100.com/poll/query.do";
    public static final String KUAIDI100_KEY = "nsIHKufx4718";
    public static final String KUAIDI100_CUSTOMER = "9A2008F5D5D3B89B2FD6DD4FDDB13BE0";
    public static final String KUAIDI100_CALLBACKURL = "http://api.mingz-tech.com/kd/callback";
    public static final String KUAIDI100_URL_POLL = "http://www.kuaidi100.com/poll";


    /**
     * 中通新增订单
     */
    public static String createOrder(String data) {
        //订单信息处理
        String httpResult = null;
        //下单并获取中通订单号
        Map<String, String> map = new HashMap<String, String>();
        map.put("data", data);
        map.put("msg_type", MSG_TYPE_ADD);
        map.put("data_digest", Md5Helper.md5(data + KEY));
        map.put("company_id", COMPANY_ID);

        try {
            httpResult = HttpClientUtil.doPost(URL_ADD, null, map, null);

        } catch (JSONException e) {
            logger.info("json转换异常", e);
        }
        return httpResult;
    }

    /**
     * 中通单号跟踪信息查询
     */
    public static String getOrderInfo(String logisticsId) {
        Map<String, String> map = new HashMap<String, String>();
        String data = "{\"logisticsId\":\"" + logisticsId + "\"}";
        map.put("data", data);
        map.put("msg_type", MSG_TYPE_QUERY);
        map.put("data_digest", Md5Helper.md5(data + KEY));
        map.put("company_id", COMPANY_ID);
        String httpResult = "";
        try {
            httpResult = HttpClientUtil.doGet(URL_QUERY, null, map);
        } catch (Exception e) {
            logger.info("中通单号跟踪信息查询异常", e);
        }
        //System.out.println("httpResult==============>>>>>>>>>>>"+httpResult);
        return httpResult;
    }

    /**
     * 中通单号批量获取
     */
    public static String getZTONo(String data) {
        Map<String, String> map = new HashMap<String, String>();
        //List<String> list = new ArrayList<>();
        //String data="{\"logisticsId\":\""+logisticsId+"\"}";
        map.put("data", data);
        map.put("msg_type", MSG_TYPE_GETNO);
        map.put("data_digest", Md5Helper.md5(data + KEY));
        map.put("company_id", COMPANY_ID);
        String httpResult = "";
        httpResult = HttpClientUtil.doPost(URL_ADD, null, map, null);
        System.out.println("getZTONo===>>>>>>" + httpResult);
        return httpResult;
    }

    /**
     * 快递100订单信息查询
     */
    public static String kuaidi100(String company, String num) {
        HashMap<String, String> params = new HashMap<String, String>();
        String param = "{\"com\":\"" + company + "\",\"num\":\"" + num + "\"}";

        String customer = KUAIDI100_CUSTOMER;
        String key = KUAIDI100_KEY;
        String sign = "";
        try {
            sign = MD5Util.MD5Encode(param + key + customer, "UTF-8").toUpperCase();
            logger.info(sign);
        } catch (Exception e1) {
            logger.info("MD5加密出错", e1);
        }
        params.put("param", param);
        params.put("sign", sign);
        params.put("customer", customer);
        String httpResult = "";
        try {
            httpResult = HttpClientUtil.doGet(KUAIDI100_URL_QUERY, null, params);
        } catch (Exception e) {
            logger.info("快递100信息查询异常", e);
        }
        return httpResult;
    }

    /**
     * 快递100订阅
     *
     * @param company     公司
     * @param number      单号
     * @param from        出发地城市，省-市-区，非必填
     * @param to          目的地城市，省-市-区，非必填
     * @param callBackUrl
     */
    public static void Kuaidi100Poll(String company, String number, String from, String to, String param) {
        TaskRequest req = new TaskRequest();
        req.setCompany(company);
        if (from != null) {
            req.setFrom(from);
        }
        if (to != null) {
            req.setTo(to);
        }
        HashMap<String, String> parameters = req.getParameters();
        req.setNumber(number);
        parameters.put("callbackurl", KUAIDI100_CALLBACKURL + param);
        parameters.put("interCom", "1");
        parameters.put("autoCom", "1");
        req.setKey(KUAIDI100_KEY);
        req.setParameters(parameters);
        HashMap<String, String> p = new HashMap<String, String>();
        p.put("schema", "json");
        p.put("param", JSON.toJSONString(req));
        //System.out.println("param========="+JSON.toJSONString(req));
        try {
            String result = HttpClientUtil.doPost(KUAIDI100_URL_POLL, null, p, null);
            TaskResponse taskResponse = JSONObject.parseObject(result, TaskResponse.class);
            if (taskResponse.getResult() == true) {
                logger.info(taskResponse.getReturnCode() + " - 快递100推送订阅成功");
            } else {
                logger.info(taskResponse.getReturnCode() + " - 快递100推送订阅失败");
            }
        } catch (Exception e) {
            logger.info(" - 快递100推送订阅异常");
        }
    }

}
