package unius.system_oauth.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OAuthTokenDto {
    private String access_token;

    public String getAccessToken() {
        return this.access_token;
    }
}
