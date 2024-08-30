package unius.domain_user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unius.domain_user.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
