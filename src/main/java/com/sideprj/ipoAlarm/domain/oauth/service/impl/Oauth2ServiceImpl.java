package com.sideprj.ipoAlarm.domain.oauth.service.impl;

import com.sideprj.ipoAlarm.domain.oauth.kakao.client.KakaoOauthAPi;
import com.sideprj.ipoAlarm.domain.oauth.kakao.client.KakaoResourceApi;
import com.sideprj.ipoAlarm.domain.oauth.kakao.dto.KakaoResourceDto;
import com.sideprj.ipoAlarm.domain.oauth.kakao.dto.KakaoTokenDto;
import com.sideprj.ipoAlarm.domain.oauth.kakao.dto.KakaoUserInfoDto;
import com.sideprj.ipoAlarm.domain.oauth.mapper.Oauth2Mapper;
import com.sideprj.ipoAlarm.domain.oauth.service.Oauth2Service;
import com.sideprj.ipoAlarm.domain.user.dto.LoginDto;
import com.sideprj.ipoAlarm.domain.user.entity.User;
import com.sideprj.ipoAlarm.domain.user.repository.UserRepository;
import com.sideprj.ipoAlarm.domain.user.service.AuthService;
import com.sideprj.ipoAlarm.domain.user.service.UserService;
import com.sideprj.ipoAlarm.domain.user.vo.UserDetailsRequestVo;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class Oauth2ServiceImpl implements Oauth2Service {

    private final Environment env;

    private final UserRepository userRepository;
    private final UserService userService;
    private final KakaoResourceApi kakaoResourceApi;
    private final KakaoOauthAPi kakaoOauthAPi;
    private final AuthService authService;



    @Override
    public KakaoResourceDto getUserInfo(String accessToken, String registration) {
        String resourceUri = env.getProperty("spring.security.oauth2.client.provider." + registration + ".user-info-uri");
        return kakaoResourceApi.kakaoGetResource("Bearer " + accessToken);
    }

    @Override
    public String getAccessToken(String authorizationCode, String registration) {
        String clientId = env.getProperty("spring.security.oauth2.client.registration." + registration + ".client-id");
        String clientSecret = env.getProperty("spring.security.oauth2.client.registration." + registration + ".client-secret");
        String redirectUri = env.getProperty("spring.security.oauth2.client.registration." + registration + ".redirect-uri");
        String tokenUri = env.getProperty("spring.security.oauth2.client.provider." + registration + ".token-uri");

        KakaoTokenDto tokenDto = kakaoOauthAPi.kakaogetToken(authorizationCode,clientId,redirectUri,clientSecret,"authorization_code");
        return tokenDto.getAccessToken();
    }

    @Override
    public void socialSignIn(String email, String password, String image) {

        UserDetailsRequestVo userVo = UserDetailsRequestVo.builder()
                .email(email)
                .password(password)
                .image(image)
                .build();

        userService.createUsers(userVo);
    }

    public boolean checkUserExists(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent();
    }

    @Override
    @Transactional
    public void socialLogin(String code, String registration, HttpServletResponse response) throws BadRequestException {
        String accessToken = getAccessToken(code, registration);
        KakaoResourceDto userInfoDto = getUserInfo(accessToken, registration);
        KakaoUserInfoDto kakaoUserInfoDto = Oauth2Mapper.mapFromKakaoResourceDtoToKakaoUserInfoDto(userInfoDto);

        if (!checkUserExists(kakaoUserInfoDto.getEmail())){
            socialSignIn(kakaoUserInfoDto.getEmail(), kakaoUserInfoDto.getId(), kakaoUserInfoDto.getImage());
        }
        LoginDto loginDto = Oauth2Mapper.mapFromKakaoResourceDtoToLoginDto(kakaoUserInfoDto);
        authService.login(loginDto, response);

    }
}
