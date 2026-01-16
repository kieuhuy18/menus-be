package com.app.user.dto;

import lombok.Getter;

@Getter
public enum ResponseStatus {
    UNKNOWN(-1, "UNKNOWN"),
    SUCCESS(0, "SUCCESS"),
    UNAUTHORIZED(1, "UNAUTHORIZED"),
    EXISTS(2, "EXISTS"),
    PERMISSION_DENIED(3, "PermissionDenied"),
    NOT_FOUND(4, "NotFound"),
    INVALID_PARAM(5, "InvalidParam"),
    SERVER_ERROR(7, "ServerError"),
    BAD_REQUEST(99, "BadRequest");

    private final int code;
    private final String label;

    ResponseStatus(int code, String label) {
        this.code = code;
        this.label = label;
    }
}
