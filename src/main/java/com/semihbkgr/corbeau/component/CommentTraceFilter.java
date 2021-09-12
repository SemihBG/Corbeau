package com.semihbkgr.corbeau.component;

import com.semihbkgr.corbeau.service.RequestTraceService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CommentTraceFilter implements WebFilter {

    private final RequestTraceService commentTraceRepository;

    public CommentTraceFilter(@Qualifier("commentTraceService") RequestTraceService commentTraceRepository) {
        this.commentTraceRepository = commentTraceRepository;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        if(!serverWebExchange.getRequest().getURI().toString().startsWith("/moderation/login"))
            return webFilterChain.filter(serverWebExchange);
        InetSocketAddress clientRemoteAddress = serverWebExchange.getRequest().getRemoteAddress();
        if (clientRemoteAddress == null)
            return Mono.error(new IllegalStateException("Client remote address is null"));
        String clientIpAddr = clientRemoteAddress.getHostString();
        if (clientIpAddr == null)
            return Mono.error(new IllegalStateException("Client ip address is null"));
        var clientRequest = commentTraceRepository.increaseAndUpdate(clientIpAddr);
        if (clientRequest.getRequestCount() < 5)
            return webFilterChain.filter(serverWebExchange);
        else return Mono.empty();
    }

}
