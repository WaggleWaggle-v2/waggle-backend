package unius.domain_oauth.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PlatformType {
    GOOGLE("GOOGLE"),
    KAKAO("KAKAO");

    private final String description;
}
