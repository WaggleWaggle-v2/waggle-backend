package unius.application_member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public abstract class GetBookInfoDto {

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class Response {
        private boolean isLock;
        private String senderNickname;
        private String receiverNickname;
        private String description;
        private String bookImageUrl;
        private LocalDateTime createdAt;

        public static Response lockedResponse() {
            return Response.builder()
                    .isLock(true)
                    .build();
        }
    }
}
