package com.bookstore.web;

import com.alibaba.fastjson.JSON;
import com.bookstore.domain.Page;
import com.bookstore.domain.User;
import com.bookstore.service.UserService;
import com.bookstore.util.StringUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user")
public class UserController extends BasicController {

    private UserService userService = UserService.getInstance();

    @Override
    protected void initServlet(ServletConfig config) {
        
    }

    /**
     * 获取用户
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String indexStr = req.getParameter("index");
        String sizeStr = req.getParameter("size");
        int index = 1, size = 1;
        if (StringUtils.notEmpty(indexStr))
            index = Integer.parseInt(indexStr);
        if (StringUtils.notEmpty(sizeStr))
            size = Integer.parseInt(sizeStr);
        Page<User> page = userService.findUser(index, size);
        renderTemplate(req, resp, "user", "page", page);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
