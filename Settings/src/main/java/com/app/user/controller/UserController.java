package com.app.user.controller;

import com.app.user.dto.ApiResponse;
import com.app.user.dto.ResponseStatus;
import com.app.user.dto.UserDetailResponse;
import com.app.user.dto.UserListResponse;
import com.app.user.dto.UserUpdateRequest;
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

    

    // API tạo mới người dùng
    @PostMapping
    public ResponseEntity<ApiResponse<User>> create(@Valid @RequestBody User body) {
        User created = service.create(body);
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.of(ResponseStatus.SUCCESS, ResponseStatus.SUCCESS.getLabel(), created));
    }

    // API lấy danh sách người dùng có phân trang
    @GetMapping
public ResponseEntity<ApiResponse<List<UserListResponse>>> list(
        @RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber,
        @RequestParam(name = "pageLimit", defaultValue = "10") int pageLimit
) {
    if (pageNumber < 1) pageNumber = 1;
    if (pageLimit < 1) pageLimit = 10;

    Pageable pageable = PageRequest.of(pageNumber - 1, pageLimit, Sort.by("userCreateAt").descending());

    Page<User> pageData = service.findAll(pageable);

    // Chỉ chuyển các thông tin cần thiết ra DTO
    List<UserListResponse> users = pageData.getContent().stream()
            .map(UserListResponse::fromEntity) // UserResponse chỉ có thông tin cơ bản
            .toList();

    return ResponseEntity.ok(ApiResponse.list(
            ResponseStatus.SUCCESS,
            ResponseStatus.SUCCESS.getLabel(),
            users,
            pageNumber,
            pageLimit
    ));
}


    // API lấy thông tin chi tiết người dùng theo ID
    @GetMapping("/{id}")
public ResponseEntity<ApiResponse<UserDetailResponse>> get(@PathVariable String id) {
    User found = service.findById(id);
    if (found == null) return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(ApiResponse.of(ResponseStatus.NOT_FOUND, ResponseStatus.NOT_FOUND.getLabel(), null));

    // Chuyển sang DTO chi tiết
    UserDetailResponse dto = UserDetailResponse.fromEntity(found);
    return ResponseEntity.ok(ApiResponse.of(ResponseStatus.SUCCESS, ResponseStatus.SUCCESS.getLabel(), dto));
}

    // API cập nhật thông tin người dùng
    @PutMapping("/{id}")
public ResponseEntity<ApiResponse<UserDetailResponse>> update(
        @PathVariable String id,
        @Valid @RequestBody UserUpdateRequest updateRequest) {

    // Chuyển DTO sang entity chứa các field cần update
    User update = new User();
    update.setFullName(updateRequest.getFullName());
    update.setUserEmail(updateRequest.getUserEmail());
    update.setPhoneNumber(updateRequest.getPhoneNumber());
    update.setUserBirthday(updateRequest.getUserBirthday());
    update.setUserGender(updateRequest.getUserGender());
    update.setUserAddress(updateRequest.getUserAddress());

    User updated = service.update(id, update);
    if (updated == null) return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(ApiResponse.of(ResponseStatus.NOT_FOUND, ResponseStatus.NOT_FOUND.getLabel(), null));

    UserDetailResponse dto = UserDetailResponse.fromEntity(updated);
    return ResponseEntity.ok(ApiResponse.of(ResponseStatus.SUCCESS, ResponseStatus.SUCCESS.getLabel(), dto));
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
