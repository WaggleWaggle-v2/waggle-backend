package unius.system_post_counter.service;

@FunctionalInterface
public interface UpdatePostCounterCallbackService {
    void updatePostCounter(String userId, Long additionValue);
}
