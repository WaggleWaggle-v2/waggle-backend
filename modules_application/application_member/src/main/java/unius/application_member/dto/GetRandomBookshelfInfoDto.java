package unius.application_member.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

public abstract class GetRandomBookshelfInfoDto {

    @Getter
    @Setter
    @RequiredArgsConstructor
    public static class Response {
        private String id;
        private String nickname;
        private String backgroundImageUrl;
        private String introduction;
        private String bookshelfType;
        private boolean isOpen;
        private Long count;
    }
}
