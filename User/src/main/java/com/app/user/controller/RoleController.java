package com.app.user.controller;

import com.app.user.dto.ApiResponse;
import com.app.user.dto.ResponseStatus;
import com.app.user.dto.RoleRequestDTO;
import com.app.user.dto.RoleResponseDTO;
import com.app.user.entity.Role;

import com.app.user.service.RoleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users/roles")
public class RoleController {
    private final RoleService service;

        // Inject RoleService để xử lý nghiệp vụ vai trò
        public RoleController(RoleService service) {
        this.service = service;
    }

    // API tạo mới vai trò (Role)
    @PostMapping
    public ResponseEntity<ApiResponse<RoleResponseDTO>> create(@Valid @RequestBody RoleRequestDTO request) {
        Role created = service.create(request);
        return ResponseEntity.ok(
                ApiResponse.of(
                        ResponseStatus.SUCCESS,
                        null,
                        RoleResponseDTO.fromEntity(created)
                ));
    }

    // API lấy danh sách vai trò có phân trang
    @GetMapping
    public ResponseEntity<ApiResponse<List<RoleResponseDTO>>> list(
            @RequestParam(defaultValue = "1") int pageNumber, 
            @RequestParam(defaultValue = "10") int pageLimit
            
    ) {
        Pageable pageable = PageRequest.of(
                Math.max(pageNumber - 1, 0),
                Math.max(pageLimit, 1),
                Sort.by("createAt").descending()
        );
        
        // Gọi Service
        Page<Role> pageData = service.findAll(pageable);
        
        List<RoleResponseDTO> roles = pageData.getContent()
                .stream()
                .map(RoleResponseDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(
                ApiResponse.list(
                        ResponseStatus.SUCCESS,
                        null,
                        roles,
                        pageNumber,
                        pageLimit
                )
        );
    }

    // API lấy thông tin chi tiết vai trò theo ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RoleResponseDTO>> get(@PathVariable String id) {
        Role role = service.findById(id);
        
        return ResponseEntity.ok(
                ApiResponse.of(
                        ResponseStatus.SUCCESS,
                        null,
                        RoleResponseDTO.fromEntity(role)
                ));
    }

    // API cập nhật thông tin vai trò
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<RoleResponseDTO>> update(@PathVariable String id, @Valid @RequestBody RoleRequestDTO request) {
        Role updated = service.update(id, request);
        return ResponseEntity.ok(
                ApiResponse.of(
                        ResponseStatus.SUCCESS,
                        null,
                        RoleResponseDTO.fromEntity(updated)
                ));
    }

    // API xóa vai trò
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.ok(
                ApiResponse.of(
                        ResponseStatus.SUCCESS,
                        null,
                        null
                ));
    }
}

