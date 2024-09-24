package com.sideprj.ipoAlarm.domain.user.controller;

import com.sideprj.ipoAlarm.domain.user.constants.UserConstants;
import com.sideprj.ipoAlarm.domain.user.service.UserService;
import com.sideprj.ipoAlarm.domain.user.vo.UserDetailsRequestVo;
import com.sideprj.ipoAlarm.domain.user.vo.UserStatusResponseVo;
import com.sideprj.ipoAlarm.util.error.ErrorResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(
        name = "CRUD REST APIs for Users",
        description = "CRUD REST APIs in Users to CREATE, UPDATE, FETCH AND DELETE"
)
@RestController
@RequestMapping(path = "/api/user", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "Create User REST API",
            description = "REST API to create new User"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PostMapping(path = "/registration")
    public ResponseEntity<UserStatusResponseVo> createUsers(@Valid @RequestBody UserDetailsRequestVo usersDetailsRequestVo) {
        userService.createUsers(usersDetailsRequestVo);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new UserStatusResponseVo(UserConstants.STATUS_201, UserConstants.MESSAGE_201));
    }
    @Operation(
            summary = "Update User NickName REST API",
            description = "REST API to Update User NickName REST API"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PutMapping(path = "/nickName")
    public ResponseEntity<UserStatusResponseVo> putNickName(@RequestParam String nickName) {
        userService.updateNickName(nickName);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new UserStatusResponseVo(UserConstants.STATUS_201, UserConstants.MESSAGE_PUT_NICKNAME));
    }

    @Operation(
            summary = "Update User Profile REST API",
            description = "REST API to Update User Profile REST API"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PutMapping(value = "/profile", consumes = {"multipart/form-data"})
    public ResponseEntity<UserStatusResponseVo> putProfile(@RequestPart(value = "file", required = false) MultipartFile file) throws FileUploadException {
        userService.updateProfile(file);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new UserStatusResponseVo(UserConstants.STATUS_201, UserConstants.MESSAGE_PUT_PROFILE));
    }

    @Operation(
            summary = "NickName Check",
            description = "NickName Check"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )

    @GetMapping(value = "/nick_name_check")
    public ResponseEntity<UserStatusResponseVo> checkNickName(@RequestParam("nickName") String nickName) {
        userService.nickNameCheck(nickName);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new UserStatusResponseVo(UserConstants.STATUS_200, UserConstants.NICKNAME_CHECK_OK));
    }

}
