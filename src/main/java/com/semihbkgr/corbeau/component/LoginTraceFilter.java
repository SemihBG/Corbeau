package com.semihbkgr.corbeau.component;

import com.semihbkgr.corbeau.service.RequestTraceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class LoginTraceFilter implements WebFilter {

    private final RequestTraceService loginTraceRepository;

    public LoginTraceFilter(@Qualifier("loginTraceService") RequestTraceService loginTraceRepository) {
        this.loginTraceRepository = loginTraceRepository;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        String clientIpAddr = serverWebExchange.getRequest().getRemoteAddress().getHostString();
        log.info("Client ip: {}", clientIpAddr);
        var clientRequest = loginTraceRepository.increaseAndUpdate(clientIpAddr);
        log.info("Request count: {}", clientRequest.getRequestCount());
        if (clientRequest.getRequestCount() < 5)
            return webFilterChain.filter(serverWebExchange);
        else return Mono.empty();
    }

}
