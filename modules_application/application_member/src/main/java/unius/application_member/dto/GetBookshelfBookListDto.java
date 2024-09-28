package unius.application_member.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

public abstract class GetBookshelfBookListDto {

    @Getter
    @Setter
    @RequiredArgsConstructor
    public static class Response {
        private long id;
        private String nickname;
        private String description;
        private String bookImageUrl;
        private boolean isOpen;
        private String bookType;
    }
}
