package com.app.user.dto;

import com.app.user.entity.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)



public class UserResponse {

    private String id;
    private String userName;
    private String userEmail;
    private String fullName;
    private String phoneNumber;
    private Boolean active;

    // detail only
    private String userGender;
    private LocalDate userBirthday;
    private String userAddress;

    public static UserResponse fromEntity(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .userEmail(user.getUserEmail())
                .fullName(user.getFullName())
                .phoneNumber(user.getPhoneNumber())
                .active(user.getUserActive())
                .userGender(user.getUserGender())
                .userBirthday(user.getUserBirthday())
                .userAddress(user.getUserAddress())
                .build();
    }

    
}
