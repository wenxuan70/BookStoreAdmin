package com.bookstore.util;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;

import java.util.Properties;

public class DataSourceUtils {

    private static DataSource ds;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            // 加载数据库配置
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            InputStream is = cl.getResourceAsStream("jdbc.properties");
            Properties jdbcProp = new Properties();
            jdbcProp.load(is);
            MysqlDataSource mds = new MysqlDataSource();
            mds.setURL(jdbcProp.getProperty("url"));
            mds.setUser(jdbcProp.getProperty("username"));
            mds.setPassword(jdbcProp.getProperty("password"));

            ds = mds;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static DataSource getDataSource() {
        return ds;
    }
}
