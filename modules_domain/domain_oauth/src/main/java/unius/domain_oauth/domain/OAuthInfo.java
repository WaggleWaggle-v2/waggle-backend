package unius.domain_oauth.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import unius.domain_oauth.type.PlatformType;
import unius.domain_user.domain.User;
import unius.independent_jpa.model.AuditingModel;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class OAuthInfo extends AuditingModel {

    @Id
    private String id;

    @Column(nullable = false)
    private String oauthId;

    @Enumerated(EnumType.STRING)
    private PlatformType platform;

    @MapsId
    @OneToOne
    private User user;
}
