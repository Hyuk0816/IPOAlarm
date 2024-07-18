package com.sideprj.ipoAlarm.domain.user.vo;

import com.sideprj.ipoAlarm.domain.user.role.ROLE;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
@Schema(
        name = "Response",
        description = "Schema to hold successful response Users Detail information"
)
@Data
@Builder
@AllArgsConstructor
public class UserDetailsRequestVo {


    @Email
    @NotEmpty(message = "Email cannot be null or empty")
    @Schema(description = "Email is used for login", example = "test@test.com")
    private String email;

    @NotEmpty(message = "Password cannot be null or empty")
    @Schema(description = "User's password", example = "password123")
    private String password;

    @Schema(description = "User's image", example = "s3 link")
    private String image;

    private String  role;


}
