package com.app.user.config;

import com.app.user.entity.Role;
import com.app.user.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Seeds the default USER role at application startup so dependent services
 * always have the baseline authority available.
 */
@Component
@RequiredArgsConstructor
public class RoleInitializer {

    private static final String DEFAULT_ROLE = "USER";

    private final RoleRepository roleRepository;

    @PostConstruct
    @SuppressWarnings("null")
    // Khởi tạo role USER mặc định khi ứng dụng start
    public void init() {
        if (roleRepository.existsByNameRole(DEFAULT_ROLE)) {
            return;
        }

        Role defaultRole = Role.builder()
                .nameRole(DEFAULT_ROLE)
                .description("Default application user role")
                .createAt(new Date())
                .updateAt(new Date())
                .build();

        roleRepository.save(defaultRole);
    }
}
