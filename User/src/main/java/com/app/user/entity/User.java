package com.app.user.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Document("users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private String id;
    
    @Indexed(unique = true)
    private String userName;
    
    @Indexed(unique = true)
    private String userEmail;
    
    private String password;
    private String userFullName;
    private String userPhonenumber;
    private Date userBirthday;
    private String userGender;
    private String userAddress;
    private String userAvatar;
    private Boolean userActive;
    private String roleId;
    private Date userCreateAt;
    private Date userUpdateAt;
}
