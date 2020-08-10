package com.bookstore.service;

import com.bookstore.dao.BookDao;
import com.bookstore.dao.impl.BookDaoImpl;
import com.bookstore.domain.Book;
import com.bookstore.domain.Page;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BookService {

    private static final BookService bookService = new BookService();

    public static BookService getInstance() {
        return bookService;
    }

    private BookService() {}

    private BookDao bookDao = BookDaoImpl.getInstance();

    /*---查询业务---*/

    /**
     * 查询所有书籍(分页)
     * @param index
     * @param size
     * @return
     */
    public Page<Book> findAllBook(int index, int size) {
        Page<Book> page = new Page<>();
        int begin = (index - 1) * size;
        page.setData(bookDao.findAllBook(begin, size));
        page.setCount(bookDao.getTotal());
        page.updatePage(index, size);
        return page;
    }

    /**
     * 查询类似名称的书籍(分页)
     * @param query
     * @param index
     * @param size
     * @return
     */
    public Page<Book> findAllBook(String query, int index, int size) {
        Page<Book> page = new Page<>();
        int begin = (index - 1) * size;
        page.setData(bookDao.findAllBook(query, begin, size));
        page.setCount(bookDao.getTotal());
        page.updatePage(index, size);
        return page;
    }

    /**
     * 查询所有分类
     * @return
     */
    public List<String> findAllSort() {
        return bookDao.findAllSort();
    }

    /**
     * 判断是否有该分类
     * @param sort
     * @return
     */
    public boolean hasSort(String sort) {
        return bookDao.findSortId(sort) != null;
    }

    /**
     * 判断是否有该书籍
     * @param id
     * @return
     */
    public boolean hasId(long id) {
        return bookDao.findBook(id).getId() != null;
    }

    /**
     * 判断书籍是否有封面
     * @param id
     * @return
     */
    public boolean hasImage(long id) {
        return bookDao.getUrl(id) != null;
    }

    /**
     * 获取书籍图片的url
     * @param id
     * @return
     */
    public String getUrl(long id) {
        return bookDao.getUrl(id);
    }

    /**
     * 验证是否有该书籍
     * @param bookIds
     * @return
     */
    public boolean validBookId(Long[] bookIds) {
        for (int i = 0; i < bookIds.length; i++) {
            if (!hasId(bookIds[i]))
                return false;
        }
        return true;
    }

    /*---更新业务---*/

    /**
     * 添加书籍
     * @param title
     * @param desc
     * @param author
     * @param publisher
     * @param publishDateStr
     * @param priceStr
     * @param sort
     * @return
     * @throws ParseException
     */
    public boolean addBook(String title, String desc, String author, String publisher,
                           String publishDateStr, String priceStr, String sort) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date publishDate = sdf.parse(publishDateStr);
        BigDecimal price = BigDecimal.valueOf(Double.valueOf(priceStr));
        Book book = new Book(title,author,desc,price,publishDate,publisher,sort);
        return bookDao.addBook(book);
    }

    /**
     * 修改书籍信息
     * @param id
     * @param desc
     * @param priceStr
     * @param sort
     * @return
     */
    public boolean updateBook(long id, String desc, String priceStr, String sort) {
        BigDecimal price = BigDecimal.valueOf(Double.valueOf(priceStr));
        return bookDao.updateBook(id,desc,price,sort);
    }

    /**
     * 删除书籍
     * @param id
     * @return
     */
    public boolean deleteBook(long id) {
        return bookDao.deleteBook(id);
    }

    /**
     * 保存书籍图片的url
     * @param id
     * @param url
     * @return
     */
    public boolean uploadImage(long id, String url) {
        return bookDao.uploadImage(id, url);
    }

    /**
     * 更新书籍图片的url
     * @param id
     * @param url
     * @return
     */
    public boolean updateImage(long id, String url) {
        return bookDao.updateImage(id,url);
    }
}
