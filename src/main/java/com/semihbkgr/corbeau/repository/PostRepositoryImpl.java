package com.semihbkgr.corbeau.repository;

import com.semihbkgr.corbeau.model.Post;
import com.semihbkgr.corbeau.model.projection.PostShallow;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.CriteriaDefinition;
import org.springframework.data.relational.core.query.Query;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {

    static final String SQL_FIND_ALL_PAGED_ORDERED =
            "SELECT db.posts.id, db.posts.title, db.posts.subject_id, db.subjects.name as subject_name, " +
                    "db.posts.created_by, db.posts.updated_by, db.posts.created_at, db.posts.updated_at " +
                    "FROM db.posts LEFT JOIN db.subjects ON db.posts.subject_id=db.subjects.id " +
                    "ORDER BY %s %s " +
                    "LIMIT ? OFFSET ?";

    static final String SQL_FIND_ALL_PAGED_UNORDERED =
            "SELECT db.posts.id, db.posts.title, db.posts.subject_id, db.subjects.name as subject_name, " +
                    "db.posts.created_by, db.posts.updated_by, db.posts.created_at, db.posts.updated_at " +
                    "FROM db.posts LEFT JOIN db.subjects ON db.posts.subject_id=db.subjects.id " +
                    "LIMIT ? OFFSET ?";

    static final String SQL_FIND_ALL_UNPAGED_ORDERED=
            "SELECT db.posts.id, db.posts.title, db.posts.subject_id, db.subjects.name as subject_name, " +
                    "db.posts.created_by, db.posts.updated_by, db.posts.created_at, db.posts.updated_at " +
                    "FROM db.posts LEFT JOIN db.subjects ON db.posts.subject_id=db.subjects.id " +
                    "ORDER BY %s %s";

    static final String SQL_FIND_ALL_UNPAGED_UNORDERED =
            "SELECT db.posts.id, db.posts.title, db.posts.subject_id, db.subjects.name as subject_name, " +
                    "db.posts.created_by, db.posts.updated_by, db.posts.created_at, db.posts.updated_at " +
                    "FROM db.posts LEFT JOIN db.subjects ON db.posts.subject_id=db.subjects.id ";

    private final R2dbcEntityTemplate template;

    @Override
    public Mono<Post> save(@NonNull Post post) {
        return template.insert(post);
    }

    @Override
    public Mono<Post> update(@NonNull Post post) {
        return template.update(post);
    }

    @Override
    public Mono<Post> findById(int id) {
        return template.selectOne(Query.query(Criteria.where("id").is(id)),Post.class);
    }

    @Override
    public Mono<Post> findByTitle(@NonNull String title) {
        return template.selectOne(Query.query(Criteria.where("title").is(title)),Post.class);
    }

    //Pageable sort takes only first order in sort.
    @Override
    @SuppressWarnings("ConstantConditions")
    public Flux<PostShallow> findAll(@NonNull Pageable pageable) {
        DatabaseClient.GenericExecuteSpec ges;
        if (pageable.isPaged() && pageable.getSort().isSorted()){
            var order=pageable.getSort().stream().findFirst().orElseThrow();
            ges = template.getDatabaseClient()
                    .sql(String.format(SQL_FIND_ALL_PAGED_ORDERED,order.getProperty(),order.isAscending()?
                            Sort.Direction.ASC:Sort.Direction.DESC))
                    .bind(0, pageable.getPageSize())
                    .bind(1, pageable.getOffset());
        } else if (pageable.isPaged() && pageable.getSort().isUnsorted())
            ges = template.getDatabaseClient().sql(SQL_FIND_ALL_PAGED_UNORDERED)
                    .bind(0, pageable.getPageSize())
                    .bind(1, pageable.getOffset());
        else if (pageable.isUnpaged() && pageable.getSort().isSorted()){
            var order=pageable.getSort().stream().findFirst().orElseThrow();
            ges = template.getDatabaseClient()
                    .sql(String.format(SQL_FIND_ALL_UNPAGED_ORDERED,order.getProperty(),order.isAscending()?
                            Sort.Direction.ASC:Sort.Direction.DESC));
        } else
            ges = template.getDatabaseClient().sql(SQL_FIND_ALL_UNPAGED_UNORDERED);

        return ges.map((row, rowMetadata) -> PostShallow.builder()
                .id(row.get("id", Integer.class))
                .title(row.get("title", String.class))
                .subjectId(row.get("subject_id", Integer.class))
                .subjectName(row.get("subject_name", String.class))
                .createdBy(row.get("created_by", String.class))
                .updatedBy(row.get("updated_by", String.class))
                .createdAt(row.get("created_at", Long.class))
                .updatedAt(row.get("updated_at", Long.class))
                .build()
        ).all();
    }

    @Override
    public Mono<Long> count() {
        return template.count(Query.query(CriteriaDefinition.empty()),Post.class);
    }

}
