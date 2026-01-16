package com.app.user.dto;

import com.app.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserListResponse {
    private String id;
    private String userName;
    private String userEmail;
    private String fullName;   
    private String phoneNumber;
    private Boolean active;

    public static UserListResponse fromEntity(User user) {
        return UserListResponse.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .userEmail(user.getUserEmail())
                .fullName(user.getFullName())  
                .phoneNumber(user.getPhoneNumber())
                .active(user.getUserActive())
                .build();
    }
}
