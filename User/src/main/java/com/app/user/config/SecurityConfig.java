package com.app.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.app.user.security.FakeJwtFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
public class SecurityConfig {
    private final FakeJwtFilter fakeJwtFilter;

    // Inject FakeJwtFilter để kiểm tra token giả
    public SecurityConfig(FakeJwtFilter fakeJwtFilter) {
        this.fakeJwtFilter = fakeJwtFilter;
    }




   // Cấu hình chuỗi filter bảo mật và cho phép tất cả request
   @Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .sessionManagement(sm ->
            sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )
        .authorizeHttpRequests(auth -> auth
            .anyRequest().permitAll()   
        )
        .addFilterBefore(fakeJwtFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
}
    // Bean mã hóa mật khẩu bằng BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
