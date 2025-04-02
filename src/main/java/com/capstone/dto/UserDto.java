package com.capstone.dto;

import com.capstone.security.UserRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {

    private String loginId;
    private String password;
    private UserRole role;

}
