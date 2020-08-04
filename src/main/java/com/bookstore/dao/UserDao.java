package com.bookstore.dao;

import com.bookstore.domain.User;

import java.util.List;

public interface UserDao {

    List<User> findUser(int begin, int count);

    long getUserOfTotal();
}
