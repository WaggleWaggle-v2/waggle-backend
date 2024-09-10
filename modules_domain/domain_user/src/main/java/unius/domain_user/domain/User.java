package unius.domain_user.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import unius.core_user.type.UserState;
import unius.core_uuid.util.UuidUtils;
import unius.independent_jpa.model.AuditingModel;

@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
@Entity
public class User extends AuditingModel {

    @Id
    @Column(nullable = false, updatable = false, unique = true)
    private String id;

    @Enumerated(EnumType.STRING)
    private UserState userState;

    public User() {
        this.id = UuidUtils.generateUuid();
    }
}
