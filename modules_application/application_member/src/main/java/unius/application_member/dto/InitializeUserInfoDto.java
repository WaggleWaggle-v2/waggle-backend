package unius.application_member.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public abstract class InitializeUserInfoDto {

    @Getter
    @RequiredArgsConstructor
    public static class Request {
        private final String nickname;
        private final boolean isOpen;
    }

    @Getter
    @RequiredArgsConstructor
    public static class Response {
        private final String uuid;
    }
}
