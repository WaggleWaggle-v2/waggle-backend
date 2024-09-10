package unius.application_member.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;

public abstract class SetBookshelfBackgroundDto {

    @Getter
    public static class Request {

        @NotNull
        @Range(min = 1, max = 9)
        private Integer number;
    }
}
