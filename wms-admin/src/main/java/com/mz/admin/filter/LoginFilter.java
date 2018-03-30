package com.mz.admin.filter;

import com.mz.common.component.RedisCache;
import com.mz.common.util.SpringContextUtil;
import com.mz.common.util.WebTokenUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
public class LoginFilter implements Filter {

    private RedisCache redisCache = (RedisCache) SpringContextUtil.getBean("redisCache");

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) req;
        HttpServletResponse httpServletResponse = (HttpServletResponse) resp;
        if (httpServletRequest.getMethod().equals("OPTIONS")) {
            httpServletResponse.setStatus(200);
            chain.doFilter(req, resp);
            return;
        }
        String token = ((HttpServletRequest) req).getHeader("token");
        //System.out.println(redisCache);
        if (token != null) {
            Map<String, Object> map = WebTokenUtil.parserJavaWebToken(token);
            //System.out.println("map = >>>>>>>" + JSON.toJSONString(map));
            if (redisCache.getValue(token) != null) {//登陆成功
                //System.out.println("登陆成功" + redisCache.getValue(token).toString());
                chain.doFilter(req, resp);
            } else {
                httpServletResponse.sendError(401, "登陆失败，解析失败");
                return;
            }
        } else {
            httpServletResponse.sendError(401, "登陆失败,token为null");
            return;
        }
    }

    @Override
    public void destroy() {
    }


}