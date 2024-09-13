package unius.system_oauth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;
import unius.system_oauth.dto.GoogleInfoDto;
import unius.system_oauth.dto.KakaoInfoDto;
import unius.system_oauth.dto.OAuthTokenDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthService {

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String GOOGLE_CLIENT_ID;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String GOOGLE_CLIENT_SECRET;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String GOOGLE_REDIRECT_URI;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String KAKAO_CLIENT_ID;

    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String KAKAO_CLIENT_SECRET;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String KAKAO_REDIRECT_URI;

    private final RestTemplate restTemplate;

    public String getGoogleAuthCode(String code) {
        String oauthToken = getGoogleToken(code);

        return getGoogleOAuthId(oauthToken);
    }

    public String getKakaoAuthCode(String code) {
        String oauthToken = getKakaoToken(code);

        return getKakaoOAuthId(oauthToken);
    }

    private String getGoogleToken(String code) {
        ResponseEntity<OAuthTokenDto> googleUserInfo = restTemplate.postForEntity("https://www.googleapis.com/oauth2/v4/token?" +
                "code=" + code +
                "&client_id=" + GOOGLE_CLIENT_ID +
                "&client_secret=" + GOOGLE_CLIENT_SECRET +
                "&redirect_uri=" + GOOGLE_REDIRECT_URI +
                "&grant_type=" + "authorization_code", null, OAuthTokenDto.class);

        if(ObjectUtils.isEmpty(googleUserInfo.getBody().getAccessToken())) {
            throw new RuntimeException();
        }

        return googleUserInfo.getBody().getAccessToken();
    }

    private String getKakaoToken(String code) {
        ResponseEntity<OAuthTokenDto> kakaoUserInfo = restTemplate.postForEntity("https://kauth.kakao.com/oauth/token?" +
                "code=" + code +
                "&client_id" + KAKAO_CLIENT_ID +
                "&client_secret" + KAKAO_CLIENT_SECRET +
                "&redirect_uri" + KAKAO_REDIRECT_URI +
                "&grant_type=" + "authorization_code", null, OAuthTokenDto.class);

        if(ObjectUtils.isEmpty(kakaoUserInfo.getBody().getAccessToken())) {
            throw new RuntimeException();
        }

        return kakaoUserInfo.getBody().getAccessToken();
    }

    private String getGoogleOAuthId(String oauthToken) {
        HttpHeaders httpheaders = new HttpHeaders();

        httpheaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + oauthToken);
        HttpEntity<MultiValueMap<String, String>> getUserInfo = new HttpEntity<>(httpheaders);
        ResponseEntity<GoogleInfoDto> googleUserInfo = restTemplate.exchange("https://www.googleapis.com/oauth2/v3/userinfo", HttpMethod.GET, getUserInfo, GoogleInfoDto.class);

        if(ObjectUtils.isEmpty(googleUserInfo.getBody().getId())) {
            throw new RuntimeException();
        }

        return googleUserInfo.getBody().getId();
    }

    private String getKakaoOAuthId(String oauthToken) {
        HttpHeaders httpheaders = new HttpHeaders();

        httpheaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + oauthToken);
        HttpEntity<MultiValueMap<String, String>> getUserInfo = new HttpEntity<>(httpheaders);
        ResponseEntity<KakaoInfoDto> kakaoUserInfo = restTemplate.exchange("https://kapi.kakao.com/v1/user/access_token_info", HttpMethod.GET, getUserInfo, KakaoInfoDto.class);

        if(ObjectUtils.isEmpty(kakaoUserInfo.getBody().getId())) {
            throw new RuntimeException();
        }

        return String.valueOf(kakaoUserInfo.getBody().getId());
    }
}
