package unius.domain_bookshelf.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BookshelfType {
    WHITE("WHITE"),
    BLACK("BLACK");

    private final String description;
}
