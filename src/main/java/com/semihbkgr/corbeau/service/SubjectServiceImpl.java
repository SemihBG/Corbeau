package com.semihbkgr.corbeau.service;

import com.semihbkgr.corbeau.error.IllegalValueException;
import com.semihbkgr.corbeau.model.Subject;
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
    public Flux<Subject> findAll() {
        return subjectRepository.findAll();
    }

    @Override
    public Mono<Subject> save(@NonNull Subject subject) {
        return subjectRepository.save(subject.withId(0));
    }

    @Override
    public Mono<Subject> update(int id, @NonNull Subject subject) throws IllegalValueException {
        return subjectRepository.findById(id)
                .switchIfEmpty(Mono.error(()->
                        new IllegalValueException("Subject not available by given id", subjectRepository.TABLE_NAME, "id", id)))
                .then(subjectRepository.save(subject.withId(id)));
    }

    @Override
    public Mono<Void> delete(int id) throws IllegalValueException {
        return subjectRepository.findById(id)
                .switchIfEmpty(Mono.error(()->
                        new IllegalValueException("Image not available by given id", subjectRepository.TABLE_NAME, "id", id)))
                .then(subjectRepository.deleteById(id));
    }


}
