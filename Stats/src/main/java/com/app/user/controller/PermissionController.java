package com.app.user.controller;

import com.app.user.dto.ApiResponse;
import com.app.user.dto.ResponseStatus;
import com.app.user.entity.Permission;
import com.app.user.service.PermissionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users/permissions")
public class PermissionController {
    private final PermissionService service;

    public PermissionController(PermissionService service) {
        this.service = service;
    }

    // API tạo mới quyền hạn (Permission)
    @PostMapping
    public ResponseEntity<ApiResponse<Permission>> create(@Valid @RequestBody Permission body) {
        Permission created = service.create(body);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.of(ResponseStatus.SUCCESS, ResponseStatus.SUCCESS.getLabel(), created));
    }

    // API lấy danh sách quyền hạn có phân trang
    @GetMapping
    public ResponseEntity<ApiResponse<List<Permission>>> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) Integer pageLimit
    ) {
        int p = pageNumber != null ? pageNumber : page;
        int s = pageLimit != null ? pageLimit : size;
        if (p < 1) p = 1;
        if (s < 1) s = 10;
        Pageable pageable = PageRequest.of(p - 1, s, Sort.by("createAt").descending());
        
        // Gọi Service
        Page<Permission> pageData = service.findAll(pageable);
        
        // Trả về response với đầy đủ thông tin phân trang
        return ResponseEntity.ok(ApiResponse.list(
                ResponseStatus.SUCCESS, 
                ResponseStatus.SUCCESS.getLabel(), 
                pageData.getContent(),
                p,
                s
                
        ));
    }

    // API lấy thông tin chi tiết quyền hạn theo ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Permission>> get(@PathVariable String id) {
        Permission found = service.findById(id);
        if (found == null) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.of(ResponseStatus.NOT_FOUND, ResponseStatus.NOT_FOUND.getLabel(), null));
        return ResponseEntity.ok(ApiResponse.of(ResponseStatus.SUCCESS, ResponseStatus.SUCCESS.getLabel(), found));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Permission>> update(@PathVariable String id, @Valid @RequestBody Permission update) {
        Permission updated = service.update(id, update);
        if (updated == null) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.of(ResponseStatus.NOT_FOUND, ResponseStatus.NOT_FOUND.getLabel(), null));
        return ResponseEntity.ok(ApiResponse.of(ResponseStatus.SUCCESS, ResponseStatus.SUCCESS.getLabel(), updated));
    }

    // API xóa quyền hạn
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> delete(@PathVariable String id) {
        boolean deleted = service.delete(id);
        if (!deleted) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.of(ResponseStatus.NOT_FOUND, ResponseStatus.NOT_FOUND.getLabel(), null));
        return ResponseEntity.ok(ApiResponse.of(ResponseStatus.SUCCESS, ResponseStatus.SUCCESS.getLabel(), null));
    }
}
