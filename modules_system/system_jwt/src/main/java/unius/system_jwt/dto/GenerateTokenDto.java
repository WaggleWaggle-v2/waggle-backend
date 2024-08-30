package unius.system_jwt.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public abstract class GenerateTokenDto {

    @Getter
    @RequiredArgsConstructor
    public static class Request extends GenerateTokenDto {
        private final long id;
        private final String userState;
    }

    @Getter
    @RequiredArgsConstructor
    public static class Response extends GenerateTokenDto {
        private final String accessToken;
        private final String refreshToken;
    }
}
