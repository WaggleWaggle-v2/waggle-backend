package unius.core_domain.service;

import unius.core_domain.dto.DomainDto;

public interface DomainService<ID> {

    <Request extends DomainDto, Response extends DomainDto> Response create(Request request);

    <Response extends DomainDto> Response get(ID id);

    void delete(ID id);
}
