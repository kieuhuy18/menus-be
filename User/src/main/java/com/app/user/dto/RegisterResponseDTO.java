package com.app.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterResponseDTO {
    private String id;
    private String userName;
    private String fullName;
    private String email;
}
