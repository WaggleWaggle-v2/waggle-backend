package unius.domain_book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unius.domain_book.domain.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
}
