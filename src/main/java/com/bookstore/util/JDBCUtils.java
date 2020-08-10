package com.bookstore.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCUtils {

    private static DataSource ds;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            // 加载数据库配置
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            InputStream is = cl.getResourceAsStream("jdbc.properties");
            Properties jdbcProp = new Properties();
            jdbcProp.load(is);
            ds = DruidDataSourceFactory.createDataSource(jdbcProp);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static DataSource getDataSource() {
        return ds;
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public static void releaseResource(Connection conn) {
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void rollBack(Connection conn) {
        try {
            if (conn != null)
                conn.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private JDBCUtils() {}
}
