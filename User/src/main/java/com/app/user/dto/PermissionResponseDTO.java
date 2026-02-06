package com.app.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.app.user.entity.Permission;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PermissionResponseDTO {
    private String id;
    private String namePermission;
    private String description;
    private String resource;
    private List<String> action;
    private Date createAt;
    public static PermissionResponseDTO fromEntity(Permission p) {
        if(p == null) return null;
        return PermissionResponseDTO.builder()
                .id(p.getId())
                .namePermission(p.getNamePermission())
                .description(p.getDescription())
                .resource(p.getResource())
                .action(p.getAction())
                .createAt(p.getCreateAt())
                .build();
    }
}
