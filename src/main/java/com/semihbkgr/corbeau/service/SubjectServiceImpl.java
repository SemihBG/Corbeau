package com.semihbkgr.corbeau.service;

import com.semihbkgr.corbeau.error.IllegalValueException;
import com.semihbkgr.corbeau.model.Subject;
import com.semihbkgr.corbeau.model.projection.SubjectDeep;
import com.semihbkgr.corbeau.repository.SubjectRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;

    @Override
    public Mono<Subject> save(@NonNull Subject subject) {
        return subjectRepository.save(subject.withId(0));
    }

    @Override
    public Flux<Subject> findAll() {
        return subjectRepository.findAll();
    }

    @Override
    public Flux<SubjectDeep> findAllDeep() {
        return subjectRepository.findAllDeep();
    }

    @Override
    public Mono<Subject> findById(int id) throws IllegalValueException {
        return subjectRepository.findById(id)
                .switchIfEmpty(Mono.error(() ->
                        new IllegalValueException("No subject found by given id", SubjectRepository.TABLE_NAME, "id", id)));
    }

    @Override
    public Mono<SubjectDeep> findByNameDeep(@NonNull String name) throws IllegalValueException {
        return subjectRepository.findByNameDeep(name)
                .switchIfEmpty(Mono.error(() ->
                        new IllegalValueException("No subject found by given id", SubjectRepository.TABLE_NAME, "name", name)));
    }

    @Override
    public Mono<Subject> update(int id, @NonNull Subject subject) throws IllegalValueException {
        if (id < 1 || subject.getName() == null) throw new IllegalArgumentException();
        return subjectRepository.findById(id)
                .switchIfEmpty(Mono.error(() ->
                        new IllegalValueException("Subject not available by given id", SubjectRepository.TABLE_NAME, "id", id)))
                .flatMap(savedSubject -> {
                    savedSubject.setName(subject.getName());
                    return subjectRepository.update(savedSubject);
                });
    }

    @Override
    public Mono<Void> deleteById(int id) throws IllegalValueException {
        if (id < 1) throw new IllegalArgumentException();
        return subjectRepository.findById(id)
                .switchIfEmpty(Mono.error(() ->
                        new IllegalValueException("Image not available by given id", SubjectRepository.TABLE_NAME, "id", id)))
                .then(subjectRepository.deleteById(id));
    }


}
