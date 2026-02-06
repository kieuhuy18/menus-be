package com.app.user.security;

import com.app.user.entity.User;
import com.app.user.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
public class FakeJwtFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;

    public FakeJwtFilter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Override
    

    protected void doFilterInternal(
            
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        
       
        
        String authHeader = request.getHeader("Authorization");

      
        // Không có token → cho qua
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        // Token format: FAKE_JWT_TOKEN_{userId}
        if (!token.startsWith("FAKE_JWT_TOKEN_")) {
            filterChain.doFilter(request, response);
            return;
        }

        String userId = token.replace("FAKE_JWT_TOKEN_", "");

     

        
         
        userRepository.findById(userId).ifPresent(user -> {

    List<SimpleGrantedAuthority> authorities =
            List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));

    UsernamePasswordAuthenticationToken authentication =
        new UsernamePasswordAuthenticationToken(
                user,
                null,
                Collections.emptyList() //  không cần quyền
        );

SecurityContextHolder.getContext().setAuthentication(authentication);

    if (SecurityContextHolder.getContext().getAuthentication() == null) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println(">>> Authenticated user " + user.getUserName()
                + " with ROLE_ADMIN");
    }

        });

        filterChain.doFilter(request, response);
    }


}
