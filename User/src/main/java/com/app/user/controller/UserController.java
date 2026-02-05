package com.app.user.controller;

import com.app.user.dto.ApiResponse;
import com.app.user.dto.ResponseStatus;
import com.app.user.dto.UserResponse;
import com.app.user.dto.UserUpdateRequest;
import com.app.user.entity.User;
import com.app.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

        // Inject UserService để thao tác dữ liệu người dùng
        public UserController(UserService service) {
        this.service = service;
    }

    // tạo user mới
    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> create(
            @Valid @RequestBody UserUpdateRequest request) {

        User user = mapToEntity(request);

        // giao toàn bộ nghiệp vụ cho service ( trùng , tồn tại , set nhày tạo , lưu DB)
        User created = service.create(user);

        return ResponseEntity.ok(
                ApiResponse.of(
                        ResponseStatus.SUCCESS,
                        null,
                        UserResponse.fromEntity(created)
                ));
    }

    // lấy danh sách theo phân trang
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> list(
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "10") int pageLimit) {

        Pageable pageable = PageRequest.of(
                Math.max(pageNumber - 1, 0),
                Math.max(pageLimit, 1),
                Sort.by("userCreateAt").descending()
        );

        Page<User> pageData = service.findAll(pageable);

        List<UserResponse> users = pageData.getContent()
                .stream()
                .map(UserResponse::fromEntity)
                .toList();

        return ResponseEntity.ok(
                ApiResponse.list(
                        ResponseStatus.SUCCESS,
                        null,
                        users,
                        pageNumber,
                        pageLimit
                )
        );
    }

    // lấy chi tiết user
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> get(@PathVariable String id) {


        User user = service.findById(id);

        return ResponseEntity.ok(
                ApiResponse.of(
                        ResponseStatus.SUCCESS,
                        null,
                        UserResponse.fromEntity(user)
                )
        );
    }

    // cập nhật user
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> update(
            @PathVariable String id,
            @Valid @RequestBody UserUpdateRequest request) {

        User update = mapToEntity(request);

        User updated = service.update(id, update);

        return ResponseEntity.ok(
                ApiResponse.of(
                        ResponseStatus.SUCCESS,
                        null,
                        UserResponse.fromEntity(updated)
                )
        );
    }

    // xóa
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> delete(@PathVariable String id) {

        service.delete(id);

        return ResponseEntity.ok(
                ApiResponse.of(
                        ResponseStatus.SUCCESS,
                        null,
                        null
                )
        );
    }

        // Mapping DTO sang entity để tái sử dụng giữa create/update
        private User mapToEntity(UserUpdateRequest request){
        User user = new User();
        user.setFullName(request.getFullName());
        user.setUserEmail(request.getUserEmail());
       
        user.setPhoneNumber(request.getPhoneNumber());
        user.setUserGender(request.getUserGender());
        user.setUserBirthday(request.getUserBirthday());
        user.setUserAddress(request.getUserAddress());
        return user;
        

    }
}
