package unius.application_member.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import unius.domain_bookshelf.service.BookshelfService;
import unius.domain_user.service.UserService;
import unius.system_book_counter.service.UpdateBookCounterCallbackService;
import unius.system_post_counter.service.UpdatePostCounterCallbackService;

@Service
@RequiredArgsConstructor
public class CallbackService implements UpdateBookCounterCallbackService, UpdatePostCounterCallbackService {

    private final BookshelfService bookshelfService;
    private final UserService userService;

    @Override
    @Transactional
    public void updateBookCounter(String bookshelfId, Long additionValue) {
        bookshelfService.updateBookCounter(bookshelfId, additionValue);
    }

    @Override
    @Transactional
    public void updatePostCounter(String userId, Long additionValue) {
        userService.updatePostCounter(userId, additionValue);
    }
}
