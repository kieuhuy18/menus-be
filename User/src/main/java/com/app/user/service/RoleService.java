package com.app.user.service;

import com.app.user.dto.ResponseStatus;
import com.app.user.dto.RoleRequestDTO;
import com.app.user.entity.Role;
import com.app.user.exception.BusinessException;
import com.app.user.repository.RoleRepository;
import com.app.user.repository.PermissionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.Date;
import java.util.Set;

@Service
public class RoleService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public RoleService(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    // Tạo mới role, kiểm tra trùng tên và permissions tồn tại
    public Role create(RoleRequestDTO request) {
        if (!StringUtils.hasText(request.getNameRole())) {
            throw new BusinessException(ResponseStatus.INVALID_PARAM, "Role name is required");
        }
        
        // trùng tên role
        if (roleRepository.findByNameRole(request.getNameRole()).isPresent()) {
            throw new BusinessException(ResponseStatus.EXISTS, "Role name already exists");
        }
        //  kiểm tra permissionIds có tồn tại không
        if (request.getPermissionIds() != null && !request.getPermissionIds().isEmpty()) {
            validatePermissionIds(request.getPermissionIds());
        }
        
        Role role = Role.builder()
                .nameRole(request.getNameRole())
                .description(request.getDescription())
                .permissionIds(request.getPermissionIds())
                .createAt(new Date())
                .updateAt(new Date())
                .build();
        
        return roleRepository.save(role);
    }
// Lấy danh sách role có phân trang
    public Page<Role> findAll(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }

    // Tìm role theo ID
    public Role findById(String id) {
        return roleRepository.findById(id).orElseThrow(() -> new BusinessException(ResponseStatus.NOT_FOUND, "Role not found") );
    }

    public Role update(String id, RoleRequestDTO request) {
        Role existing = roleRepository.findById(id).orElseThrow(() -> new BusinessException(ResponseStatus.NOT_FOUND, "Role not found") );
        
        // kiểm tra trùng tên role nếu có thay đổi
        if (StringUtils.hasText(request.getNameRole())) {
            if (!request.getNameRole().equals(existing.getNameRole()) &&
                roleRepository.findByNameRole(request.getNameRole()).isPresent()) {
                throw new BusinessException(ResponseStatus.EXISTS, "Role name already exists");
            }
            existing.setNameRole(request.getNameRole());
        }
        
        
        if (request.getDescription() != null) {
            existing.setDescription(request.getDescription());
        }
        
        if (request.getPermissionIds() != null) {
            if (!request.getPermissionIds().isEmpty()) {
                validatePermissionIds(request.getPermissionIds());
            }
            existing.setPermissionIds(request.getPermissionIds());
        }
        
        existing.setUpdateAt(new Date());
        return roleRepository.save(existing);
    }

    // Xóa role
    public void delete(String id) {
        if (!roleRepository.existsById(id)){
            throw new BusinessException(ResponseStatus.NOT_FOUND, "Role not found");
        }
        
        
        roleRepository.deleteById(id);
    }
    
    
    // Helper: Kiểm tra danh sách permission có tồn tại không
    private void validatePermissionIds(Set<String> permissionIds) {
        for (String permissionId : permissionIds) {
            if (!permissionRepository.existsById(permissionId)) {
                throw new BusinessException(ResponseStatus.INVALID_PARAM, "Permission not found: " + permissionId);
            }
        }
    }
}

