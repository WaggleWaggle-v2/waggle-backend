package unius.application_member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public abstract class SetBookshelfThemeDto {

    @Getter
    public static class Request {

        @NotNull
        @NotBlank
        private String theme;
    }
}
