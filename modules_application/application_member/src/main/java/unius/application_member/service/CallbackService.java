package unius.application_member.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import unius.domain_bookshelf.service.BookshelfService;
import unius.system_book_counter.service.UpdateBookCounterCallbackService;

@Service
@RequiredArgsConstructor
public class CallbackService implements UpdateBookCounterCallbackService {

    private final BookshelfService bookshelfService;

    @Override
    @Transactional
    public void updateBookCounter(String bookshelfId, Long additionValue) {
        bookshelfService.updateBookCounter(bookshelfId, additionValue);
    }
}
