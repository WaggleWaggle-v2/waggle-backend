package unius.domain_book_list.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unius.domain_book_list.domain.BookList;

@Repository
public interface BookListRepository extends JpaRepository<BookList, Long> {
}
