package unius.domain_book.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BookState {
    ACTIVE("ACTIVE"),
    WITHDRAW("WITHDRAW");

    private final String description;
}
