package unius.domain_bookshelf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unius.domain_bookshelf.domain.Bookshelf;

@Repository
public interface BookshelfRepository extends JpaRepository<Bookshelf, String> {
}
