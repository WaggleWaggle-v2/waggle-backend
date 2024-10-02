package unius.application_member.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

public abstract class GetMyReceiveBookDto {

    @Getter
    @Setter
    @RequiredArgsConstructor
    public static class Response {
        private long id;
        private long bookId;
        private String nickname;
        private String description;
    }
}
