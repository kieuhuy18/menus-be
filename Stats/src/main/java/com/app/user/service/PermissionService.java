package com.app.user.service;

import com.app.user.entity.Permission;
import com.app.user.entity.Role;
import com.app.user.repository.PermissionRepository;
import com.app.user.repository.RoleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

    // Tạo permission mới, kiểm tra trùng tên
    public Permission create(Permission p) {
        if (p.getNamePermission() != null && repository.findByNamePermission(p.getNamePermission()).isPresent()) {
            throw new IllegalArgumentException("Permission name already exists");
        }
        p.setId(null);
        p.setCreateAt(new Date());
        return repository.save(p);
    }

    // Lấy danh sách permission theo phân trang
    public Page<Permission> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    // Tìm permission theo ID
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

    // Xóa permission, kiểm tra xem có role nào đang sử dụng không
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