package com.bookstore.web;

import com.bookstore.domain.Book;
import com.bookstore.domain.Page;
import com.bookstore.service.BookService;
import com.bookstore.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;

@WebServlet("/book")
public class BookController extends BasicController {

    private static final Logger log = LoggerFactory.getLogger(BookController.class);

    private static final String TITLE_VALIDATION = "^([\\u4E00-\\u9FA5]|\\w){1,15}$";
    private static final String PRICE_VALIDATION = "^(([1-9][0-9]*)|0)(\\.[0-9]+)?$";
    private static final String PUBLISH_DATE_VALIDATION = "^(19|20)[0-9]{2}-((0[1-9])|(1[0-2]))-((0[1-9])|([1-2][0-9])|(3[0-2]))$";

    private BookService bookService = BookService.getInstance();

    /**
     * 跳转到书籍页面
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String indexStr = req.getParameter("index"),
                sizeStr = req.getParameter("size"),
                query = req.getParameter("q");

        int index = 1, size = 10;

        if (StringUtils.notEmpty(indexStr))
            index = Integer.parseInt(indexStr);
        if (StringUtils.notEmpty(sizeStr))
            size = Integer.parseInt(sizeStr);
        Page<Book> page = null;

        if (query != null) {
            page = bookService.findAllBook(query, index, size);
        } else {
            page = bookService.findAllBook(index, size);
        }

        String[] paramsName = {"page","q"};
        Object[] param = {page,query};

        renderTemplate(req, resp, "book",paramsName,param);
    }

    /**
     * 添加书籍
     * 请求参数: title,desc,author,sort,price,publisher,publishDate
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title"),
                desc = req.getParameter("desc"),
                author = req.getParameter("author"),
                sort = req.getParameter("sort"),
                priceStr = req.getParameter("price"),
                publisher = req.getParameter("publisher"),
                publishDateStr = req.getParameter("publishDate");
        log.info(title+","+desc+","+author+","+sort+","+priceStr+","+publisher+","+publishDateStr);
        // 验证
        boolean verification = verifyParameters(resp, title, desc,
                author, publisher, publishDateStr, priceStr, sort);
        // 验证失败,直接返回
        if (!verification)
            return;

        boolean success = false;
        try {
            success = bookService.addBook(title, desc, author,
                    publisher, publishDateStr,
                    priceStr, sort);
        } catch (ParseException e) {
            log.info("日期解析失败",e);
        }

        if (success)
            writeOkMsg(resp, "添加成功");
        else
            writeErrorMsg(resp, "添加失败");
    }

    /**
     * 修改书籍信息
     * 请求参数: id,desc,sort,price
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String  idStr = req.getParameter("id"),
                desc = req.getParameter("desc"),
                sort = req.getParameter("sort"),
                priceStr = req.getParameter("price");
        if (StringUtils.isEmpty(idStr)) {
            writeErrorMsg(resp, "书籍id不能为空");
            return;
        }
        if (StringUtils.isEmpty(desc)) {
            writeErrorMsg(resp, "描述不能为空");
            return;
        }
        if (StringUtils.isEmpty(priceStr) || !priceStr.matches(PRICE_VALIDATION)) {
            writeErrorMsg(resp, "价格格式不正确");
            return;
        }
        if (StringUtils.isEmpty(sort) || !bookService.hasSort(sort)) {
            writeErrorMsg(resp, "分类不存在");
            return;
        }
        try {
            long id = Long.parseLong(idStr);
            if (!bookService.hasId(id)) {
                writeErrorMsg(resp, "不存在该书籍");
                return;
            }
            boolean updateSuccess = bookService.updateBook(id, desc, priceStr, sort);
            if (updateSuccess)
                writeOkMsg(resp, "修改成功");
            else
                writeOkMsg(resp, "修改失败");
        } catch (NumberFormatException e) {
            writeErrorMsg(resp, "id格式不正确");
        }
    }

    /**
     * 删除书籍
     * 请求参数: id
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String  idStr = req.getParameter("id");
        if (StringUtils.isEmpty(idStr)) {
            writeErrorMsg(resp, "书籍id不能为空");
            return;
        }
        try {
            long id = Long.parseLong(idStr);
            if (!bookService.hasId(id)) {
                writeErrorMsg(resp, "不存在该书籍");
                return;
            }
            boolean deleteSuccess = bookService.deleteBook(id);
            if (deleteSuccess)
                writeOkMsg(resp, "删除成功");
            else
                writeOkMsg(resp, "删除失败");
        } catch (NumberFormatException e) {
            writeErrorMsg(resp, "id格式不正确");
        }
    }

    /**
     * 验证参数。
     * 验证失败返回false,并响应错误消息;
     * 验证成功返回true。
     * @param resp
     * @param title
     * @param desc
     * @param author
     * @param publisher
     * @param publishDateStr
     * @param priceStr
     * @param sort
     * @return
     * @throws IOException
     */
    private boolean verifyParameters(HttpServletResponse resp, String title,
                                     String desc, String author, String publisher,
                                     String publishDateStr, String priceStr, String sort)
            throws IOException {
        if (StringUtils.isEmpty(title) || !title.matches(TITLE_VALIDATION)) {
            log.info(title);
            writeErrorMsg(resp, "书籍名称格式不正确");
            return false;
        }
        if (StringUtils.isEmpty(desc)) {
            writeErrorMsg(resp, "描述不能为空");
            return false;
        }
        if (StringUtils.isEmpty(author)) {
            writeErrorMsg(resp, "作者不能为空");
            return false;
        }
        if (StringUtils.isEmpty(sort)) {
            writeErrorMsg(resp, "分类不能为空");
            return false;
        } else if (!bookService.hasSort(sort)) {
            writeErrorMsg(resp, "分类不存在");
            return false;
        }
        if (StringUtils.isEmpty(priceStr) || !priceStr.matches(PRICE_VALIDATION)) {
            writeErrorMsg(resp, "价格格式不正确");
            return false;
        }
        if (StringUtils.isEmpty(publisher)) {
            writeErrorMsg(resp, "出版社不能为空");
            return false;
        }
        if (StringUtils.isEmpty(publishDateStr) || !publishDateStr.matches(PUBLISH_DATE_VALIDATION)) {
            writeErrorMsg(resp, "出版日期格式不正确");
            return false;
        }
        return true;
    }
}
