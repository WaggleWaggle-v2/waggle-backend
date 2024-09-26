package unius.domain_book.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BookType {
    SHORT("SHORT"),
    LONG("LONG");

    private final String description;
}
