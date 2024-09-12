package unius.application_member.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public abstract class SetBookshelfIntroductionDto {

    @Getter
    public static class Request {

        @NotNull
        private String introduction;
    }
}
