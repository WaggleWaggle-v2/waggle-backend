package unius.application_member.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public abstract class SetUserNicknameDto {

    @Getter
    public static class Request {
        private String nickname;
    }

    @Getter
    @RequiredArgsConstructor
    public static class Response {
        private final String nickname;
    }
}
