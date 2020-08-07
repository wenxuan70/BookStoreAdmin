package com.bookstore.web;

import com.bookstore.domain.ResponseContent;
import com.bookstore.domain.User;
import com.bookstore.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/info")
public class InfoController extends BasicController {

    private UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = (String) req.getSession().getAttribute("user");
        if (username == null) {
            ResponseContent rs = ResponseContent.isError("你还未登录");
            writeJson(resp, rs);
            return;
        }
        User user = userService.findUser(username);
        renderTemplate(req, resp, "info", "user", user);
    }
}
