package com.app.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users") // Khớp với cấu hình Gateway
public class UserController {
    // Chúng ta sẽ gọi đường dẫn này: /api/users/test
    @GetMapping("/test")
    public String testService() {
        return "User Service đã nhận được cuộc gọi!";
    }
}
