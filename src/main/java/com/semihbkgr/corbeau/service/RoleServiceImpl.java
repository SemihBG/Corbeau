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
    public Flux<Role> saveAll(@NonNull Iterable<Role> roleIterable) {
        return roleRepository.saveAll(roleIterable);
    }

    @Override
    public Mono<Role> findById(int id) throws IllegalValueException {
        if(id<=1)
            throw new IllegalArgumentException("Id parameter cannot be null");
        return roleRepository.findById(id);
    }

    @Override
    public Mono<Role> findByName(@NonNull String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public Flux<Role> findAll() {
        return roleRepository.findAll();
    }

}
