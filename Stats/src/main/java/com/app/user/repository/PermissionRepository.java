package com.app.user.repository;

import com.app.user.entity.Permission;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface PermissionRepository extends MongoRepository<Permission, String> {
    Optional<Permission> findByNamePermission(String namePermission);
}