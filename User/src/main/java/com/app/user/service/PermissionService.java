package com.app.user.service;

import com.app.user.dto.PermissionRequestDTO;
import com.app.user.dto.PermissionResponseDTO;
import com.app.user.dto.ResponseStatus;
import com.app.user.entity.Permission;
import com.app.user.entity.Role;
import com.app.user.repository.PermissionRepository;
import com.app.user.repository.RoleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.app.user.exception.BusinessException;


import org.springframework.stereotype.Repository;
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
    public Permission create(PermissionRequestDTO dto) {
        System.out.println(">>> PermissionService.create called");

        if (!StringUtils.hasText(dto.getNamePermission())) {
        throw new BusinessException(ResponseStatus.INVALID_PARAM, "Permission name is required");
    }
        
        
        if (repository.findByNamePermission(dto.getNamePermission()).isPresent()) {
            throw new BusinessException(ResponseStatus.EXISTS, "Role name already exists");
        }
        Permission p = Permission.builder()
                .namePermission(dto.getNamePermission())
                .description(dto.getDescription())
                .resource(dto.getResource())
                .action(dto.getAction())
                .createAt(new Date())
        
                .build();
        return repository.save(p);
    }

    // Lấy danh sách permission theo phân trang
    public Page<Permission> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    // Tìm permission theo ID
    public Permission findById(String id) {
        return repository.findById(id).orElseThrow(() -> new BusinessException(ResponseStatus.NOT_FOUND, "Permission not found"));
    }

    public Permission update(String id, PermissionRequestDTO dto) {
        Permission existing = repository.findById(id).orElseThrow(() -> new BusinessException(ResponseStatus.NOT_FOUND, "Permission not found"));
        
        if(StringUtils.hasText(dto.getNamePermission())
            && !dto.getNamePermission().equals(existing.getNamePermission())) {
            // kiểm tra trùng tên
            if (repository.findByNamePermission(dto.getNamePermission()).isPresent()) {
                throw new BusinessException(ResponseStatus.EXISTS, "Permission name already exists");
            }
            existing.setNamePermission(dto.getNamePermission());
        }
          
        if (dto.getDescription() != null) existing.setDescription(dto.getDescription());
        if (dto.getResource() != null) existing.setResource(dto.getResource());
        if (dto.getAction() != null) existing.setAction(dto.getAction());
        return repository.save(existing);
    }

    // Xóa permission, kiểm tra xem có role nào đang sử dụng không
    public void delete(String id) {
        Permission permission = repository.findById(id)
                .orElseThrow(() -> new BusinessException(ResponseStatus.NOT_FOUND, "Permission not found"));
       
        
        List<Role> roles = roleRepository.findByPermissionIdsContaining(id);
        if (!roles.isEmpty()) {
            throw new BusinessException(ResponseStatus.INVALID_PARAM,
                "Permission is being used by roles, cannot delete " 
                
            );
        }
        repository.delete(permission);
        }
        

    }
