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

    /*---辅助函数---*/

    /**
     * 设置分页
     * @param index 当前页
     * @param size 每页数量
     */
    public void updatePage(int index, int size) {
        // 当前页
        setCurr(index);
        // 总页数
        long total = getCount() % size == 0 ? getCount() / size : getCount() / size + 1;
        setTotal(total);
        // 起始页,结束页
        if (total <= 5) {
            // 小于5页直接返回
            setStart(1);
            setEnd(total);
        } else {
            long start = index - 2, end = index + 2;
            if (end > total) {
                long sub = end - total;
                start -= sub;
                end -= sub;
            }
            if (start < 1) {
                long add = 1 - start;
                start += add;
                end += add;
            }
            if (end > total) {
                end = total;
            }
            setStart(start);
            setEnd(end);
        }
    }
}
