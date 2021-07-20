package com.semihbkgr.corbeau.repository;

import com.semihbkgr.corbeau.model.Post;
import com.semihbkgr.corbeau.model.projection.PostShallow;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {

    private final R2dbcEntityTemplate template;

    @Override
    public Mono<Post> save(@NonNull Post post) {
        return template.insert(post);
    }

    @Override
    public Flux<PostShallow> findAllPostShallow(Pageable pageable) {
        return template.select(Query.query(Criteria.empty()).sort(pageable.getSortOr(Sort.unsorted())).limit(pageable.getPageSize()).offset(pageable.getOffset()).get).all();
    }

}
