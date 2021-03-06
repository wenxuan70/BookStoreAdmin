package com.bookstore.dao.impl;

import com.bookstore.dao.BookDao;
import com.bookstore.domain.Book;
import com.bookstore.util.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookDaoImpl implements BookDao {

    private static final Logger log = LoggerFactory.getLogger(BookDaoImpl.class);

    private static final BookDao bookDao = new BookDaoImpl();

    public static BookDao getInstance() {
        return bookDao;
    }

    private BookDaoImpl() {}

    private QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());

    /*---查询书籍相关方法---*/

    @Override
    public List<Book> findAllBook(int begin, int count) {
        String sql = "SELECT b.id as id, b.title as title, b.`desc` as `desc`, b.author as author, " +
                " b.publisher as publisher, b.publish_date as publish_date,b.price as price, " +
                " b.sort as sort, i.url as url " +
                " FROM " +
                " book as b " +
                " LEFT JOIN book_img AS i ON b.id = i.book_id " +
                " LIMIT ?, ?";
        List<Book> books = Collections.emptyList();
        try {
            books = queryRunner.query(sql, new BookListHandler(), begin, count);
        } catch (SQLException e) {
            log.error("BookDaoImpl的findAllBook方法异常",e);
        }
        return books;
    }

    @Override
    public List<Book> findAllBook(String query, int begin, int count) {
        String sql = "SELECT b.id as id, b.title as title, b.`desc` as `desc`, b.author as author, " +
                " b.publisher as publisher, b.publish_date as publish_date,b.price as price, " +
                " b.sort as sort, i.url as url " +
                " FROM " +
                " book as b " +
                " LEFT JOIN book_img AS i ON b.id = i.book_id " +
                " WHERE b.title REGEXP ? " +
                " LIMIT ?, ?";
        List<Book> books = Collections.emptyList();
        try {
            books = queryRunner.query(sql, new BookListHandler(), query, begin, count);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public List<String> findAllSort() {
        String sql = "select sort_name from sort";
        List<String> sorts = Collections.emptyList();
        try {
            sorts = queryRunner.query(sql, new BeanListHandler<String>(String.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sorts;
    }

    @Override
    public Integer findSortId(String sort) {
        String sql = "select id from sort where sort_name = ?";
        Integer id = null;
        try {
            id = queryRunner.query(sql,new ScalarHandler<>(), sort);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public long getTotal() {
        String sql = "select count(id) from book";
        Long total = null;
        try {
            total = queryRunner.query(sql,new ScalarHandler<>());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    @Override
    public long getTotal(String query) {
        String sql = "select count(id) from book where title regexp ?";
        Long total = null;
        try {
            total = queryRunner.query(sql,new ScalarHandler<>(),query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    @Override
    public Book findBook(long id) {
        String sql = "select id, title, `desc`, author, publisher, publish_date, price, sort, null as url from book where id = ?";
        Book book = null;
        try {
            book = queryRunner.query(sql,new BookHandler(),id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
    }

    @Override
    public String getUrl(long id) {
        String sql = "select url from book_img where book_id = ?";
        String url = null;
        try {
            url = queryRunner.query(sql,new ScalarHandler<>(),id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return url;
    }

    /*---更新书籍相关方法---*/

    @Override
    public boolean addBook(Book book) {
        String sql = "insert into book(title, `desc`, author, publisher, publish_date, price, sort_id, sort) " +
                "value(?,?,?,?,?,?,?,?)";
        long sortId = findSortId(book.getSort());
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            conn.setAutoCommit(false);
            int update = queryRunner.update(conn, sql, book.getTitle(),
                    book.getDesc(), book.getAuthor(),
                    book.getPublisher(), book.getPublishDate(),
                    book.getPrice(), sortId, book.getSort());
            conn.commit();
            return update == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            JDBCUtils.rollBack(conn);
        } finally {
            JDBCUtils.releaseResource(conn);
        }
        return false;
    }

    @Override
    public boolean deleteBook(long id) {
        String sql = "delete from book where id = ?";
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            conn.setAutoCommit(false);
            int update = queryRunner.update(conn, sql, id);
            conn.commit();
            return update == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            JDBCUtils.rollBack(conn);
        } finally {
            JDBCUtils.releaseResource(conn);
        }
        return false;
    }

    @Override
    public boolean updateBook(long id, String desc, BigDecimal price, String sort) {
        String sql = "update book set `desc` = ?, price = ?, sort = ? where id = ?";
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            conn.setAutoCommit(false);
            int update = queryRunner.update(conn, sql, desc, price, sort, id);
            conn.commit();
            return update == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            JDBCUtils.rollBack(conn);
        } finally {
            JDBCUtils.releaseResource(conn);
        }
        return false;
    }

    @Override
    public boolean uploadImage(long id, String url) {
        String sql = "insert into book_img(url,book_id) value(?,?)";
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            conn.setAutoCommit(false);
            int insert = queryRunner.update(conn, sql, url, id);
            conn.commit();
            return insert == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            JDBCUtils.rollBack(conn);
        } finally {
            JDBCUtils.releaseResource(conn);
        }
        return false;
    }

    @Override
    public boolean updateImage(long id, String url) {
        String sql = "update book_img set url = ? where book_id = ?";
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            conn.setAutoCommit(false);
            int update = queryRunner.update(conn, sql, url, id);
            conn.commit();
            return update == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            JDBCUtils.rollBack(conn);
        } finally {
            JDBCUtils.releaseResource(conn);
        }
        return false;
    }

    /*---辅助类---*/

    /**
     * 书籍列表处理类
     */
    private static class BookListHandler implements ResultSetHandler<List<Book>> {

        @Override
        public List<Book> handle(ResultSet rs) throws SQLException {
            List<Book> books = new ArrayList<>();
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getLong("id"));
                book.setTitle(rs.getString("title"));
                book.setDesc(rs.getString("desc"));
                book.setAuthor(rs.getString("author"));
                book.setPublisher(rs.getString("publisher"));
                book.setPublishDate(rs.getDate("publish_date"));
                book.setSort(rs.getString("sort"));
                book.setPrice(rs.getBigDecimal("price"));
                book.setUrl(rs.getString("url"));
                books.add(book);
            }
            return books;
        }
    }

    /**
     * 书籍处理类
     */
    private static class BookHandler implements ResultSetHandler<Book> {

        @Override
        public Book handle(ResultSet rs) throws SQLException {
            Book book = new Book();
            if (rs.next()) {
                book.setId(rs.getLong("id"));
                book.setTitle(rs.getString("title"));
                book.setDesc(rs.getString("desc"));
                book.setAuthor(rs.getString("author"));
                book.setPublisher(rs.getString("publisher"));
                book.setPublishDate(rs.getDate("publish_date"));
                book.setSort(rs.getString("sort"));
                book.setPrice(rs.getBigDecimal("price"));
                book.setUrl(rs.getString("url"));
            }
            return book;
        }
    }
}
