package com.semihbkgr.corbeau.repository;

import com.semihbkgr.corbeau.model.Comment;
import com.semihbkgr.corbeau.model.projection.CommentDeep;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {

    static final String SQL_FIND_ALL_BY_POST_ID_PAGED_ORDERED =
            "SELECT * " +
                    "FROM comments " +
                    "WHERE post_id = ? " +
                    "ORDER BY %s %s " +
                    "LIMIT ? OFFSET ?";

    static final String SQL_FIND_ALL_BY_POST_ID_PAGED_UNORDERED =
            "SELECT * " +
                    "FROM comments " +
                    "WHERE post_id = ? " +
                    "LIMIT ? OFFSET ?";

    static final String SQL_FIND_ALL_BY_POST_ID_UNPAGED_ORDERED =
            "SELECT * " +
                    "FROM comments " +
                    "WHERE post_id = ? " +
                    "ORDER BY %s %s";

    static final String SQL_FIND_ALL_BY_POST_ID_UNPAGED_UNORDERED =
            "SELECT * " +
                    "FROM comments " +
                    "WHERE post_id = ? ";

    static final String SQL_FIND_ALL_DEEP_PAGED_ORDERED =
            "SELECT comments.id, comments.name, comments.surname, comments.email, comments.post_id, " +
                    "comments.content, comments.created_at, comments.updated_at, posts.title as post_title " +
                    "FROM comments " +
                    "JOIN posts on comments.post_id = posts.id " +
                    "ORDER BY %s %s " +
                    "LIMIT ? OFFSET ?";

    static final String SQL_FIND_ALL_DEEP_PAGED_UNORDERED =
            "SELECT comments.id, comments.name, comments.surname, comments.email, comments.post_id, " +
                    "comments.content, comments.created_at, comments.updated_at, posts.title as post_title " +
                    "FROM comments " +
                    "JOIN posts on comments.post_id = posts.id " +
                    "LIMIT ? OFFSET ?";

    static final String SQL_FIND_ALL_DEEP_UNPAGED_ORDERED =
            "SELECT comments.id, comments.name, comments.surname, comments.email, comments.post_id, " +
                    "comments.content, comments.created_at, comments.updated_at, posts.title as post_title " +
                    "FROM comments " +
                    "JOIN posts on comments.post_id = posts.id " +
                    "ORDER BY %s %s";

    static final String SQL_FIND_ALL_DEEP_UNPAGED_UNORDERED =
            "SELECT comments.id, comments.name, comments.surname, comments.email, comments.post_id, " +
                    "comments.content, comments.created_at, comments.updated_at, posts.title as post_title " +
                    "FROM comments " +
                    "JOIN posts on comments.post_id = posts.id";

    static final BiFunction<Row, RowMetadata, Comment> COMMENT_MAPPER =
            (row, rowMetadata) ->{
                var comment= Comment.builder()
                        .id(row.get("id", Integer.class))
                        .name(row.get("name", String.class))
                        .surname(row.get("surname", String.class))
                        .email(row.get("email", String.class))
                        .content(row.get("content", String.class))
                        .postId(row.get("post_id", Integer.class))
                        .build();
                comment.setCreatedAt(row.get("created_at", Long.class));
                comment.setUpdatedAt(row.get("updated_at", Long.class));
                return comment;
            };

    static final BiFunction<Row, RowMetadata, CommentDeep> COMMENT_DEEP_MAPPER=
            (row, rowMetadata) ->
                CommentDeep.builder()
                        .id(row.get("id", Integer.class))
                        .name(row.get("name", String.class))
                        .surname(row.get("surname", String.class))
                        .email(row.get("email", String.class))
                        .content(row.get("content", String.class))
                        .postId(row.get("post_id", Integer.class))
                        .createdAt(row.get("created_at", Long.class))
                        .updatedAt(row.get("updated_at", Long.class))
                        .postTitle(row.get("post_title",String.class))
                        .build();

    private final R2dbcEntityTemplate template;

    @Override
    public Mono<Comment> save(@NonNull Comment comment) {
        return template.insert(comment.withId(0));
    }

    @Override
    public Mono<Comment> update(Comment comment) {
        return template.update(comment);
    }

    @Override
    public Mono<Comment> findById(int id) {
        return template.selectOne(Query.query(Criteria.where("id").is(id)),Comment.class);
    }

    @Override
    public Flux<Comment> findAllByPostId(int postId, @NonNull Pageable pageable) {
        DatabaseClient.GenericExecuteSpec ges;
        if (pageable.isPaged() && pageable.getSort().isSorted()) {
            var order = pageable.getSort().stream().findFirst().orElseThrow();
            ges = template.getDatabaseClient()
                    .sql(String.format(SQL_FIND_ALL_BY_POST_ID_PAGED_ORDERED, order.getProperty(), order.isAscending() ?
                            Sort.Direction.ASC : Sort.Direction.DESC))
                    .bind(0, postId)
                    .bind(1, pageable.getPageSize())
                    .bind(2, pageable.getOffset());
        } else if (pageable.isPaged() && pageable.getSort().isUnsorted())
            ges = template.getDatabaseClient().sql(SQL_FIND_ALL_BY_POST_ID_PAGED_UNORDERED)
                    .bind(0, postId)
                    .bind(1, pageable.getPageSize())
                    .bind(2, pageable.getOffset());
        else if (pageable.isUnpaged() && pageable.getSort().isSorted()) {
            var order = pageable.getSort().stream().findFirst().orElseThrow();
            ges = template.getDatabaseClient()
                    .sql(String.format(SQL_FIND_ALL_BY_POST_ID_UNPAGED_ORDERED, order.getProperty(), order.isAscending() ?
                            Sort.Direction.ASC : Sort.Direction.DESC))
                    .bind(0, postId);
        } else
            ges = template.getDatabaseClient().sql(SQL_FIND_ALL_BY_POST_ID_UNPAGED_UNORDERED)
                    .bind(0, postId);
        return ges.map(COMMENT_MAPPER)
                .all();
    }

    @Override
    public Flux<CommentDeep> findAllDeep(@NonNull Pageable pageable) {
        DatabaseClient.GenericExecuteSpec ges;
        if (pageable.isPaged() && pageable.getSort().isSorted()) {
            var order = pageable.getSort().stream().findFirst().orElseThrow();
            ges = template.getDatabaseClient()
                    .sql(String.format(SQL_FIND_ALL_DEEP_PAGED_ORDERED, order.getProperty(), order.isAscending() ?
                            Sort.Direction.ASC : Sort.Direction.DESC))
                    .bind(0, pageable.getPageSize())
                    .bind(1, pageable.getOffset());
        } else if (pageable.isPaged() && pageable.getSort().isUnsorted())
            ges = template.getDatabaseClient().sql(SQL_FIND_ALL_DEEP_PAGED_UNORDERED)
                    .bind(0, pageable.getPageSize())
                    .bind(1, pageable.getOffset());
        else if (pageable.isUnpaged() && pageable.getSort().isSorted()) {
            var order = pageable.getSort().stream().findFirst().orElseThrow();
            ges = template.getDatabaseClient()
                    .sql(String.format(SQL_FIND_ALL_DEEP_UNPAGED_ORDERED, order.getProperty(), order.isAscending() ?
                            Sort.Direction.ASC : Sort.Direction.DESC));
        } else
            ges = template.getDatabaseClient().sql(SQL_FIND_ALL_DEEP_UNPAGED_UNORDERED);
        return ges.map(COMMENT_DEEP_MAPPER)
                .all();
    }

    @Override
    public Mono<Integer> deleteById(int id) {
        return template.delete(Query.query(Criteria.where("id").is(id)),Comment.class);
    }

    @Override
    public Mono<Long> count() {
        return template.count(Query.query(Criteria.empty()),Comment.class);
    }

    @Override
    public Mono<Long> countByPostId(int postId) {
        return template.count(Query.query(Criteria.where("post_id").is(postId)),Comment.class);
    }

    private String formatOrderedQuery(String query, Sort sort) throws IllegalArgumentException {
        var order = sort.stream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Sort parameter has no order"));
        return String.format(query, order.getProperty(),
                order.isAscending() ? Sort.Direction.ASC.name() : Sort.Direction.DESC.name());
    }

}
