package unius.domain_bookshelf.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BookshelfState {
    ACTIVE("ACTIVE"),
    WITHDRAW("WITHDRAW");

    private final String description;
}
