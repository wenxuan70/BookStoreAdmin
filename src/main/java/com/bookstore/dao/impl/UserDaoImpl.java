package com.bookstore.dao.impl;

import com.bookstore.dao.UserDao;
import com.bookstore.domain.User;
import com.bookstore.util.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class UserDaoImpl implements UserDao {

    private static final UserDao userDao = new UserDaoImpl();

    private UserDaoImpl() {}

    // 单例
    public static UserDao getInstance() {
        return userDao;
    }

    private QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());

    /*---查询用户相关方法---*/

    @Override
    public List<User> findAllUser(int begin, int count) {
        List<User> users = null;
        String sql = "select id,username,password,email,create_time from `user` limit ?,?;";
        try {
            users = queryRunner.query(sql, new UserListHandler(), begin, count);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public List<User> findAllUser(String query, int begin, int count) {
        String sql = "select id,username,password,email,create_time from `user` where username regexp ? limit ?,?";
        List<User> users = null;
        try {
            users = queryRunner.query(sql, new UserListHandler(), query, begin, count);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public User findUser(long id) {
        String sql = "select id, username, password, email, create_time from `user` where id = ?";
        User user = null;
        try {
            user = queryRunner.query(sql, new UserHandler(), id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User findUser(String username) {
        String sql = "select id, username, password, email, create_time from `user` where username = ?";
        User user = null;
        try {
            user = queryRunner.query(sql, new UserHandler(), username);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User login(String username, String password) {
        String sql = "select id,username,email,password,create_time from user where username = ? && password = password(?)";
        User user = null;
        try {
            user = queryRunner.query(sql, new UserHandler(), username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
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

    @Override
    public long getUserOfTotal(String query) {
        String sql = "select count(id) from `user` where username regexp ?";
        Long total = null;
        try {
            total = queryRunner.query(sql, new ScalarHandler<>(1), query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    /*---更新用户相关方法:事务管理---*/

    @Override
    public boolean addUser(String username, String password, String email, Date createTime) {
        String sql = "insert into `user`(username, password, email, create_time) value(?,password(?),?,?)";
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection();
            // 管理事务
            connection.setAutoCommit(false);
            int update = queryRunner.update(connection, sql, username, password, email, createTime);
            // 提交事务
            connection.commit();
            return update == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            // 回滚
            JDBCUtils.rollBack(connection);
        } finally {
            JDBCUtils.releaseResource(connection);
        }
        return false;
    }

    @Override
    public boolean updateUser(long id, String password, String email) {
        String sql = "update `user` set password = password(?), email = ? where id = ?";
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            // 管理事务
            conn.setAutoCommit(false);
            int update = queryRunner.update(conn, sql, password, email, id);
            // 提交事务
            conn.commit();
            return update == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            // 回滚
            JDBCUtils.rollBack(conn);
        } finally {
            JDBCUtils.releaseResource(conn);
        }
        return false;
    }

    @Override
    public boolean deleteUser(long id) {
        String sql = "delete from `user` where id = ?";
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            // 管理事务
            conn.setAutoCommit(false);
            int delete = queryRunner.update(conn, sql, id);
            // 提交
            conn.commit();
            return delete == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            // 回滚
            JDBCUtils.rollBack(conn);
        } finally {
            JDBCUtils.releaseResource(conn);
        }
        return false;
    }

    /*---辅助类---*/

    /**
     * 用户列表处理类
     */
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

    /**
     * 用户处理类
     */
    private static class UserHandler implements ResultSetHandler<User> {
        @Override
        public User handle(ResultSet rs) throws SQLException {
            User user = new User();
            if (rs.next()) {
                user.setId(rs.getLong("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setCreateTime(rs.getDate("create_time"));
            }
            return user;
        }
    }
}
