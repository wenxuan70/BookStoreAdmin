package com.bookstore.web;

import com.bookstore.domain.ResponseContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 异常处理器
 */
@WebServlet("/error")
public class ErrorHandler extends BasicController {

    private static final Logger log = LoggerFactory.getLogger(ErrorHandler.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String errorMsg = (String) req.getAttribute("javax.servlet.error.message");
        Integer code = (Integer) req.getAttribute("javax.servlet.error.status_code");
        if (code.equals(404)) {
            errorMsg = "页面走丢了T_T";
        }
        log.info("错误信息: " + errorMsg);
        ResponseContent rs = new ResponseContent(code, errorMsg);
        writeJson(resp, rs);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
