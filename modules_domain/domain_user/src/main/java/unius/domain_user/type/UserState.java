package unius.domain_user.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserState {
    INCOMPLETE("INCOMPLETE"),
    VERIFIED("VERIFIED"),
    BLOCKED("BLOCKED"),
    WITHDRAW("WITHDRAW"),
    ADMIN("ADMIN");

    private final String description;
}
