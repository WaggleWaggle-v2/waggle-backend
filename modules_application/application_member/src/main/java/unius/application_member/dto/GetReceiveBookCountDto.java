package unius.application_member.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

public abstract class GetReceiveBookCountDto {

    @Getter
    @Setter
    @RequiredArgsConstructor
    public static class Response {
        private long receiveCount;
    }
}
