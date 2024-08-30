package unius.domain_oauth.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import unius.core_domain.dto.DomainDto;
import unius.domain_oauth.type.PlatformType;
import unius.domain_user.domain.User;

public abstract class CreateOAuthDto {

    @Getter
    @RequiredArgsConstructor
    public static class Request implements DomainDto {
        private final String code;
        private final PlatformType platform;
        private final User user;
    }

    @Getter
    @RequiredArgsConstructor
    public static class Response implements DomainDto {
        private final String id;
        private final String platform;
    }
}
