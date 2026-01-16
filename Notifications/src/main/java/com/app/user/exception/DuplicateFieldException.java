package com.app.user.exception;
// dùng để báo lỗi trùng lặp trường dữ liệu 
public class DuplicateFieldException extends RuntimeException {
    public DuplicateFieldException(String message) {
        super(message);
    }
}
