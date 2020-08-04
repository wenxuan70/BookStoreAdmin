package com.bookstore.dao.impl;

import com.bookstore.dao.UserDao;
import com.bookstore.domain.User;
import com.bookstore.util.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UserDaoImpl implements UserDao {

    private static UserDao userDao = new UserDaoImpl();

    private UserDaoImpl() {}

    // 单例
    public static UserDao getInstance() {
        return userDao;
    }

    private QueryRunner queryRunner = new QueryRunner(
            DataSourceUtils.getDataSource());

    @Override
    public List<User> findUser(int begin, int count) {
        List<User> users = new ArrayList<>();
        String sql = "select id,username,password,email,create_time from `user` limit ?,?;";
        try {
            users = queryRunner.query(sql, new UserListHandler(), begin, count);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public long getUserOfTotal() {
        String sql = "select count(id) from `user`;";
        Long total = null;
        try {
            total = queryRunner.query(sql, new ScalarHandler<>(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    private static class UserListHandler implements ResultSetHandler<List<User>> {

        @Override
        public List<User> handle(ResultSet rs) throws SQLException {
            List<User> userList = new ArrayList<>();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setCreateTime(rs.getDate("create_time"));
                userList.add(user);
            }
            return userList;
        }
    }

}
