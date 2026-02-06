package com.app.user.exception;

import com.app.user.dto.ResponseStatus;

public class BusinessException extends RuntimeException {

    private final ResponseStatus status;

    public BusinessException(ResponseStatus status, String message) {
        super(message);
        this.status = status;
    }

    public ResponseStatus getStatus() {
        return status;
    }
}
