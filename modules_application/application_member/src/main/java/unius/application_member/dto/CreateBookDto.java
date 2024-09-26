package unius.application_member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

public abstract class CreateBookDto {

    @Getter
    @RequiredArgsConstructor
    public static class Request {

        @NotNull
        @NotBlank
        private final String bookshelfId;

        @NotNull
        @NotBlank
        private final String nickname;

        @NotNull
        private final String description;

        @NotNull
        private final boolean isOpen;

        @NotNull
        @NotBlank
        private final String bookType;
    }

    @Setter
    @Getter
    @RequiredArgsConstructor
    public static class Response {
        private String bookshelfId;
        private String nickname;
        private String description;
        private boolean isOpen;
        private String bookImageUrl;
        private String bookType;
    }
}
