package com.bookstore.web;


import com.bookstore.domain.User;
import com.bookstore.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet("/login")
public class LoginController extends BasicController {
    private static final int WEEK = 7 * 24 * 3600;

    private UserService userService = UserService.getInstance();

    /**
     * 跳转到登录页面
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (req.getSession().getAttribute("user") != null) {
            // 已登录, 重定向到首页, 防止已登录用户再次登录
            resp.sendRedirect("/index");
            return;
        }
        // 跳转到登录页
        renderTemplate(req, resp, "login");
    }

    /**
     * 登录
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String username = req.getParameter("username"),
                password = req.getParameter("password");
        User user = userService.login(username, password);
        if (user.getId() == null) {
            renderTemplate(req, resp, "login", "errorMsg", "用户名或密码错误");
        } else {
            // 保持登录状态
            HttpSession session = req.getSession();
            session.setAttribute("user", user.getUsername());
            session.setMaxInactiveInterval(WEEK);
            // 跳转到首页
            resp.sendRedirect("/index");
        }
    }
}
