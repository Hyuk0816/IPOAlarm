package com.sideprj.ipoAlarm.domain.oauth.kakao.client;

import com.sideprj.ipoAlarm.domain.oauth.kakao.dto.KakaoTokenDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(
        name = "KakaoOauth",
        url = "https://kauth.kakao.com"
)
public interface KakaoOauthAPi {
    @PostMapping(
            value = "/oauth/token?" +
                    "code={CODE}" +
                    "&client_id={CLIENT_ID}" +
                    "&redirect_uri={REDIRECT_URI}" +
                    "&client_secret={CLIENT_SECRET}" +
                    "&grant_type={GRANT_TYPE}")
    KakaoTokenDto kakaogetToken(@PathVariable("CODE") String code,
                                @PathVariable("CLIENT_ID") String clientId,
                                @PathVariable("REDIRECT_URI") String redirectUri,
                                @PathVariable("CLIENT_SECRET") String clientSecret,
                                @PathVariable("GRANT_TYPE") String grantType);

}
