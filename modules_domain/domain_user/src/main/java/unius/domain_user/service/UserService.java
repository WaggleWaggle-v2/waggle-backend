package unius.domain_user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import unius.core_domain.dto.DomainDto;
import unius.core_domain.service.DomainService;
import unius.domain_user.domain.User;
import unius.domain_user.dto.CreateUserDto;
import unius.domain_user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService implements DomainService<Long> {

    private final UserRepository userRepository;

    @Override
    public <Request extends DomainDto, Response extends DomainDto> Response create(Request request) {
        if(request instanceof CreateUserDto.Request) {
            User user = User.builder()
                    .userState(((CreateUserDto.Request) request).getUserState())
                    .build();

            userRepository.save(user);

            return null;
        }

        throw new IllegalArgumentException();
    }

    @Override
    public <Response extends DomainDto> Response get(Long aLong) {
        return null;
    }

    @Override
    public void delete(Long aLong) {

    }
}
