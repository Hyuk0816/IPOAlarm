package com.sideprj.ipoAlarm.domain.user.service.impl;

import com.sideprj.ipoAlarm.domain.user.constants.UserConstants;
import com.sideprj.ipoAlarm.domain.user.entity.User;
import com.sideprj.ipoAlarm.domain.user.mapper.UserMapper;
import com.sideprj.ipoAlarm.domain.user.repository.UserRepository;
import com.sideprj.ipoAlarm.domain.user.role.ROLE;
import com.sideprj.ipoAlarm.domain.user.service.UserService;
import com.sideprj.ipoAlarm.domain.user.vo.UserDetailsRequestVo;
import com.sideprj.ipoAlarm.util.exception.UsersAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void createUsers(UserDetailsRequestVo userDetailsRequestVo) {

        var checkUserVo = UserDetailsRequestVo.builder()
                .email(userDetailsRequestVo.getEmail())
                .password(passwordEncoder.encode(userDetailsRequestVo.getPassword()))
                .image(userDetailsRequestVo.getImage())
                .build();

        Optional<User> optionalUsers = userRepository.findByEmail(checkUserVo.getEmail());
        if (optionalUsers.isPresent()) {
            throw new UsersAlreadyExistsException(UserConstants.MESSAGE_ALREADY_EXISTS);
        }

        User user = UserMapper.mapToUsersDetailsRequestVo(checkUserVo);
        user.setRole(ROLE.USER);
        userRepository.save(user);
    }

}
