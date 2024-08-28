package unius.application_gateway.component.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

@Component
public class JwtFilter extends AbstractGatewayFilterFactory<JwtFilter.Config> {
    @Override
    public GatewayFilter apply(Config config) {
        return null;
    }

    public static class Config {}
}
