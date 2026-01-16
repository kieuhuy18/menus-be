package com.app.user.exception;
//dùng cho lỗi khi không tìm thấy tài nguyên (404 Not Found)
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
