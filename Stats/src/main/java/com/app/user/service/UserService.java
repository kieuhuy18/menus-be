package com.app.user.service;

import com.app.user.entity.User;
import com.app.user.exception.DuplicateFieldException;
import com.app.user.exception.InvalidFieldException;
import com.app.user.exception.ResourceNotFoundException;
import com.app.user.repository.RoleRepository;
import com.app.user.repository.UserRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    // validate các trường của user , nếu sai thì ném InvalidFieldException
    private void validateUserFields(User u) {
        if (u.getFullName() == null || u.getFullName().isBlank()) {
            throw new InvalidFieldException("Full name cannot be empty");
        }

        if (u.getUserEmail() != null && !u.getUserEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new InvalidFieldException("Invalid email format");
        }

        if (u.getPhoneNumber() != null && !u.getPhoneNumber().matches("^0\\d{9,10}$")) {
            throw new InvalidFieldException("Invalid phone number");
        }

        if (u.getUserGender() != null && !u.getUserGender().matches("(?i)^(male|female|other)$")) {
            throw new InvalidFieldException("Gender must be male, female or other");
        }
    }

    //tạo user
    public User create(User u) {
        validateUserFields(u);

        // Check trùng username/email/phone
        if (u.getUserName() != null && userRepository.findByUserName(u.getUserName()).isPresent()) {
            throw new DuplicateFieldException("Username already exists");
        }

        if (u.getUserEmail() != null && userRepository.findByUserEmail(u.getUserEmail()).isPresent()) {
            throw new DuplicateFieldException("Email already exists");
        }

        if (u.getPhoneNumber() != null && userRepository.findByPhoneNumber(u.getPhoneNumber()).isPresent()) {
            throw new DuplicateFieldException("Phone number already exists");
        }

        // Kiểm tra role tồn tại
        if (StringUtils.hasText(u.getRoleId()) && !roleRepository.existsById(u.getRoleId())) {
            throw new ResourceNotFoundException("Role not found");
        }

        u.setId(null);
        u.setUserActive(u.getUserActive() == null ? Boolean.TRUE : u.getUserActive());
        u.setUserCreateAt(new Date());
        u.setUserUpdateAt(new Date());

        return userRepository.save(u);
    }

    // update user
    public User update(String id, User update) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        validateUserFields(update);

        if (StringUtils.hasText(update.getFullName())) existing.setFullName(update.getFullName());
        if (StringUtils.hasText(update.getPhoneNumber())) {
            Optional<User> byPhone = userRepository.findByPhoneNumber(update.getPhoneNumber());
            if (byPhone.isPresent() && !byPhone.get().getId().equals(id)) {
                throw new DuplicateFieldException("Phone number already exists");
            }
            existing.setPhoneNumber(update.getPhoneNumber());
        }

        if (update.getUserBirthday() != null) existing.setUserBirthday(update.getUserBirthday());
        if (StringUtils.hasText(update.getUserGender())) existing.setUserGender(update.getUserGender());
        if (update.getUserAddress() != null) existing.setUserAddress(update.getUserAddress());

        if (StringUtils.hasText(update.getUserEmail())) {
            Optional<User> byEmail = userRepository.findByUserEmail(update.getUserEmail());
            if (byEmail.isPresent() && !byEmail.get().getId().equals(id)) {
                throw new DuplicateFieldException("Email already exists");
            }
            existing.setUserEmail(update.getUserEmail());
        }

        if (StringUtils.hasText(update.getUserAvatar())) existing.setUserAvatar(update.getUserAvatar());
        if (update.getUserActive() != null) existing.setUserActive(update.getUserActive());

        if (StringUtils.hasText(update.getUserName())) {
            Optional<User> byUsername = userRepository.findByUserName(update.getUserName());
            if (byUsername.isPresent() && !byUsername.get().getId().equals(id)) {
                throw new DuplicateFieldException("Username already exists");
            }
            existing.setUserName(update.getUserName());
        }

        if (StringUtils.hasText(update.getPassword())) existing.setPassword(update.getPassword());

        if (StringUtils.hasText(update.getRoleId())) {
            if (!roleRepository.existsById(update.getRoleId())) {
                throw new ResourceNotFoundException("Role not found");
            }
            existing.setRoleId(update.getRoleId());
        }

        existing.setUserUpdateAt(new Date());
        return userRepository.save(existing);
    }

    // lấy danh sách user với phân trang
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    // lấy user theo id
    public User findById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    // xóa user theo id
    public boolean delete(String id) {
        if (!userRepository.existsById(id)) return false;
        userRepository.deleteById(id);
        return true;
    }
}
