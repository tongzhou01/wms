package com.mz.common.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * sql查询数据封装
 *
 * @author tongzhou
 * @date 2018-03-13 11:34
 **/
public class QueryParam extends HashMap<String, Object> {

    public QueryParam() {
        super();
    }

    public QueryParam(Map<String, Object> params) {
        this.putAll(params);
    }
}
