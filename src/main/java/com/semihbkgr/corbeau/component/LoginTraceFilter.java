package com.semihbkgr.corbeau.component;

import com.semihbkgr.corbeau.service.RequestTraceService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;

public class LoginTraceFilter implements WebFilter {

    private final RequestTraceService loginTraceRepository;

    public LoginTraceFilter(@Qualifier("commentTraceService") RequestTraceService loginTraceRepository) {
        this.loginTraceRepository = loginTraceRepository;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        InetSocketAddress clientRemoteAddress = serverWebExchange.getRequest().getRemoteAddress();
        if (clientRemoteAddress == null)
            return Mono.error(new IllegalStateException("Client remote address is null"));
        String clientIpAddr = clientRemoteAddress.getHostString();
        if (clientIpAddr == null) return Mono.error(new IllegalStateException("Client ip address is null"));
        var clientRequest = loginTraceRepository.increaseAndUpdate(clientIpAddr);
        if (clientRequest.getRequestCount() < 5)
            return webFilterChain.filter(serverWebExchange);
        else return Mono.empty();
    }

}
