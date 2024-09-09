package unius.application_member.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public abstract class SetBookshelfRevelationDto {

    @Getter
    public static class Request {

        @NotNull
        private Boolean isOpen;
    }
}
