package com.app.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {
    private String fullName;
    private String userEmail;
    private String phoneNumber;
    private String userGender;
    private String userAddress;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate userBirthday;
    
}
