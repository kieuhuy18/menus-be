package com.app.user.controller;
import com.app.user.dto.*;
import com.app.user.dto.ApiResponse;
import com.app.user.dto.PermissionRequestDTO;
import com.app.user.dto.PermissionResponseDTO;
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

        // Inject PermissionService để thao tác quyền hạn
        public PermissionController(PermissionService service) {
        this.service = service;
    }

    // API tạo mới quyền hạn (Permission)
    @PostMapping
    public ResponseEntity<ApiResponse<PermissionResponseDTO>> create(@Valid @RequestBody PermissionRequestDTO request) {
        Permission created = service.create(request);
       

        return ResponseEntity.ok(
                ApiResponse.of(
                        ResponseStatus.SUCCESS,
                        null,
                        PermissionResponseDTO.fromEntity(created)
                ));
    }

    // API lấy danh sách quyền hạn có phân trang
    @GetMapping
    public ResponseEntity<ApiResponse<List<PermissionResponseDTO>>> list(
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "10") int pageLimit
            
    ) {
        
        Pageable pageable = PageRequest.of(
                Math.max(pageNumber - 1, 0),
                Math.max(pageLimit, 1),
                Sort.by("createAt").descending()
        );
        // Gọi Service
        Page<Permission> pageData = service.findAll(pageable);
        
        // Trả về response với đầy đủ thông tin phân trang
        
        List<PermissionResponseDTO> data = pageData.getContent()
                .stream()
                .map(PermissionResponseDTO::fromEntity)
                .toList();
        
        return ResponseEntity.ok(ApiResponse.list(
                ResponseStatus.SUCCESS, 
                null,
                data,
                pageNumber,
                pageLimit
                
        ));
    }

    // API lấy thông tin chi tiết quyền hạn theo ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PermissionResponseDTO>> get(@PathVariable String id) {
        Permission found = service.findById(id);
        
        return ResponseEntity.ok(
                ApiResponse.of(
                        ResponseStatus.SUCCESS,
                        null,
                        PermissionResponseDTO.fromEntity(found)
                ));
    }

        // API cập nhật quyền hạn theo ID
        @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PermissionResponseDTO>> update(@PathVariable String id, @Valid @RequestBody PermissionRequestDTO request) {
        Permission updated = service.update(id, request);
        return ResponseEntity.ok(
                ApiResponse.of(
                        ResponseStatus.SUCCESS,
                        null,
                        PermissionResponseDTO.fromEntity(updated)
                ));
    }

    // API xóa quyền hạn
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> delete(@PathVariable String id) {
       service.delete(id);
         return ResponseEntity.ok(
                ApiResponse.of(
                          ResponseStatus.SUCCESS,
                          "Permission deleted successfully",
                          null
                ));
    }
}
