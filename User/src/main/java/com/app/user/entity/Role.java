package com.app.user.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotBlank;
import java.util.Date;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Document("roles")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    private String id;

    @Indexed(unique = true)
    @NotBlank(message = "Role name is required")
    private String nameRole;
    private String description;

    private Set<String> permissionIds;
    private Date createAt;
    private Date updateAt;
    
}

