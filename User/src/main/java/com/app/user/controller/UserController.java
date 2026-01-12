package com.app.user.controller;

import com.app.user.dto.ApiResponse;
import com.app.user.dto.ResponseStatus;
import com.app.user.entity.User;
import com.app.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    // API kiểm tra service có hoạt động không
    @GetMapping("/test")
    public String testService() {
        return "User Service đã nhận được cuộc gọi!";
    }

    // API tạo mới người dùng
    @PostMapping
    public ResponseEntity<ApiResponse<User>> create(@Valid @RequestBody User body) {
        User created = service.create(body);
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.of(ResponseStatus.SUCCESS, ResponseStatus.SUCCESS.getLabel(), created));
    }

    // API lấy danh sách người dùng có phân trang
    @GetMapping
    public ResponseEntity<ApiResponse<List<User>>> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) Integer pageLimit
    ) {
        int p = pageNumber != null ? pageNumber : page;
        int s = pageLimit != null ? pageLimit : size;
        if (p < 1) p = 1;
        if (s < 1) s = 10;
        Pageable pageable = PageRequest.of(p - 1, s, Sort.by("userCreateAt").descending());
        
        // Gọi Service
        Page<User> pageData = service.findAll(pageable);
        
        // Trả về response với đầy đủ thông tin phân trang
        return ResponseEntity.ok(ApiResponse.list(
                ResponseStatus.SUCCESS, 
                ResponseStatus.SUCCESS.getLabel(), 
                pageData.getContent(),
                p,
                s,
                pageData.getTotalElements(),
                pageData.getTotalPages()
        ));
    }

    // API lấy thông tin chi tiết người dùng theo ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> get(@PathVariable String id) {
        User found = service.findById(id);
        if (found == null) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.of(ResponseStatus.NOT_FOUND, ResponseStatus.NOT_FOUND.getLabel(), null));
        return ResponseEntity.ok(ApiResponse.of(ResponseStatus.SUCCESS, ResponseStatus.SUCCESS.getLabel(), found));
    }

    // API cập nhật thông tin người dùng
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> update(@PathVariable String id, @Valid @RequestBody User update) {
        User updated = service.update(id, update);
        if (updated == null) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.of(ResponseStatus.NOT_FOUND, ResponseStatus.NOT_FOUND.getLabel(), null));
        return ResponseEntity.ok(ApiResponse.of(ResponseStatus.SUCCESS, ResponseStatus.SUCCESS.getLabel(), updated));
    }

    // API xóa người dùng
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> delete(@PathVariable String id) {
        boolean deleted = service.delete(id);
        if (!deleted) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.of(ResponseStatus.NOT_FOUND, ResponseStatus.NOT_FOUND.getLabel(), null));
        return ResponseEntity.ok(ApiResponse.of(ResponseStatus.SUCCESS, ResponseStatus.SUCCESS.getLabel(), null));
    }
}
