package com.semihbkgr.corbeau.component;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class ReservedWordContainer {

    private final Set<String> moderationReservedWordList;
    private final Set<String> subjectReservedWordList;
    private final Set<String> postReservedWordList;

    public boolean moderationContains(@NonNull String word) {
        return moderationReservedWordList.contains(word);
    }

    public boolean subjectContains(@NonNull String word) {
        return subjectReservedWordList.contains(word);
    }

    public boolean postContains(@NonNull String word) {
        return postReservedWordList.contains(word);
    }

}
