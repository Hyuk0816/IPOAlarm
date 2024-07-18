package com.sideprj.ipoAlarm.domain.user.mapper;

import com.sideprj.ipoAlarm.domain.user.dto.UserDto;
import com.sideprj.ipoAlarm.domain.user.entity.User;
import com.sideprj.ipoAlarm.domain.user.role.ROLE;
import com.sideprj.ipoAlarm.domain.user.vo.UserDetailsRequestVo;

public class UserMapper {

    public static User mapToUsers(UserDto usersDto) {

        return User.builder()
                .email(usersDto.getEmail())
                .password(usersDto.getPassword())
                .image(usersDto.getImage())
                .role(ROLE.USER)
                .build();
    }

    public static User mapToUsersDetailsRequestVo(UserDetailsRequestVo userDetailsRequestVo) {
        return User.builder()
                .email(userDetailsRequestVo.getEmail())
                .password(userDetailsRequestVo.getPassword())
                .image(userDetailsRequestVo.getImage())
                .build();

    }
}

