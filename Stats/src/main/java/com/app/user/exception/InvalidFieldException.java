package com.app.user.exception;
//dùng cho lỗi valdate nghiệp vụ ( name rỗng , sdt sai format , ngay sinh ở tương lai ,...)
public class InvalidFieldException extends RuntimeException {
    public InvalidFieldException(String message) {
        super(message);
    }
}
