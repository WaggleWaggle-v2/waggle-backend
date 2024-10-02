package unius.application_member.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

public abstract class GetSendBookCountDto {

    @Getter
    @Setter
    @RequiredArgsConstructor
    public static class Response {
        private long sendCount;
    }
}
