package com.bookstore.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 异常处理器
 */
@WebServlet("/error")
public class ErrorHandler extends BasicController {

    private static final Logger log = LoggerFactory.getLogger(ErrorHandler.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String errorMsg = (String) req.getAttribute("javax.servlet.error.message");
        Integer code = (Integer) req.getAttribute("javax.servlet.error.status_code");
        String requestUri = (String) req.getAttribute("javax.servlet.error.request_uri");
        String servletName = (String) req.getAttribute("javax.servlet.error.servlet_name");
        Throwable exception  = (Throwable) req.getAttribute("javax.servlet.error.exception");
        if (code.equals(404)) {
            errorMsg = "页面走丢了T_T";
        }
        Map<String,Object> errorContent = new LinkedHashMap<>(4);
        errorContent.put("code",code);
        errorContent.put("requestUri",requestUri);
        errorContent.put("servletName",servletName);
        errorContent.put("exception",exception);
        log.info("错误处理信息: " + errorContent);
        writeJson(resp, errorContent);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }
}
