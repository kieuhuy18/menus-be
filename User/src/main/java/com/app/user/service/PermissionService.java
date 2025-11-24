package com.app.user.service;

import com.app.user.entity.Permission;
import com.app.user.entity.Role;
import com.app.user.repository.PermissionRepository;
import com.app.user.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.Date;
import java.util.List;

@Service
public class PermissionService {
    private final PermissionRepository repository;
    private final RoleRepository roleRepository;

    public PermissionService(PermissionRepository repository, RoleRepository roleRepository) {
        this.repository = repository;
        this.roleRepository = roleRepository;
    }

    public Permission create(Permission p) {
        if (p.getNamePermission() != null && repository.findByNamePermission(p.getNamePermission()).isPresent()) {
            throw new IllegalArgumentException("Permission name already exists");
        }
        p.setId(null);
        p.setCreateAt(new Date());
        return repository.save(p);
    }

    public List<Permission> findAll() {
        return repository.findAll();
    }

    public Permission findById(String id) {
        return repository.findById(id).orElse(null);
    }

    public Permission update(String id, Permission update) {
        Permission existing = repository.findById(id).orElse(null);
        if (existing == null) return null;
        if (StringUtils.hasText(update.getNamePermission())) {
            if (!update.getNamePermission().equals(existing.getNamePermission()) &&
                repository.findByNamePermission(update.getNamePermission()).isPresent()) {
                throw new IllegalArgumentException("Permission name already exists");
            }
            existing.setNamePermission(update.getNamePermission());
        }
        if (update.getDescription() != null) existing.setDescription(update.getDescription());
        if (StringUtils.hasText(update.getResource())) existing.setResource(update.getResource());
        if (update.getAction() != null) existing.setAction(update.getAction());
        return repository.save(existing);
    }

    public boolean delete(String id) {
        if (!repository.existsById(id)) return false;
        
        List<Role> rolesUsingPermission = roleRepository.findByPermissionIdsContaining(id);
        if (!rolesUsingPermission.isEmpty()) {
            throw new IllegalArgumentException(
                "Cannot delete permission. It is being used by " + 
                rolesUsingPermission.size() + " role(s)"
            );
        }
        
        repository.deleteById(id);
        return true;
    }
}