package com.sideprj.ipoAlarm.domain.oauth.service.impl;

import com.sideprj.ipoAlarm.domain.oauth.entity.KakaoToken;
import com.sideprj.ipoAlarm.domain.oauth.kakao.client.KakaoOauthAPi;
import com.sideprj.ipoAlarm.domain.oauth.kakao.client.KakaoResourceApi;
import com.sideprj.ipoAlarm.domain.oauth.kakao.dto.KakaoResourceDto;
import com.sideprj.ipoAlarm.domain.oauth.kakao.dto.KakaoTokenDto;
import com.sideprj.ipoAlarm.domain.oauth.kakao.dto.KakaoUserInfoDto;
import com.sideprj.ipoAlarm.domain.oauth.mapper.Oauth2Mapper;
import com.sideprj.ipoAlarm.domain.oauth.repository.KakaoTokenRepository;
import com.sideprj.ipoAlarm.domain.oauth.service.Oauth2Service;
import com.sideprj.ipoAlarm.domain.user.constants.UserConstants;
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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
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
    private final KakaoTokenRepository kakaoTokenRepository;
    private final RedisTemplate<String,String> redisTemplate;


    @Override
    public KakaoResourceDto getUserInfo(String accessToken, String registration) {
        String resourceUri = env.getProperty("spring.security.oauth2.client.provider." + registration + ".user-info-uri");
        log.info(accessToken);
        return kakaoResourceApi.kakaoGetResource("Bearer " + accessToken);
    }

    @Override
    public Map<String,String> getAccessToken(String authorizationCode, String registration) {
        String clientId = env.getProperty("spring.security.oauth2.client.registration." + registration + ".client-id");
        String clientSecret = env.getProperty("spring.security.oauth2.client.registration." + registration + ".client-secret");
        String redirectUri = env.getProperty("spring.security.oauth2.client.registration." + registration + ".redirect-uri");
        String tokenUri = env.getProperty("spring.security.oauth2.client.provider." + registration + ".token-uri");

        KakaoTokenDto tokenDto = kakaoOauthAPi.kakaogetToken(authorizationCode,clientId,redirectUri,clientSecret,"authorization_code");
        String accessToken = tokenDto.getAccessToken();
        String refreshToken = tokenDto.getRefreshToken();
        Map<String,String> tokenMap = new HashMap<>();
        tokenMap.put("access_token",accessToken);
        tokenMap.put("refresh_token",refreshToken);
        return tokenMap;
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

    public KakaoToken checkKakaoTokenExists(Long userId) {
        return kakaoTokenRepository.findByUserUserId(userId);
    }

//    @Override
//    public void socialLogin(String code, String registration, HttpServletResponse response) throws BadRequestException {
//        Map<String, String> mapToken = getAccessToken(code, registration);
//        String accessToken = mapToken.get("access_token");
//        String refreshToken = mapToken.get("refresh_token");
//
//        KakaoResourceDto userInfoDto = getUserInfo(accessToken, registration);
//        KakaoUserInfoDto kakaoUserInfoDto = Oauth2Mapper.mapFromKakaoResourceDtoToKakaoUserInfoDto(userInfoDto);
//
//        if (!checkUserExists(kakaoUserInfoDto.getEmail())){
//            socialSignIn(kakaoUserInfoDto.getEmail(), kakaoUserInfoDto.getId(), kakaoUserInfoDto.getImage());
//        }
//
//        User user = userRepository.findByEmail(kakaoUserInfoDto.getEmail()).orElseThrow(() -> new UsernameNotFoundException(UserConstants.MESSAGE_404));
//
//        if(checkKakaoTokenExists(user.getUserId())!=null){
//            KakaoToken existsToken = checkKakaoTokenExists(user.getUserId());
//            //쿼리 DSL 방식이랑 고민중,,,,,
//            //아니면 아예 토큰을 redis에 저장시켜야 하나 고민 중,,, userAccess : access value , userRefresh : refresh value --> 이런식,,,
//            existsToken.setAccessToken(accessToken);
//            existsToken.setRefreshToken(refreshToken);
//            kakaoTokenRepository.save(existsToken);
//            LoginDto loginDto = Oauth2Mapper.mapFromKakaoResourceDtoToLoginDto(kakaoUserInfoDto);
//            authService.login(loginDto, response);
//        }else{
//
//            KakaoToken kakaoToken = KakaoToken.builder()
//                    .accessToken(accessToken)
//                    .refreshToken(refreshToken)
//                    .user(user)
//                    .build();
//
//            LoginDto loginDto = Oauth2Mapper.mapFromKakaoResourceDtoToLoginDto(kakaoUserInfoDto);
//            kakaoTokenRepository.save(kakaoToken);
//            authService.login(loginDto, response);
//        }
//    }

    public boolean checkRedis(String email) {
        return redisTemplate.opsForValue().get(email+"-kakao_access") == null && redisTemplate.opsForValue().get(email + "-kakao_refresh")== null ;
    }

    @Override
    public void socialLogin(String code, String registration, HttpServletResponse response) throws BadRequestException {
        Map<String, String> mapToken = getAccessToken(code, registration);
        String accessToken = mapToken.get("access_token");
        String refreshToken = mapToken.get("refresh_token");

        KakaoResourceDto userInfoDto = getUserInfo(accessToken, registration);

        KakaoUserInfoDto kakaoUserInfoDto = Oauth2Mapper.mapFromKakaoResourceDtoToKakaoUserInfoDto(userInfoDto);

        if (!checkUserExists(kakaoUserInfoDto.getEmail())){
            socialSignIn(kakaoUserInfoDto.getEmail(), kakaoUserInfoDto.getId(), kakaoUserInfoDto.getImage());
        }

        User user = userRepository.findByEmail(kakaoUserInfoDto.getEmail()).orElseThrow(() -> new UsernameNotFoundException(UserConstants.MESSAGE_404));

        String accessKey = user.getEmail() + "-kakao_access";
        String refreshKey = user.getEmail() + "-kakao_refresh";

        if(checkRedis(user.getEmail())){
            redisTemplate.opsForValue().set(accessKey, "Bearer "+accessToken);
            redisTemplate.opsForValue().set(refreshKey, "Bearer "+refreshToken);
            LoginDto loginDto = Oauth2Mapper.mapFromKakaoResourceDtoToLoginDto(kakaoUserInfoDto);
            authService.login(loginDto, response);
        }else{
            redisTemplate.opsForValue().set(accessKey, "Bearer "+accessToken);
            redisTemplate.opsForValue().set(refreshKey, "Bearer "+refreshToken);
            LoginDto loginDto = Oauth2Mapper.mapFromKakaoResourceDtoToLoginDto(kakaoUserInfoDto);
            authService.login(loginDto, response);
        }
    }
}
