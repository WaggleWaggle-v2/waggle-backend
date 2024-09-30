package unius.application_member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public abstract class GetBookInfoDto {

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Response {
        private boolean isLock;
        private String description;
        private LocalDateTime createdAt;
    }
}
