package com.app.user.service;

import com.app.user.entity.User;
import com.app.user.repository.RoleRepository;
import com.app.user.repository.UserRepository;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.data.domain.Pageable;
import java.util.Date;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    // Tạo mới user, kiểm tra trùng username/email và role tồn tại
    public User create(User u) {
        if (u.getUserName() != null && userRepository.findByUserName(u.getUserName()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (u.getUserEmail() != null && userRepository.findByUserEmail(u.getUserEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }
        if (StringUtils.hasText(u.getRoleId()) && !roleRepository.existsById(u.getRoleId())) {
            throw new IllegalArgumentException("Not found role");
        }
        u.setId(null);
        u.setUserActive(u.getUserActive() == null ? Boolean.TRUE : u.getUserActive());
        u.setUserCreateAt(new Date());
        u.setUserUpdateAt(new Date());
        return userRepository.save(u);
    }

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    // Tìm user theo ID
    public User findById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    public User update(String id, User update) {
        User existing = userRepository.findById(id).orElse(null);
        if (existing == null) return null;

        if (StringUtils.hasText(update.getUserFullName())) existing.setUserFullName(update.getUserFullName());
        if (StringUtils.hasText(update.getUserPhonenumber())) existing.setUserPhonenumber(update.getUserPhonenumber());
        if (update.getUserBirthday() != null) existing.setUserBirthday(update.getUserBirthday());
        if (StringUtils.hasText(update.getUserGender())) existing.setUserGender(update.getUserGender());
        if (update.getUserAddress() != null) existing.setUserAddress(update.getUserAddress());

        if (StringUtils.hasText(update.getUserEmail())) {
            if (!update.getUserEmail().equals(existing.getUserEmail()) &&
                userRepository.findByUserEmail(update.getUserEmail()).isPresent()) {
                throw new IllegalArgumentException("Email already exists");
            }
            existing.setUserEmail(update.getUserEmail());
        }
        if (StringUtils.hasText(update.getUserAvatar())) existing.setUserAvatar(update.getUserAvatar());
        if (update.getUserActive() != null) existing.setUserActive(update.getUserActive());

        if (StringUtils.hasText(update.getUserName())) {
            if (!update.getUserName().equals(existing.getUserName()) &&
                userRepository.findByUserName(update.getUserName()).isPresent()) {
                throw new IllegalArgumentException("Username already exists");
            }
            existing.setUserName(update.getUserName());
        }
        if (StringUtils.hasText(update.getPassword())) existing.setPassword(update.getPassword());

        if (StringUtils.hasText(update.getRoleId())) {
            if (!roleRepository.existsById(update.getRoleId())) {
                throw new IllegalArgumentException("Not found role");
            }
            existing.setRoleId(update.getRoleId());
        }

        existing.setUserUpdateAt(new Date());
        return userRepository.save(existing);
    }

    // Xóa user theo ID
    public boolean delete(String id) {
        if (!userRepository.existsById(id)) return false;
        userRepository.deleteById(id);
        return true;
    }
}
