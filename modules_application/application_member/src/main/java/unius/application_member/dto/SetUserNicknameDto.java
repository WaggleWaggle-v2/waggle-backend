package unius.application_member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public abstract class SetUserNicknameDto {

    @Getter
    public static class Request {

        @NotNull
        @NotBlank
        private String nickname;
    }

    @Getter
    @RequiredArgsConstructor
    public static class Response {
        private final String nickname;
    }
}
