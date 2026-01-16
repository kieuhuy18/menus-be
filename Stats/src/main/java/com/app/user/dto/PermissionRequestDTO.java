package com.app.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PermissionRequestDTO {
    @NotBlank(message = "Permission name is required")
    private String namePermission;
    
    private String description;
    
    @NotBlank(message = "Resource is required")
    private String resource;
    
    @NotEmpty(message = "At least one action is required")
    private List<String> action;
}
