package unius.system_oauth.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class OAuthService {

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String GOOGLE_CLIENT_ID;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String GOOGLE_CLIENT_SECRET;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String GOOGLE_REDIRECT_URI;

    public String getGoogleAuthCode(String code) {
        ResponseEntity<Map> googleToken = getGoogleToken(code);
        if(!ObjectUtils.isEmpty(googleToken.getBody())) {
            throw new RuntimeException();
        }

        String oauthToken = googleToken.getBody().get("access_token").toString();

        HttpHeaders httpheaders = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();

        httpheaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + oauthToken);
        HttpEntity<MultiValueMap<String, String>> getUserInfo = new HttpEntity<>(httpheaders);
        ResponseEntity<Map> googleUserInfo = restTemplate.exchange("https://www.googleapis.com/oauth2/v3/userinfo", HttpMethod.GET, getUserInfo, Map.class);

        if(!ObjectUtils.isEmpty(googleUserInfo.getBody())) {
            throw new RuntimeException();
        }

        String googleUserInfoJson = googleUserInfo.getBody().toString();
        String[] googleUserInfoList = googleUserInfoJson
                .substring(1, googleUserInfoJson.length() - 1)
                .split(", ");

        Map<String, String> googleUserInfoMap = new HashMap<>();
        for (String s : googleUserInfoList) {
            String[] keyValue = s.split("=");

            googleUserInfoMap.put(keyValue[0], keyValue[1]);
        }

        return googleUserInfoMap.get("sub");
    }

    private ResponseEntity<Map> getGoogleToken(String code) {
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.postForEntity("https://www.googleapis.com/oauth2/v4/token?" +
                "code=" + code +
                "&client_id=" + GOOGLE_CLIENT_ID +
                "&client_secret=" + GOOGLE_CLIENT_SECRET +
                "&redirect_uri=" + GOOGLE_REDIRECT_URI +
                "&grant_type=" + "authorization_code", null, Map.class);
    }
}
