package unius.system_jwt.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ClaimKey {
    KEY_STATE("userState");

    private final String description;
}
