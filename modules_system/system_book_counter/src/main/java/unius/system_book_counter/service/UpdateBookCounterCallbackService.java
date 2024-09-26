package unius.system_book_counter.service;

@FunctionalInterface
public interface UpdateBookCounterCallbackService {
    void updateBookCounter(String bookshelfId, Long additionValue);
}
