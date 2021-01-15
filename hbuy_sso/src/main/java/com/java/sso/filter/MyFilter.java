package com.java.sso.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * author:快乐风男
 * time:9:30
 */
@WebFilter(urlPatterns = "/*")  //表示所有的请求均要通过此过滤器
public class MyFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("过滤器的初始化init方法被执行了。。。");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("过滤器的核心doFilter方法被执行了。。。");
        //得到HTTP请求响应对象
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        //设置请求响应的字符编码
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        //设置响应正文的字符编码
        response.setContentType("text/templates.html;charset=UTF-8");
        //允许跨域访问
        // 设置允许多个域名请求的数组
        String[] allowDomains = {"http://localhost:8083","http://localhost:6060","http://localhost:8092","http://localhost:8093","http://localhost:8086"};
        //将数组存入set集合
        Set allowOrigins = new HashSet(Arrays.asList(allowDomains));
        //origin主要是用来说明最初请求是从哪里发起的；origin只用于Post请求，而Referer则用于所有类型的请求；
        String originHeads = request.getHeader("Origin");
        if (allowOrigins.contains(originHeads)){
            //设置允许跨域的配置
            // 这里填写你允许进行跨域的主机ip（正式上线时可以动态配置具体允许的域名和IP）
            response.setHeader("Access-Control-Allow-Origin",originHeads);// 设置允许跨域访问的路径
        }
        // response.setHeader("Access-Control-Allow-Origin", "http://localhost:8084"); // 设置允许所有跨域访问
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,PUT,OPTIONS,DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin,X-Requested-With,Content-Type,Accept,Authorization,token");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        //放行
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {
        System.out.println("过滤器的销毁destroy方法被执行了。。。");
    }
}
