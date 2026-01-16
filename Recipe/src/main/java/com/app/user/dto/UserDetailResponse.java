package com.app.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailResponse {
    private String id;
    private String userName;
    private String userFullName;
    private String userEmail;
    private String userPhonenumber;
    private String userGender;
   
    private LocalDate userBirthday;
    private String userAddress;
    private Boolean active;

    public static UserDetailResponse fromEntity(com.app.user.entity.User user) {
        return UserDetailResponse.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .userFullName(user.getFullName())
                .userEmail(user.getUserEmail())
                .userPhonenumber(user.getPhoneNumber())
                .userGender(user.getUserGender())
                .userBirthday(user.getUserBirthday())
                .userAddress(user.getUserAddress())
                .active(user.getUserActive())
                .build();
    }
}
