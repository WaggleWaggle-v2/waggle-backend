package unius.application_member.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

public abstract class GetMyUserInfoDto {

    @Setter
    @Getter
    @RequiredArgsConstructor
    public static class Response {
        private String id;
        private String userState;
        private String nickname;
    }
}
