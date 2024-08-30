package unius.domain_oauth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unius.domain_oauth.domain.OAuthInfo;

@Repository
public interface OAuthInfoRepository extends JpaRepository<OAuthInfo, Long> {
}
