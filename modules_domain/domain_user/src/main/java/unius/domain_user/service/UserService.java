package unius.domain_user.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import unius.core_user.type.UserState;
import unius.core_uuid.util.UuidUtils;
import unius.domain_user.domain.User;
import unius.domain_user.repository.UserRepository;
import unius.domain_user.repository.UserRepositoryQuerydsl;

import static unius.core_user.type.UserState.INCOMPLETE;

@Service
@RequiredArgsConstructor
public class UserService {

    private final EntityManager entityManager;
    private final UserRepository userRepository;
    private final UserRepositoryQuerydsl userRepositoryQuerydsl;

    public User create() {
        User user = User.builder()
                .id(UuidUtils.generateUuid())
                .userState(INCOMPLETE)
                .build();

        userRepository.save(user);
        entityManager.flush();
        entityManager.detach(user);

        return user;
    }

    public User get(String userId, UserState... userStates) {
        return userRepositoryQuerydsl.getUserInfo(userId, userStates);
    }

    public void setUserState(User user, UserState userState) {
        userRepositoryQuerydsl.setUserState(user, userState);
    }

}
