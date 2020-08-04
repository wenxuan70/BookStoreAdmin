package com.bookstore.web;


import javax.servlet.*;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;

@WebFilter(urlPatterns = "/*", initParams = {
        @WebInitParam(name = "oldEncode", value = "iso-8859-1"),
        @WebInitParam(name = "newEncode", value = "utf-8")
})
public class EncodeFilter implements Filter {

    private static String oldEncode;
    private static String newEncode;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        oldEncode = filterConfig.getInitParameter("oldEncode");
        newEncode = filterConfig.getInitParameter("newEncode");

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        // 动态代理
        HttpServletRequest encodeReq = (HttpServletRequest) Proxy.newProxyInstance(req.getClass().getClassLoader(),
                new Class[]{HttpServletRequest.class},
                new EncodeRequest(req));
        // 设置响应数据的编码方式
        response.setCharacterEncoding(newEncode);
        chain.doFilter(encodeReq, response);
    }

    @Override
    public void destroy() {

    }

    /**
     * 转换编码方式
     * @param old
     * @return
     * @throws UnsupportedEncodingException
     */
    private static String convertEncode(String old) throws UnsupportedEncodingException {
        if (old == null)
            return null;
        return new String(old.getBytes(oldEncode), newEncode);
    }

    /**
     * 封装后的HttpServletRequest
     * 解决获取的参数乱码问题
     */
    private static class EncodeRequest implements InvocationHandler {

        private HttpServletRequest req;


        public EncodeRequest(HttpServletRequest req) {
            this.req = req;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            switch (method.getName()) {
                case "getParameter": {
                    String oldValue = ((String) method.invoke(req, args));
                    return convertEncode(oldValue);
                }
                case "getParameterNames": {
                    Enumeration<String> oldValue = (Enumeration<String>) method.invoke(req, args);
                    List<String> newValue = new ArrayList<>();
                    while (oldValue.hasMoreElements()) {
                       newValue.add(oldValue.nextElement());
                    }
                    return Collections.enumeration(newValue);
                }
                case "getParameterValues": {
                    String[] returnValue = (String[]) method.invoke(req, args);
                    for (int i = 0; i < returnValue.length; i++) {
                        returnValue[i] = convertEncode(returnValue[i]);
                    }
                    return returnValue;
                }
                case "getParameterMap": {
                    Map<String, String[]> returnValue = (Map<String, String[]>) method.invoke(req, args);
                    for (String s : returnValue.keySet()) {
                        String[] sArray = returnValue.get(s);
                        for (int i = 0; i < sArray.length; i++) {
                            sArray[i] = convertEncode(sArray[i]);
                        }
                    }
                    return returnValue;
                }
                default: {
                    return method.invoke(req, args);
                }
            }
        }
    }
}
