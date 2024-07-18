package com.sideprj.ipoAlarm.domain.user.dto;

import com.sideprj.ipoAlarm.domain.user.role.ROLE;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDto {

    private Long UserId;

    @Email
    @NotEmpty(message = "Email cannot be null or empty")
    private String email;

    @NotEmpty(message = "Password cannot be null or empty")
    private String password;

    private String image;

    private String role;
}
