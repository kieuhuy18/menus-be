package com.app.user.dto;
import com.app.user.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

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

    public static RoleResponseDTO fromEntity(Role role) {
        return RoleResponseDTO.builder()
                .id(role.getId())
                .nameRole(role.getNameRole())
                .description(role.getDescription())
                .permissionIds(role.getPermissionIds())
                .createAt(role.getCreateAt())
                .updateAt(role.getUpdateAt())
                .build();
    }
}
