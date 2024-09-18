package com.sideprj.ipoAlarm.util.jwt;

import com.sideprj.ipoAlarm.domain.user.repository.UserRepository;
import com.sideprj.ipoAlarm.domain.user.userDetails.CustomUserDetails;
import com.sideprj.ipoAlarm.util.exception.APIException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;
import java.util.UUID;

import static org.springframework.util.StringUtils.hasText;

@Component
@RequiredArgsConstructor
@Slf4j
public class TokenProvider {

    @Value("${app.jwt-secret}")
    private String secretKey;
    @Value("${app.jwt-expiration}")
    private Long accessTokenValid;

    private final RedisTemplate<String, String> redisTemplate;
    private final UserRepository userRepository;

    public SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String publishAccessToken(Authentication authentication) {
        Date now = new Date();
        Date accessValidDate = new Date(now.getTime() + accessTokenValid);
        return Jwts.builder()
                .claim("email", authentication.getName())
                .claim("role", authentication.getAuthorities())
                .issuedAt(now)
                .expiration(accessValidDate)
                .signWith(getSignKey())
                .compact();
    }

    public String createRefreshToken(Authentication authentication) {
        UUID refreshTokenUUID = UUID.randomUUID();
        redisTemplate.opsForValue().set(refreshTokenUUID.toString(), authentication.getName(), Duration.ofDays(1));
        return String.valueOf(refreshTokenUUID);
    }

    public String createAccessToken(Authentication authentication) {
        userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String accessToken = publishAccessToken(authentication);
        redisTemplate.opsForValue().set(authentication.getName(), accessToken, Duration.ofMinutes(30));
        return accessToken;
    }

    public String searchAccessTokenByRefreshToken(String refreshToken) {
        String email = redisTemplate.opsForValue().get(refreshToken);
        if (hasText(email)) {
            String accessToken = redisTemplate.opsForValue().get(email);
            if (hasText(accessToken)) {
                return accessToken;
            } else {
                redisTemplate.delete(refreshToken);
            }
        } else {
            redisTemplate.delete(refreshToken);
        }
        return null;
    }


    public void searchAccessTokenByEmail(Authentication authentication) {
        String email = authentication.getName();
        String accessToken = redisTemplate.opsForValue().get(email);

        if (hasText(accessToken)) {
            redisTemplate.delete(email);
        }
        createAccessToken(authentication);
        createRefreshToken(authentication);
    }

    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return String.valueOf(claims.get("email"));
    }


    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(getSignKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            Date now = new Date();
            return claims.getExpiration().after(now);
        } catch (MalformedJwtException ex) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Invaild JWT token");
        } catch (ExpiredJwtException ex) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new APIException(HttpStatus.BAD_REQUEST, "JWT claims string is empty");
        }
    }

    public void createRefreshTokenCookie(HttpServletResponse response, String refreshTokenKey, String refreshTokenValue, long maxAgeForCookie) {
        ResponseCookie cookie = ResponseCookie.from(refreshTokenKey, refreshTokenValue)
                .path("/")
                .httpOnly(true)
                .maxAge(maxAgeForCookie)
                .sameSite("Strict")
                .secure(true)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    public void deleteRefreshTokenCookie(HttpServletResponse response, String refreshTokenKey) {
        createRefreshTokenCookie(response, refreshTokenKey, "", 0);
    }
}

