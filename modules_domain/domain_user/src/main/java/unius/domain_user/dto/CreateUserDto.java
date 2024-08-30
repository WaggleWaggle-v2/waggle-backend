package unius.domain_user.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import unius.core_domain.dto.DomainDto;
import unius.domain_user.type.UserState;

public abstract class CreateUserDto {

    @Getter
    @RequiredArgsConstructor
    public static class Request implements DomainDto {
        private final UserState userState;
    }

    @Getter
    @RequiredArgsConstructor
    public static class Response implements DomainDto {
        private final long id;
        private final String nickname;
        private final String profileImageUrl;
    }
}
