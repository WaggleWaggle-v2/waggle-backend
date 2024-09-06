package unius.application_gateway.component.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import unius.system_jwt.service.TokenService;

@Component
public class JwtFilter extends AbstractGatewayFilterFactory<JwtFilter.Config> {
    public static class Config {}
    private final TokenService tokenService;

    @Autowired
    public JwtFilter(TokenService tokenService) {
        super(Config.class);
        this.tokenService = tokenService;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if(exchange.getRequest().getPath().toString().startsWith("/unius/member/login")) {
                return chain.filter(exchange);
            }

            String token = exchange.getRequest().getHeaders().getFirst("Authorization");

            if(tokenService.validateToken(token)){
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            ServerHttpRequest request = exchange.getRequest().mutate()
                    .header("X-User-Id-Header", tokenService.getId(token))
                    .build();

            return chain.filter(exchange.mutate().request(request).build());
        });
    }
}
