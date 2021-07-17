package com.semihbkgr.corbeau.service;

import com.semihbkgr.corbeau.model.Moderator;
import com.semihbkgr.corbeau.repository.ModeratorRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ModeratorServiceImpl implements ModeratorService {

    private final ModeratorRepository moderatorRepository;

    @Override
    public Mono<Moderator> save(@NonNull Moderator moderator) {
        return moderatorRepository.save(moderator.withId(0));
    }

    @Override
    public Flux<Moderator> findAll() {
        return moderatorRepository.findAll();
    }

}
