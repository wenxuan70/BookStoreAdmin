package com.bookstore.web;

import com.bookstore.domain.User;
import com.bookstore.service.UserService;
import com.bookstore.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/info")
public class InfoController extends BasicController {

    private UserService userService = UserService.getInstance();

    /**
     * 个人信息页
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String username = (String) req.getSession().getAttribute("user");
        if (username == null) {
            writeErrorMsg(resp, "你还未登录");
            return;
        }
        User user = userService.findUser(username);
        renderTemplate(req, resp, "info", "user", user);
    }

    /**
     * 修改用户信息
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String username = (String) req.getSession().getAttribute("user");
        if (username == null) {
            writeErrorMsg(resp, "你还未登录");
            return;
        }
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        if (StringUtils.isEmpty(password) || !password.matches("^\\w{6,16}$")) {
            writeErrorMsg(resp, "密码不能为空");
            return;
        } else if (StringUtils.isEmpty(email) || !email.matches("^\\w+@\\w+\\.\\w+$")) {
            writeErrorMsg(resp, "邮箱格式不正确");
            return;
        }
        // 获取用户信息
        User user = userService.findUser(username);
        // 修改密码和邮箱
        boolean success = userService.updateUser(user.getId(), password, email);
        if (success) {
            // 修改成功后退出登录
            req.getSession().invalidate();

            writeOkMsg(resp, "修改成功");
        } else {
            writeErrorMsg(resp, "修改失败");
        }
    }
}
