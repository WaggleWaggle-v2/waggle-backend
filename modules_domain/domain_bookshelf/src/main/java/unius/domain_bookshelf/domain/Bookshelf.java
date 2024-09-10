package unius.domain_bookshelf.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import unius.domain_bookshelf.type.BookshelfState;
import unius.domain_bookshelf.type.BookshelfType;
import unius.domain_user.domain.User;
import unius.independent_jpa.model.AuditingModel;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class Bookshelf extends AuditingModel {

    @Id
    private String id;

    @Column(nullable = false,
            columnDefinition = "VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci")
    private String nickname;

    private String backgroundImageUrl;

    private String introduction;

    @Enumerated(EnumType.STRING)
    private BookshelfState bookshelfState;

    @Enumerated(EnumType.STRING)
    private BookshelfType bookshelfType;

    @Column(nullable = false)
    private boolean isOpen;

    @Column(nullable = false)
    private Long count;

    @MapsId
    @OneToOne
    private User user;
}
