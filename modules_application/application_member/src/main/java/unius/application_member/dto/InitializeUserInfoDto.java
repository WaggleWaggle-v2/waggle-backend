package unius.application_member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public abstract class InitializeUserInfoDto {

    @Getter
    @RequiredArgsConstructor
    public static class Request {

        @NotNull
        @NotBlank
        private final String nickname;

        @NotNull
        private final Boolean isOpen;
    }

    @Getter
    @RequiredArgsConstructor
    public static class Response {
        private final String uuid;
    }
}
