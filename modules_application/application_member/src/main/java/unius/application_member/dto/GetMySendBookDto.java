package unius.application_member.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

public abstract class GetMySendBookDto {

    @Getter
    @Setter
    @RequiredArgsConstructor
    public static class Response {
        private long id;
        private long bookId;
        private String nickname;
        private String description;
        private String backgroundImageUrl;
    }
}
