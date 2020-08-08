package com.bookstore.dao;

import com.bookstore.domain.Book;

import java.math.BigDecimal;
import java.util.List;

public interface BookDao {

    /*---查询书籍相关方法---*/

    List<Book> findAllBook(int begin, int count);

    List<Book> findAllBook(String query, int begin, int count);

    List<String> findAllSort();

    long getTotal();

    long getTotal(String query);

    Integer findSortId(String sort);

    Book findBook(long id);

    String getUrl(long id);

    /*---更新书籍相关方法---*/

    boolean addBook(Book book);

    boolean deleteBook(long id);

    boolean updateBook(long id, String desc, BigDecimal price, String sort);

    boolean uploadImage(long id, String url);

    boolean updateImage(long id, String url);
}
