package com.semihbkgr.corbeau.repository;

import com.semihbkgr.corbeau.model.Subject;
import com.semihbkgr.corbeau.model.projection.SubjectDeep;
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
public class SubjectRepositoryImpl implements SubjectRepository {

    static final String SQL_FIND_ALL=
            "SELECT db.subjects.id, db.subjects.name, db.subjects.created_by, " +
                    "db.subjects.updated_by,db.subjects.created_at, db.subjects.updated_at,  " +
                    "(SELECT COUNT(*) FROM db.posts WHERE db.posts.subject_id=subjects.id ) as post_count " +
                    "FROM db.subjects";

    private final R2dbcEntityTemplate template;

    @SuppressWarnings("ConstantConditions")
    @Override
    public Flux<SubjectDeep> findAll() {
        return template.getDatabaseClient().sql(SQL_FIND_ALL)
                .map((row, rowMetadata) ->
                    SubjectDeep.builder()
                            .id(row.get("id",Integer.class))
                            .name(row.get("name",String.class))
                            .createdBy(row.get("created_by", String.class))
                            .updatedBy(row.get("updated_by", String.class))
                            .createdAt(row.get("created_at", Long.class))
                            .updatedAt(row.get("updated_at", Long.class))
                            .postCount(row.get("post_count",Long.class))
                            .build()
                ).all();
    }

    @Override
    public Mono<Subject> findById(int id) {
        return template.selectOne(Query.query(Criteria.where("id").is(id)),Subject.class);
    }

    @Override
    public Mono<Subject> save(@NonNull Subject subject) {
        return template.insert(subject);
    }

    @Override
    public Mono<Subject> update(@NonNull Subject subject) {
        return template.update(subject);
    }

    @Override
    public Mono<Void> deleteById(int id) {
        return template.delete(Query.query(Criteria.where("id").is(id)),Subject.class)
                .then();
    }

}
