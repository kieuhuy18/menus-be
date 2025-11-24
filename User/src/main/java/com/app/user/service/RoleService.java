package com.app.user.service;

import com.app.user.entity.Role;
import com.app.user.repository.RoleRepository;
import com.app.user.repository.PermissionRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class RoleService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public RoleService(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    public Role create(Role role) {
        if (role.getNameRole() != null && roleRepository.findByNameRole(role.getNameRole()).isPresent()) {
            throw new IllegalArgumentException("Role name already exists");
        }
        
        if (role.getPermissionIds() != null && !role.getPermissionIds().isEmpty()) {
            validatePermissionIds(role.getPermissionIds());
        }
        
        role.setId(null);
        role.setCreateAt(new Date());
        role.setUpdateAt(new Date());
        return roleRepository.save(role);
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public Role findById(String id) {
        return roleRepository.findById(id).orElse(null);
    }

    public Role update(String id, Role update) {
        Role existing = roleRepository.findById(id).orElse(null);
        if (existing == null) return null;
        
        if (StringUtils.hasText(update.getNameRole())) {
            if (!update.getNameRole().equals(existing.getNameRole()) &&
                roleRepository.findByNameRole(update.getNameRole()).isPresent()) {
                throw new IllegalArgumentException("Role name already exists");
            }
            existing.setNameRole(update.getNameRole());
        }
        
        if (update.getDescription() != null) {
            existing.setDescription(update.getDescription());
        }
        
        if (update.getPermissionIds() != null) {
            if (!update.getPermissionIds().isEmpty()) {
                validatePermissionIds(update.getPermissionIds());
            }
            existing.setPermissionIds(update.getPermissionIds());
        }
        
        existing.setUpdateAt(new Date());
        return roleRepository.save(existing);
    }

    public boolean delete(String id) {
        if (!roleRepository.existsById(id)) return false;
        
        
        roleRepository.deleteById(id);
        return true;
    }
    
    private void validatePermissionIds(Set<String> permissionIds) {
        for (String permissionId : permissionIds) {
            if (!permissionRepository.existsById(permissionId)) {
                throw new IllegalArgumentException("Permission not found: " + permissionId);
            }
        }
    }
}

