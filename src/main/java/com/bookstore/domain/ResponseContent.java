package com.bookstore.domain;

public class ResponseContent {
    private int code;
    private String message;

    public ResponseContent(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ResponseContent isOk(String message) {
        return new ResponseContent(1, message);
    }

    public static ResponseContent isError(String message) {
        return new ResponseContent(-1, message);
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ResponseContent{");
        sb.append("code='").append(code).append('\'');
        sb.append(", message='").append(message).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
