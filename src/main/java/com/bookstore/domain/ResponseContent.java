package com.bookstore.domain;

public class ResponseContent {
    private int code;
    private Object data;

    public ResponseContent(int code, Object data) {
        this.code = code;
        this.data = data;
    }

    public static <T> ResponseContent isOk(Object data) {
        return new ResponseContent(1, data);
    }

    public static <T> ResponseContent isError(Object data) {
        return new ResponseContent(-1, data);
    }

    public int getCode() {
        return code;
    }

    public Object getData() {
        return data;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ResponseContent{");
        sb.append("code='").append(code).append('\'');
        sb.append(", data='").append(data).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
