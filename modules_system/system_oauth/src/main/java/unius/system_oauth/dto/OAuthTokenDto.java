package unius.system_oauth.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OAuthTokenDto {
    private String access_token;

    public void setAccessToken(String accessToken) {
        this.access_token = accessToken;
    }

    public String getAccessToken() {
        return this.access_token;
    }
}
