package com.bookstore.web;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("*.html")
public class ViewController extends BasicController {

    @Override
    protected void initServlet(ServletConfig config) {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath();
        int index = servletPath.lastIndexOf('/');
        if (index != 0) {
            // 路径错误!
            throw new ServletException(servletPath + "路径错误");
        }
        String viewName = servletPath.substring(1, servletPath.length() - 5);
        System.out.format("servletPath => %s\n",servletPath);
        System.out.format("访问视图 => %s.html",viewName);
        renderTemplate(req, resp, viewName);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // ...
    }
}
