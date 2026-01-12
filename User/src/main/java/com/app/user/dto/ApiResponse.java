package com.app.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private int code;
    private String message;
    private T data;
    private Integer pageNumber;
    private Integer pageLimit;
    private Long totalElements;
    private Integer totalPages;

    public static <T> ApiResponse<T> of(ResponseStatus status, String message, T data) {
        return ApiResponse.<T>builder()
                .code(status.getCode())
                .message(message != null ? message : status.getLabel())
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> list(ResponseStatus status, String message, T data, int pageNumber, int pageLimit, long totalElements, int totalPages) {
        return ApiResponse.<T>builder()
                .code(status.getCode())
                .message(message != null ? message : status.getLabel())
                .data(data)
                .pageNumber(pageNumber)
                .pageLimit(pageLimit)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .build();
    }
}
