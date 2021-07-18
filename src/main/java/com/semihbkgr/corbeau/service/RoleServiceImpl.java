package com.semihbkgr.corbeau.service;

import com.semihbkgr.corbeau.error.IllegalValueException;
import com.semihbkgr.corbeau.model.Role;
import com.semihbkgr.corbeau.repository.RoleRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Flux<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Mono<Role> findByName(@NonNull String name) throws IllegalValueException {
        return roleRepository.findByName(name)
                .switchIfEmpty(Mono.error(() ->
                        new IllegalValueException("Role no found by given name", RoleRepository.TABLE_NAME, "name", name)));
    }

}
