package com.app.user.exception;

import com.app.user.dto.ApiResponse;
import com.app.user.dto.ResponseStatus;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import jakarta.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 400 – BAD REQUEST

    // Lỗi validate @Valid DTO
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse< Object>> handleValidation(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> e.getField() + ":" + e.getDefaultMessage())
                .collect(Collectors.toList());
        ApiResponse<Object> res = ApiResponse.of(
                ResponseStatus.BAD_REQUEST,
                "Invalid request data",
                errors
        );
        return ResponseEntity.ok(res);
    }

    // Lỗi ràng buộc @NotNull, @Size, @Min...
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse< Object>> handleConstraint(ConstraintViolationException ex) {
        List<String> errors = ex.getConstraintViolations()
                .stream()
                .map(v -> v.getPropertyPath() + ":" + v.getMessage())
                .collect(Collectors.toList());
        ApiResponse<Object> res = ApiResponse.of(
                ResponseStatus.BAD_REQUEST,
                "Invalid request data",
                errors
        );
        return ResponseEntity.ok(res);
    }

    // Lỗi JSON sai format
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse< Object>> handleJsonError() {
        return buildResponse(ResponseStatus.BAD_REQUEST, "Invalid JSON format");
    }

    // Sai kiểu dữ liệu param
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse< Object>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String msg = "Invalid type for parameter '" + ex.getName() + "'";
        return buildResponse(ResponseStatus.BAD_REQUEST, msg);
    }

    // Thiếu param
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse< Object>> handleMissingParam(MissingServletRequestParameterException ex) {
        return buildResponse(ResponseStatus.BAD_REQUEST,
                "Missing parameter: " + ex.getParameterName());
    }

    //  trùng field
    @ExceptionHandler(DuplicateFieldException.class)
    public ResponseEntity<ApiResponse< Object>> handleDuplicate(DuplicateFieldException ex) {
        return buildResponse(ResponseStatus.BAD_REQUEST, ex.getMessage());
    }

    //  dữ liệu không hợp lệ
    @ExceptionHandler(InvalidFieldException.class)
    public ResponseEntity<ApiResponse< Object>> handleInvalid(InvalidFieldException ex) {
        return buildResponse(ResponseStatus.BAD_REQUEST, ex.getMessage());
    }


    // 404 – NOT FOUND

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleNotFound(ResourceNotFoundException ex) {
        return buildResponse(ResponseStatus.NOT_FOUND, ex.getMessage());
    }

    // ================= BUSINESS =================
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Object>> handleBusiness(BusinessException e) {
        return ResponseEntity.ok(
                ApiResponse.of(
                        e.getStatus(),
                        e.getMessage(),
                        null
                )
        );
    }


    //500 – SERVER ERROR
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleUnknown(Exception e) {

        // log để debug
        e.printStackTrace();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.of(
                        ResponseStatus.SERVER_ERROR,
                        "Unexpected error",
                        null
                ));
    }


    // COMMON BUILDER

    private ResponseEntity<ApiResponse<Object>> buildResponse(ResponseStatus status, String message) {
        ApiResponse<Object> res = ApiResponse.of(
                status,
                message,
                null
        );
        return ResponseEntity.ok(res);

    }
}


    


