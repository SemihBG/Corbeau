package com.semihbkgr.corbeau.repository;

import com.semihbkgr.corbeau.model.Subject;
import com.semihbkgr.corbeau.model.projection.SubjectDeep;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;


@SuppressWarnings("ConstantConditions")
@Repository
@RequiredArgsConstructor
public class SubjectRepositoryImpl implements SubjectRepository {

    static final String SQL_FIND_ALL =
            "SELECT subjects.id, subjects.name, subjects.created_by, " +
                    "subjects.updated_by,subjects.created_at, subjects.updated_at,  " +
                    "(SELECT COUNT(*) FROM posts WHERE posts.subject_id=subjects.id ) as post_count " +
                    "FROM subjects";

    static final String SQL_FIND_BY_ID =
            "SELECT subjects.id, subjects.name, subjects.created_by, " +
                    "subjects.updated_by, subjects.created_at, subjects.updated_at,  " +
                    "(SELECT COUNT(*) FROM posts WHERE posts.subject_id=subjects.id ) as post_count " +
                    "FROM subjects WHERE subjects.name=?";

    static final BiFunction<Row, RowMetadata, SubjectDeep> SUBJECT_DEEP_MAPPER =
            (row, rowMetadata) ->
                    SubjectDeep.builder()
                            .id(row.get("id", Integer.class))
                            .name(row.get("name", String.class))
                            .createdBy(row.get("created_by", String.class))
                            .updatedBy(row.get("updated_by", String.class))
                            .createdAt(row.get("created_at", Long.class))
                            .updatedAt(row.get("updated_at", Long.class))
                            .postCount(row.get("post_count", Long.class))
                            .build();

    private final R2dbcEntityTemplate template;


    @Override
    public Mono<Subject> save(@NonNull Subject subject) {
        return template.insert(subject);
    }

    @Override
    public Mono<Subject> update(@NonNull Subject subject) {
        return template.update(subject);
    }

    @Override
    public Mono<Subject> findById(int id) {
        return template.selectOne(Query.query(Criteria.where("id").is(id)), Subject.class);
    }

    @Override
    public Mono<SubjectDeep> findByNameDeep(@NonNull String name) {
        return template.getDatabaseClient()
                .sql(SQL_FIND_BY_ID)
                .bind(0, name)
                .map(SUBJECT_DEEP_MAPPER)
                .first();
    }

    @Override
    public Flux<Subject> findAll() {
        return template.select(Query.query(Criteria.empty()), Subject.class);
    }

    @Override
    public Flux<SubjectDeep> findAllDeep() {
        return template.getDatabaseClient().sql(SQL_FIND_ALL)
                .map(SUBJECT_DEEP_MAPPER)
                .all();
    }

    @Override
    public Mono<Void> deleteById(int id) {
        return template.delete(Query.query(Criteria.where("id").is(id)), Subject.class)
                .then();
    }

}
