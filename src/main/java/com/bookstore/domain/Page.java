package com.bookstore.domain;

import java.util.List;

public class Page<T> {

    private long count;
    private List<T> data;
    private long start;
    private long end;
    private long total;
    private long curr;

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getCurr() {
        return curr;
    }

    public void setCurr(long curr) {
        this.curr = curr;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Page{");
        sb.append("count=").append(count);
        sb.append(", data=").append(data);
        sb.append(", start=").append(start);
        sb.append(", end=").append(end);
        sb.append(", total=").append(total);
        sb.append(", curr=").append(curr);
        sb.append('}');
        return sb.toString();
    }
}
