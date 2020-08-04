package com.bookstore.service;

import com.bookstore.dao.UserDao;
import com.bookstore.dao.impl.UserDaoImpl;
import com.bookstore.domain.Page;
import com.bookstore.domain.User;


public class UserService {

    private static UserDao userDao = UserDaoImpl.getInstance();
    private static UserService userService = new UserService();

    private UserService() {}

    public static UserService getInstance() {
        return userService;
    }

    /**
     * 查询用户(分页)
     * @param index
     * @param size
     * @return
     */
    public Page<User> findUser(int index, int size) {
        Page<User> page = new Page<>();
        int begin = (index - 1) * size;
        page.setData(userDao.findUser(begin, size));
        page.setCount(userDao.getUserOfTotal());
        // 分页设置
        updatePage(page, index, size);
        return page;
    }

    /**
     * 设置分页
     * @param page
     * @param index
     * @param size
     */
    public void updatePage(Page<?> page, int index, int size) {
        // 当前页
        page.setCurr(index);
        // 总页数
        long total = page.getCount() % size == 0 ? page.getCount() / size : page.getCount() / size + 1;
        page.setTotal(total);
        // 起始页,结束页
        if (total <= 5) {
            // 小于5页直接返回
            page.setStart(1);
            page.setEnd(total);
        } else {
            long start = index - 2, end = index + 2;
            if (end > total) {
                long sub = end - total;
                start -= sub;
                end -= sub;
            }
            if (start < 1) {
                long add = 1 - start;
                start += add;
                end += add;
            }
            if (end > total) {
                end = total;
            }
            page.setStart(start);
            page.setEnd(end);
        }
    }
}
