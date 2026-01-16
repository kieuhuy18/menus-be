package com.app.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleResponseDTO {
    private String id;
    private String nameRole;
    private String description;
    private Set<String> permissionIds;
    private Date createAt;
    private Date updateAt;
}
