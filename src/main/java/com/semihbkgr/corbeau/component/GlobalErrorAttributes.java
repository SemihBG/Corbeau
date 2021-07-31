package com.semihbkgr.corbeau.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Map;

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {

    private final boolean includeHeader;
    private final boolean includeCookies;

    public GlobalErrorAttributes(@Value("${server.error.include-headers:#{false}") boolean includeHeader,
                                 @Value("${server.error.include-cookies:#{false}") boolean includeCookies) {
        this.includeHeader = includeHeader;
        this.includeCookies = includeCookies;
    }

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        var errorAttributesMap = super.getErrorAttributes(request, options);
        if (includeHeader)
            errorAttributesMap.put("headers", request.headers().asHttpHeaders().toString());
        if (includeCookies)
            errorAttributesMap.put("cookies", request.cookies().toString());
        return errorAttributesMap;
    }

}
