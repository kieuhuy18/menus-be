package com.app.user.controller;

import com.app.user.dto.*;
import com.app.user.dto.ResponseStatus;
import com.app.user.entity.User;
import com.app.user.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService service;

        // Inject AuthService để xử lý nghiệp vụ xác thực
        public AuthController(AuthService service) {
        this.service = service;
    }

        // API đăng ký tài khoản mới
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterResponseDTO>> register(
            @Valid @RequestBody RegisterRequestDTO request
    ) {
        User user = service.register(request);

        return ResponseEntity.ok(
                ApiResponse.of(
                        ResponseStatus.SUCCESS,
                        null,
                        RegisterResponseDTO.builder()
                                .id(user.getId())
                                .userName(user.getUserName())
                                .fullName(user.getFullName())
                                .email(user.getUserEmail())
                                .build()
                )
        );
    }


        // API đăng nhập và trả về token
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDTO>> login(
            @Valid @RequestBody LoginRequestDTO request
    ) {
        return ResponseEntity.ok(
                ApiResponse.of(
                        ResponseStatus.SUCCESS,
                        null,
                        service.login(request)
                )
        );
    }



}
