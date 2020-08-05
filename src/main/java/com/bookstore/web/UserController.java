package com.bookstore.web;

import com.bookstore.domain.Page;
import com.bookstore.domain.ResponseContent;
import com.bookstore.domain.User;
import com.bookstore.service.UserService;
import com.bookstore.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user")
public class UserController extends BasicController {

    private UserService userService = UserService.getInstance();
    // 验证规则
    private static final String USERNAME_VALIDATION = "^([\\u4E00-\\u9FA5]|\\w){2,16}$";
    private static final String PASSWORD_VALIDATION = "^\\w{6,16}$";
    private static final String EMAIL_VALIDATION = "^\\w+@\\w+\\.\\w+$";

    /**
     * 跳转到user页面
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String indexStr = req.getParameter("index");
        String sizeStr = req.getParameter("size");
        String query = req.getParameter("q");
        int index = 1, size = 10;
        if (StringUtils.notEmpty(indexStr))
            index = Integer.parseInt(indexStr);
        if (StringUtils.notEmpty(sizeStr))
            size = Integer.parseInt(sizeStr);
        Page<User> page;

        if (StringUtils.isEmpty(query)) {
            page = userService.findAllUser(index, size);
        } else {
            page = userService.findAllUser(query, index, size);
        }
        String[] paramsName = {"page", "q"};
        Object[] params = {page, query};
        renderTemplate(req, resp, "user", paramsName, params);
    }

    /**
     * 添加用户
     * 请求参数: username, password, email
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username"),
                password = req.getParameter("password"),
                email = req.getParameter("email");

        // 验证
        if (!valid(username, USERNAME_VALIDATION)) {
            ResponseContent rs = ResponseContent.isError("用户名格式错误");
            writeJson(resp, rs);
            return;
        }
        if (!valid(password, PASSWORD_VALIDATION)) {
            ResponseContent rs = ResponseContent.isError("密码格式错误");
            writeJson(resp, rs);
            return;
        }
        if (!valid(email, EMAIL_VALIDATION)) {
            ResponseContent rs = ResponseContent.isError("邮箱格式错误");
            writeJson(resp, rs);
            return;
        }

        ResponseContent rs = userService.addUser(username, password, email) ?
                ResponseContent.isOk("添加成功") :
                ResponseContent.isError("添加失败");
        writeJson(resp, rs);
    }

    /**
     * 修改用户资料
     * 请求参数: id, password, email
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id"),
                password = req.getParameter("password"),
                email = req.getParameter("email");
        if (idStr == null) {
            ResponseContent rs = ResponseContent.isError("参数id不能为空");
            writeJson(resp, rs);
            return;
        }

        long id = Long.parseLong(idStr);

        if (!userService.hasUser(id)) {
            ResponseContent rs = ResponseContent.isError("不存在id为" + id + "的用户");
            writeJson(resp, rs);
            return;
        }

        if (!valid(password, PASSWORD_VALIDATION)) {
            ResponseContent rs = ResponseContent.isError("密码格式错误");
            writeJson(resp, rs);
            return;
        }
        if (!valid(email, EMAIL_VALIDATION)) {
            ResponseContent rs = ResponseContent.isError("邮箱格式错误");
            writeJson(resp, rs);
            return;
        }

        ResponseContent rs = userService.updateUser(id, password, email) ?
                ResponseContent.isOk("修改成功") :
                ResponseContent.isError("修改失败");
        writeJson(resp, rs);
    }

    /**
     * 删除用户
     * 请求参数: id
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        if (idStr == null) {
            ResponseContent rs = ResponseContent.isError("参数id不能为空");
            writeJson(resp, rs);
            return;
        }
        long id = Long.parseLong(idStr);
        if (!userService.hasUser(id)) {
            ResponseContent rs = ResponseContent.isError("不存在id为" + id + "的用户");
            writeJson(resp, rs);
            return;
        }

        ResponseContent rs = userService.deleteUser(id) ?
                ResponseContent.isOk("删除成功") :
                ResponseContent.isError("删除失败");
        writeJson(resp, rs);
    }

    /*---辅助函数---*/

    /**
     * 验证数据
     *
     * @param data
     * @param way
     * @return
     */
    public boolean valid(String data, String way) {
        return StringUtils.notEmpty(data) && data.matches(way);
    }
}
