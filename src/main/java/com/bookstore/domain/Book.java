package com.bookstore.domain;

import java.math.BigDecimal;
import java.util.Date;

public class Book {
    private Long id;
    private String title;
    private String author;
    private String desc;
    private BigDecimal price;
    // 出版日期
    private Date publishDate;
    // 出版社
    private String publisher;
    private String sort;

    public Book() {
    }

    public Book(String title, String author, String desc, BigDecimal price,
                Date publishDate, String publisher, String sort) {
        this.title = title;
        this.author = author;
        this.desc = desc;
        this.price = price;
        this.publishDate = publishDate;
        this.publisher = publisher;
        this.sort = sort;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Book{");
        sb.append("id=").append(id);
        sb.append(", title='").append(title).append('\'');
        sb.append(", author='").append(author).append('\'');
        sb.append(", desc='").append(desc).append('\'');
        sb.append(", price=").append(price);
        sb.append(", publishDate=").append(publishDate);
        sb.append(", publisher='").append(publisher).append('\'');
        sb.append(", sort='").append(sort).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
