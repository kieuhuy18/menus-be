package com.app.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleRequestDTO {
    @NotBlank(message = "Role name is required")
    private String nameRole;
    
    private String description;
    
    private Set<String> permissionIds;
}
