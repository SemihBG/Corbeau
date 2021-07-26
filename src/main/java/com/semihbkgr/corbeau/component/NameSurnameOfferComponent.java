package com.semihbkgr.corbeau.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class NameSurnameOfferComponent {

    private final List<Pair<String, String>> nameSurnamePairList;
    private final Random random;
    private final Pair<String, String> defaultPair;

    public NameSurnameOfferComponent(@Value("${name-surname-offer.pairs:#{null}}") String[] nameSurnameArray) {
        if (nameSurnameArray != null)
            this.nameSurnamePairList = Arrays
                    .stream(nameSurnameArray)
                    .map(nameSurname -> {
                        var a = nameSurname.split(" ");
                        if (a.length == 0) return null;
                        return Pair.of(a[0], a[1]);
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toUnmodifiableList());
        else this.nameSurnamePairList = Collections.emptyList();
        random = new Random();
        defaultPair = Pair.of("Name", "Surname");
    }

    public Mono<Pair<String, String>> offer() {
        return Mono.defer(() ->
                !nameSurnamePairList.isEmpty() ?
                        Mono.just(nameSurnamePairList.get(random.nextInt(nameSurnamePairList.size()))) :
                        Mono.just(defaultPair)
        );
    }

}
