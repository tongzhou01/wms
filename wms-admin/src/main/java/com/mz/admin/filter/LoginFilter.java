package com.mz.admin.filter;

import com.mz.common.util.WebTokenUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

public class LoginFilter implements Filter {

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
        if (token != null) {
            Map<String, Object> map = WebTokenUtil.parserJavaWebToken(token);
            if (map != null) {//登陆成功
                if (map.get("createTime") != null) {
                    long createTime = Long.valueOf(map.get("createTime").toString());
                    long nowTime = new Date().getTime() - createTime;
                    long time = nowTime / (1000 * 60);
                    if (time <= 1440) {
                        chain.doFilter(req, resp);
                    } else {
                        httpServletResponse.sendError(500, "登陆失败,登录超时");
                        return;//登陆失败
                    }
                } else {
                    httpServletResponse.sendError(500, "登陆失败,登录超时");
                    return;//登陆失败
                }
            } else {
                httpServletResponse.sendError(500, "登陆失败，解析失败");
                return;
            }
        } else {
            httpServletResponse.sendError(500, "登陆失败");
            return;
        }
    }

    @Override
    public void destroy() {
    }


}