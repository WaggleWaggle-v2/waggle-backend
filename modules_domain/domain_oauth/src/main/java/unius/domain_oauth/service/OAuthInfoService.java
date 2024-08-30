package unius.domain_oauth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import unius.core_domain.dto.DomainDto;
import unius.core_domain.service.DomainService;
import unius.domain_oauth.domain.OAuthInfo;
import unius.domain_oauth.dto.CreateOAuthDto;
import unius.domain_oauth.repository.OAuthInfoRepository;

@Service
@RequiredArgsConstructor
public class OAuthInfoService implements DomainService<Long> {

    private final OAuthInfoRepository oAuthInfoRepository;

    @Override
    public <Request extends DomainDto, Response extends DomainDto> Response create(Request request) {
        if (request instanceof CreateOAuthDto.Request) {
            OAuthInfo oAuthInfo = OAuthInfo.builder()
                    .user(((CreateOAuthDto.Request) request).getUser())
                    .platform(((CreateOAuthDto.Request) request).getPlatform())
                    .build();

            oAuthInfoRepository.save(oAuthInfo);

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
