package com.semihbkgr.corbeau.repository;

import com.semihbkgr.corbeau.model.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class TagRepositoryImpl implements TagRepository {

    private final R2dbcEntityTemplate template;

    @Override
    public Mono<Tag> save(@NonNull Tag tag) {
        return template.insert(tag);
    }

    @Override
    public Mono<Tag> update(Tag tag) {
        return template.update(tag);
    }

    @Override
    public Flux<Tag> findAll() {
        return template.select(Query.query(Criteria.empty()),Tag.class);
    }

    @Override
    public Mono<Integer> deleteById(int id) {
        return template.delete(Query.query(Criteria.where("id").is(id)),Tag.class);
    }

}
