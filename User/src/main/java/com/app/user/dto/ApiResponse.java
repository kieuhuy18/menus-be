package com.app.user.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private int code;
    private String message;
    private T data;
    private Integer pageNumber;
    private Integer pageLimit;

    //  RESPONSE bình thường
    public static <T> ApiResponse<T> of(ResponseStatus status, String message, T data) {
        ApiResponse<T> res = new ApiResponse<>();
        res.code = status.getCode();
        res.message = message != null ? message : status.getLabel();
        res.data = data;
        return res;
    }

    // RESPONSE danh sách
    public static <T> ApiResponse<List<T>> list(
            ResponseStatus status,
            String message,
            List<T> data,
            int pageNumber,
            int pageLimit
    ) {
        ApiResponse<List<T>> res = new ApiResponse<>();
        res.code = status.getCode();
        res.message = message != null ? message : status.getLabel();
        res.data = data;
        res.pageNumber = pageNumber;
        res.pageLimit = pageLimit;
        return res;
    }
}

