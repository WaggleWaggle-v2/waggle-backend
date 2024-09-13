package unius.system_oauth.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleInfoDto {
    private Long sub;

    public Long getId() {
        return this.sub;
    }
}
