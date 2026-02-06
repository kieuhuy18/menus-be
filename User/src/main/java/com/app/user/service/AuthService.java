package com.app.user.service;

import com.app.user.dto.LoginRequestDTO;
import com.app.user.dto.LoginResponseDTO;
import com.app.user.dto.RegisterRequestDTO;
import com.app.user.dto.ResponseStatus;
import com.app.user.entity.Role;
import com.app.user.entity.User;
import com.app.user.exception.BusinessException;
import com.app.user.repository.RoleRepository;
import com.app.user.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

        // Đăng ký người dùng mới, đảm bảo username/email không trùng và gán role USER mặc định
        public User register(RegisterRequestDTO dto) {

        if (userRepository.existsByUserName(dto.getUserName())) {
            throw new BusinessException(ResponseStatus.EXISTS, "Username already exists");
        }

        if (userRepository.existsByUserEmail(dto.getEmail())) {
            throw new BusinessException(ResponseStatus.EXISTS, "Email already exists");
        }

        Role userRole = roleRepository.findByNameRole("USER")
                .orElseThrow(() -> new BusinessException(
                        ResponseStatus.NOT_FOUND,
                        "Default role USER not found"
                ));

        User user = User.builder()
                .userName(dto.getUserName())
                .password(passwordEncoder.encode(dto.getPassword()))
                .fullName(dto.getFullName())
                .userEmail(dto.getEmail())
                .roleIds(Set.of(userRole.getId()))
                .userActive(true)
                .build();

        return userRepository.save(user);
    }

        // Xác thực thông tin đăng nhập và tạo token tạm thời
        public LoginResponseDTO login(LoginRequestDTO dto) {

        User user = userRepository.findByUserName(dto.getUserName())
                .orElseThrow(() -> new BusinessException(
                        ResponseStatus.NOT_FOUND,
                        "User not found"
                ));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new BusinessException(ResponseStatus.UNAUTHORIZED,
                    "Username or password incorrect");
        }

        String token = "FAKE_JWT_TOKEN_" + user.getId(); // tạm thời

        return LoginResponseDTO.builder()
                .token(token)
                .userName(user.getUserName())
                .build();
    }




}
