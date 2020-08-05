package com.bookstore.dao;

import com.bookstore.domain.User;

import java.util.Date;
import java.util.List;

public interface UserDao {

    /*---查询用户相关方法---*/

    List<User> findAllUser(int begin, int count);

    List<User> findAllUser(String query, int begin, int count);

    User findUser(long id);

    User findUser(String username);

    User login(String username, String password);

    long getUserOfTotal(String query);

    long getUserOfTotal();

    /*---更新用户相关方法---*/

    boolean addUser(String username, String password, String email, Date createTime);

    boolean updateUser(long id, String password, String email);

    boolean deleteUser(long id);
}
