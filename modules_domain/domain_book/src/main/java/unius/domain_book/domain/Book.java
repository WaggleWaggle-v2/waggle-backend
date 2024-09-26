package unius.domain_book.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import unius.domain_book.type.BookState;
import unius.domain_book.type.BookType;
import unius.domain_bookshelf.domain.Bookshelf;
import unius.independent_jpa.model.AuditingModel;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class Book extends AuditingModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "bookshelf_id")
    private Bookshelf bookshelf;

    @Column(nullable = false)
    private String nickname;

    private String description;

    private String bookImageUrl;

    @Column(nullable = false)
    private boolean isOpen;

    @Enumerated(EnumType.STRING)
    private BookState bookState;

    @Enumerated(EnumType.STRING)
    private BookType bookType;
}
