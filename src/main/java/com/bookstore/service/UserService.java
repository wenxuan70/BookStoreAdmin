package com.bookstore.service;

import com.bookstore.dao.UserDao;
import com.bookstore.dao.impl.UserDaoImpl;
import com.bookstore.domain.Page;
import com.bookstore.domain.User;

import java.util.Date;


public class UserService {

    private static final UserService userService = new UserService();

    public static UserService getInstance() {
        return userService;
    }

    private UserService() {}

    private UserDao userDao = UserDaoImpl.getInstance();

    /*---查询业务---*/

    /**
     * 查询用户(分页)
     * @param index
     * @param size
     * @return
     */
    public Page<User> findAllUser(int index, int size) {
        Page<User> page = new Page<>();
        int begin = (index - 1) * size;
        page.setData(userDao.findAllUser(begin, size));
        page.setCount(userDao.getUserOfTotal());
        // 分页设置
        page.updatePage(index, size);
        return page;
    }

    /**
     * 模糊查询---用户名(分页)
     * @param query
     * @param index
     * @param size
     * @return
     */
    public Page<User> findAllUser(String query, int index, int size) {
        Page<User> page = new Page<>();
        int begin = (index - 1) * size;
        page.setData(userDao.findAllUser(query, begin, size));
        page.setCount(userDao.getUserOfTotal(query));
        page.updatePage(index, size);
        return page;
    }

    /**
     * 查找用户
     * @param username
     * @return
     */
    public User findUser(String username) {
        return userDao.findUser(username);
    }

    /**
     * 判断是否存在该用户
     * @param id
     * @return
     */
    public boolean hasUser(long id) {
        return userDao.findUser(id).getId() != null;
    }

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    public User login(String username, String password) {
        return userDao.login(username, password);
    }

    /*---修改业务---*/

    /**
     * 添加用户
     * @param username
     * @param password
     * @param email
     * @return
     */
    public boolean addUser(String username, String password, String email) {
        return userDao.addUser(username, password, email, new Date());
    }

    /**
     * 更新用户
     * @param id
     * @param password
     * @param email
     * @return
     */
    public boolean updateUser(long id, String password, String email) {
        return userDao.updateUser(id, password, email);
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    public boolean deleteUser(long id) {
        return userDao.deleteUser(id);
    }

}
