package com.bookstore.web;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录过滤器
 */
@WebFilter(urlPatterns = "/*")
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig)
            throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String path = req.getServletPath();

        //测试接口暂时放行
        if (path.startsWith("/order")) {
            chain.doFilter(request, response);
            return;
        }

        if (path.startsWith("/login") || path.startsWith("/css") || path.startsWith("/image") ||
                path.startsWith("/js") || path.startsWith("/upload")) {
            // 放行静态资源和登录页面
            chain.doFilter(request, response);
            return;
        }

        if (req.getSession().getAttribute("user") == null) {
            // 没有登录,重定向到登录页
            resp.sendRedirect("/login");
        } else {
            chain.doFilter(request, response);
        }

    }

    @Override
    public void destroy() {

    }
}
