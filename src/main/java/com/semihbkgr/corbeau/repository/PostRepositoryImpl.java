package com.semihbkgr.corbeau.repository;

import com.semihbkgr.corbeau.model.Post;
import com.semihbkgr.corbeau.model.projection.PostDeep;
import com.semihbkgr.corbeau.model.projection.PostInfo;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.CriteriaDefinition;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

import static org.springframework.data.relational.core.query.Criteria.where;
import static org.springframework.data.relational.core.query.Query.query;


@SuppressWarnings("ConstantConditions")
@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {

    static final String SQL_FIND_ALL_DEEP_PAGED_ORDERED =
            "SELECT posts.id, posts.title, posts.subject_id, posts.endpoint, " +
                    "posts.thumbnail_endpoint, posts.description, subjects.name as subject_name, " +
                    "posts.created_by, posts.updated_by, posts.created_at, posts.updated_at " +
                    "FROM posts LEFT JOIN subjects ON posts.subject_id=subjects.id " +
                    "ORDER BY %s %s " +
                    "LIMIT ? OFFSET ?";

    static final String SQL_FIND_ALL_DEEP_PAGED_UNORDERED =
            "SELECT posts.id, posts.title, posts.subject_id, posts.endpoint, " +
                    "posts.thumbnail_endpoint, posts.description, subjects.name as subject_name, " +
                    "posts.created_by, posts.updated_by, posts.created_at, posts.updated_at " +
                    "FROM posts LEFT JOIN subjects ON posts.subject_id=subjects.id " +
                    "LIMIT ? OFFSET ?";

    static final String SQL_FIND_ALL_DEEP_UNPAGED_ORDERED =
            "SELECT posts.id, posts.title, posts.subject_id, posts.endpoint, " +
                    "posts.thumbnail_endpoint, posts.description, subjects.name as subject_name, " +
                    "posts.created_by, posts.updated_by, posts.created_at, posts.updated_at " +
                    "FROM posts LEFT JOIN subjects ON posts.subject_id=subjects.id " +
                    "ORDER BY %s %s";

    static final String SQL_FIND_ALL_DEEP_UNPAGED_UNORDERED =
            "SELECT posts.id, posts.title, posts.subject_id, posts.endpoint, " +
                    "posts.thumbnail_endpoint, posts.description, subjects.name as subject_name, " +
                    "posts.created_by, posts.updated_by, posts.created_at, posts.updated_at " +
                    "FROM posts LEFT JOIN subjects ON posts.subject_id=subjects.id ";

    static final String SQL_FIND_ALL_BY_ACTIVATED_DEEP_PAGED_ORDERED =
            "SELECT posts.id, posts.title, posts.subject_id, posts.endpoint, " +
                    "posts.thumbnail_endpoint, posts.description, subjects.name as subject_name, " +
                    "posts.created_by, posts.updated_by, posts.created_at, posts.updated_at " +
                    "FROM posts LEFT JOIN subjects ON posts.subject_id=subjects.id " +
                    "WHERE posts.activated = ? " +
                    "ORDER BY %s %s " +
                    "LIMIT ? OFFSET ?";

    static final String SQL_FIND_ALL_BY_ACTIVATED_DEEP_PAGED_UNORDERED =
            "SELECT posts.id, db.posts.title, posts.subject_id, posts.endpoint, " +
                    "posts.thumbnail_endpoint, posts.description, subjects.name as subject_name, " +
                    "posts.created_by, posts.updated_by, posts.created_at, posts.updated_at " +
                    "FROM posts LEFT JOIN subjects ON posts.subject_id=subjects.id " +
                    "WHERE posts.activated = ?" +
                    "LIMIT ? OFFSET ?";

    static final String SQL_FIND_ALL_BY_ACTIVATED_DEEP_UNPAGED_ORDERED =
            "SELECT posts.id, posts.title, posts.subject_id, posts.endpoint, " +
                    "posts.thumbnail_endpoint, posts.description, subjects.name as subject_name, " +
                    "posts.created_by, posts.updated_by, posts.created_at, posts.updated_at " +
                    "FROM posts LEFT JOIN subjects ON posts.subject_id=subjects.id " +
                    "WHERE posts.activated = ?" +
                    "ORDER BY %s %s ";

    static final String SQL_FIND_ALL_BY_ACTIVATED_DEEP_UNPAGED_UNORDERED =
            "SELECT posts.id, posts.title, posts.subject_id, posts.endpoint, " +
                    "posts.thumbnail_endpoint, posts.description, subjects.name as subject_name, " +
                    "posts.created_by, posts.updated_by, posts.created_at, posts.updated_at " +
                    "FROM posts LEFT JOIN subjects ON posts.subject_id=subjects.id " +
                    "WHERE posts.activated = ?";

    static final String SQL_FIND_ALL_ACTIVATED_BY_SUBJECT_ID_INFO_PAGED_ORDERED =
            "SELECT posts.id,  posts.title, posts.endpoint, posts.thumbnail_endpoint, posts.description, " +
                    "posts.created_by, posts.updated_by, posts.created_at, posts.updated_at " +
                    "FROM posts WHERE posts.activated = TRUE AND posts.subject_id=? " +
                    "ORDER BY %s %s LIMIT ? OFFSET ?";

    static final String SQL_FIND_ALL_ACTIVATED_BY_SUBJECT_ID_INFO_PAGED_UNORDERED =
            "SELECT posts.id,  posts.title, posts.endpoint, posts.thumbnail_endpoint, posts.description, " +
                    "posts.created_by,  posts.updated_by, posts.created_at, posts.updated_at " +
                    "FROM posts WHERE posts.activated = TRUE AND posts.subject_id=? " +
                    "LIMIT ? OFFSET ?";

    static final String SQL_FIND_ALL_ACTIVATED_BY_SUBJECT_ID_INFO_UNPAGED_ORDERED =
            "SELECT posts.id,  posts.title, posts.endpoint, posts.thumbnail_endpoint, posts.description, " +
                    "posts.created_by,  posts.updated_by, posts.created_at, posts.updated_at " +
                    "FROM posts WHERE posts.activated = TRUE AND posts.subject_id=? " +
                    "ORDER BY %s %s";

    static final String SQL_FIND_ALL_ACTIVATED_BY_SUBJECT_ID_INFO_UNPAGED_UNORDERED =
            "SELECT posts.id,  posts.title, posts.endpoint, posts.thumbnail_endpoint, posts.description, " +
                    "posts.created_by,  posts.updated_by, posts.created_at, posts.updated_at " +
                    "FROM posts WHERE posts.activated = TRUE AND posts.subject_id=?";


    static final String SQL_FIND_ALL_POST_ID_AND_ACTIVATED_DEEP_PAGED_ORDERED =
            "SELECT posts.id, posts.title, posts.subject_id, posts.endpoint, " +
                    "posts.activated, posts.thumbnail_endpoint, posts.description, " +
                    "subjects.name as subject_name, posts.created_by, posts.updated_by, " +
                    "posts.created_at, posts.updated_at " +
                    "FROM tags_posts_join " +
                    "JOIN posts ON posts.id=post_id " +
                    "JOIN subjects ON subjects.id=posts.subject_id " +
                    "WHERE tag_id = ? AND activated = ? " +
                    "ORDER BY %s %s LIMIT ? OFFSET ?";

    static final String SQL_FIND_ALL_POST_ID_AND_ACTIVATED_DEEP_PAGED_UNORDERED =
            "SELECT posts.id, posts.title, posts.subject_id, posts.endpoint, " +
                    "posts.activated, posts.thumbnail_endpoint, posts.description, " +
                    "subjects.name as subject_name, posts.created_by, posts.updated_by, " +
                    "posts.created_at, posts.updated_at " +
                    "FROM tags_posts_join " +
                    "JOIN posts ON posts.id=post_id " +
                    "JOIN subjects ON subjects.id=posts.subject_id " +
                    "WHERE tag_id = ? AND activated = ? " +
                    "LIMIT ? OFFSET ?";

    static final String SQL_FIND_ALL_POST_ID_AND_ACTIVATED_DEEP_UNPAGED_ORDERED =
            "SELECT posts.id, posts.title, posts.subject_id, posts.endpoint, " +
                    "posts.activated, posts.thumbnail_endpoint, posts.description, " +
                    "subjects.name as subject_name, posts.created_by, posts.updated_by, " +
                    "posts.created_at, posts.updated_at " +
                    "FROM tags_posts_join " +
                    "JOIN posts ON posts.id=post_id " +
                    "JOIN subjects ON subjects.id=posts.subject_id " +
                    "WHERE tag_id = ? AND activated = ? " +
                    "ORDER BY %s %s ";

    static final String SQL_FIND_ALL_POST_ID_AND_ACTIVATED_DEEP_UNPAGED_UNORDERED =
            "SELECT posts.id, posts.title, posts.subject_id, posts.endpoint, " +
                    "posts.activated, posts.thumbnail_endpoint, posts.description, " +
                    "subjects.name as subject_name, posts.created_by, posts.updated_by, " +
                    "posts.created_at, posts.updated_at " +
                    "FROM tags_posts_join " +
                    "JOIN posts ON posts.id=post_id " +
                    "JOIN subjects ON subjects.id=posts.subject_id " +
                    "WHERE tag_id = ? AND activated = ?";

    static final String SQL_SEARCH_BY_TITLE_AND_ACTIVATED_DEEP =
            "SELECT posts.id, posts.title, posts.subject_id, posts.endpoint, " +
                    "posts.thumbnail_endpoint, posts.description, " +
                    "subjects.name as subject_name, " +
                    "posts.created_by, posts.updated_by, posts.created_at, posts.updated_at " +
                    "FROM db.posts " +
                    "JOIN subjects ON subjects.id=subject_id " +
                    "WHERE activated=? AND MATCH (title) AGAINST (? IN NATURAL LANGUAGE MODE)>0.1";


    static final String SQL_COUNT_BY_POST_ID_AND_ACTIVATED =
            "SELECT COUNT(*) FROM tags_posts_join JOIN posts ON posts.id=post_id WHERE tag_id=? AND posts.activated=?";

    static final BiFunction<Row, RowMetadata, PostDeep> POST_DEEP_MAPPER =
            (row, rowMetadata) ->
                    PostDeep.builder()
                            .id(row.get("id", Integer.class))
                            .title(row.get("title", String.class))
                            .subjectId(row.get("subject_id", Integer.class))
                            .endpoint(row.get("endpoint", String.class))
                            .thumbnailEndpoint(row.get("thumbnail_endpoint", String.class))
                            .description(row.get("description", String.class))
                            .subjectName(row.get("subject_name", String.class))
                            .createdBy(row.get("created_by", String.class))
                            .updatedBy(row.get("updated_by", String.class))
                            .createdAt(row.get("created_at", Long.class))
                            .updatedAt(row.get("updated_at", Long.class))
                            .build();

    static final BiFunction<Row, RowMetadata, PostInfo> POST_INFO_MAPPER =
            (row, rowMetadata) ->
                    PostInfo.builder()
                            .id(row.get("id", Integer.class))
                            .title(row.get("title", String.class))
                            .endpoint(row.get("endpoint", String.class))
                            .thumbnailEndpoint(row.get("thumbnail_endpoint", String.class))
                            .description(row.get("description", String.class))
                            .createdBy(row.get("created_by", String.class))
                            .updatedBy(row.get("updated_by", String.class))
                            .createdAt(row.get("created_at", Long.class))
                            .updatedAt(row.get("updated_at", Long.class))
                            .build();


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
        return template.selectOne(query(where("id").is(id)), Post.class);
    }

    @Override
    public Mono<Post> findByEndpoint(@NonNull String endpoint) {
        return template.selectOne(query(where("endpoint").is(endpoint)), Post.class);
    }

    @Override
    public Flux<PostDeep> findAllDeep(@NonNull Pageable pageable) {
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

        return ges.map(POST_DEEP_MAPPER)
                .all();
    }

    @Override
    public Flux<PostDeep> findAllByActivatedDeep(boolean activated, @NonNull Pageable pageable) {
        DatabaseClient.GenericExecuteSpec ges;
        if (pageable.isPaged() && pageable.getSort().isSorted()) {
            ges = template.getDatabaseClient()
                    .sql(formatOrderedQuery(SQL_FIND_ALL_BY_ACTIVATED_DEEP_PAGED_ORDERED,pageable.getSort()))
                    .bind(0, activated)
                    .bind(1, pageable.getPageSize())
                    .bind(2, pageable.getOffset());
        } else if (pageable.isPaged() && pageable.getSort().isUnsorted()) {
            ges = template.getDatabaseClient()
                    .sql(SQL_FIND_ALL_BY_ACTIVATED_DEEP_PAGED_UNORDERED)
                    .bind(0, String.valueOf(activated))
                    .bind(1, pageable.getPageSize())
                    .bind(2, pageable.getOffset());
        } else if (pageable.isUnpaged() && pageable.getSort().isSorted()) {
            ges = template.getDatabaseClient()
                    .sql(formatOrderedQuery(SQL_FIND_ALL_BY_ACTIVATED_DEEP_UNPAGED_ORDERED,pageable.getSort()))
                    .bind(0, activated);
        } else {
            ges = template.getDatabaseClient().sql(SQL_FIND_ALL_BY_ACTIVATED_DEEP_UNPAGED_UNORDERED)
                    .bind(0, String.valueOf(activated));
        }
        return ges.map(POST_DEEP_MAPPER)
                .all();
    }

    @Override
    public Flux<PostDeep> findAllByTagIdAndActivatedDeep(int tagId, boolean activated, @NonNull Pageable pageable) {
        DatabaseClient.GenericExecuteSpec ges;
        if (pageable.isPaged() && pageable.getSort().isSorted()) {
            ges = template.getDatabaseClient()
                    .sql(formatOrderedQuery(SQL_FIND_ALL_POST_ID_AND_ACTIVATED_DEEP_PAGED_ORDERED, pageable.getSort()))
                    .bind(0, tagId)
                    .bind(1, activated)
                    .bind(2, pageable.getPageSize())
                    .bind(3, pageable.getOffset());
        } else if (pageable.isPaged() && pageable.getSort().isUnsorted()) {
            ges = template.getDatabaseClient()
                    .sql(SQL_FIND_ALL_POST_ID_AND_ACTIVATED_DEEP_PAGED_UNORDERED)
                    .bind(0, tagId)
                    .bind(1, activated)
                    .bind(2, pageable.getPageSize())
                    .bind(3, pageable.getOffset());
        } else if (pageable.isUnpaged() && pageable.getSort().isSorted()) {
            ges = template.getDatabaseClient()
                    .sql(formatOrderedQuery(SQL_FIND_ALL_POST_ID_AND_ACTIVATED_DEEP_UNPAGED_ORDERED, pageable.getSort()))
                    .bind(0, tagId)
                    .bind(1, activated);
        } else {
            ges = template.getDatabaseClient()
                    .sql(SQL_FIND_ALL_POST_ID_AND_ACTIVATED_DEEP_UNPAGED_UNORDERED)
                    .bind(0, tagId)
                    .bind(1, activated);
        }
        return ges.map(POST_DEEP_MAPPER)
                .all();
    }

    @Override
    public Flux<PostInfo> findAllActivatedBySubjectIdInfo(int subjectId, @NonNull Pageable pageable) {
        DatabaseClient.GenericExecuteSpec ges;
        if (pageable.isPaged() && pageable.getSort().isSorted()) {
            var order = pageable.getSort().stream().findFirst().orElseThrow();
            ges = template.getDatabaseClient()
                    .sql(String.format(SQL_FIND_ALL_ACTIVATED_BY_SUBJECT_ID_INFO_PAGED_ORDERED, order.getProperty(), order.isAscending() ?
                            Sort.Direction.ASC : Sort.Direction.DESC))
                    .bind(0, subjectId)
                    .bind(1, pageable.getPageSize())
                    .bind(2, pageable.getOffset());
        } else if (pageable.isPaged() && pageable.getSort().isUnsorted())
            ges = template.getDatabaseClient().sql(SQL_FIND_ALL_ACTIVATED_BY_SUBJECT_ID_INFO_PAGED_UNORDERED)
                    .bind(0, subjectId)
                    .bind(1, pageable.getPageSize())
                    .bind(2, pageable.getOffset());
        else if (pageable.isUnpaged() && pageable.getSort().isSorted()) {
            var order = pageable.getSort().stream().findFirst().orElseThrow();
            ges = template.getDatabaseClient()
                    .sql(String.format(SQL_FIND_ALL_ACTIVATED_BY_SUBJECT_ID_INFO_UNPAGED_ORDERED, order.getProperty(), order.isAscending() ?
                            Sort.Direction.ASC : Sort.Direction.DESC))
                    .bind(0, subjectId);
        } else
            ges = template.getDatabaseClient().sql(SQL_FIND_ALL_ACTIVATED_BY_SUBJECT_ID_INFO_UNPAGED_UNORDERED)
                    .bind(0, subjectId);

        return ges.map(POST_INFO_MAPPER)
                .all();
    }

    @Override
    public Flux<PostDeep> searchByTitleAndActivatedDeep(@NonNull String title, boolean activated) {
        return template.getDatabaseClient()
                .sql(SQL_SEARCH_BY_TITLE_AND_ACTIVATED_DEEP)
                .bind(0, activated)
                .bind(1, title)
                .map(POST_DEEP_MAPPER)
                .all();
    }


    @Override
    public Mono<Long> count() {
        return template.count(query(CriteriaDefinition.empty()), Post.class);
    }

    @Override
    public Mono<Long> countBySubjectId(int subjectId) {
        return template.count(query(where("subject_id").is(subjectId)), Post.class);
    }

    @Override
    public Mono<Long> countByTagIdAndActivated(int tagId, boolean activated) {
        return template.getDatabaseClient()
                .sql(SQL_COUNT_BY_POST_ID_AND_ACTIVATED)
                .bind(0, tagId)
                .bind(1, activated)
                .map((row, rowMetadata) -> row.get(0, Long.class))
                .all()
                .single();
    }

    @Override
    public Mono<Long> countBySubjectIdAndActivated(int subjectId, boolean activated) {
        return template.count(query(
                where("subject_id").is(subjectId).and(where("activated").is(activated))
        ), Post.class);
    }

    private String formatOrderedQuery(String query, Sort sort) throws IllegalArgumentException {
        var order = sort.stream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Sort parameter has no order"));
        return String.format(query, order.getProperty(),
                order.isAscending() ? Sort.Direction.ASC.name() : Sort.Direction.DESC.name());
    }

}
