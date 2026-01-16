package com.app.user.repository;

import com.app.user.entity.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;
import java.util.List;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByNameRole(String nameRole);
    
    List<Role> findByPermissionIdsContaining(String permissionId);
}

