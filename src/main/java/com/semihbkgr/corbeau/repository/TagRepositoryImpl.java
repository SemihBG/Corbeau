package com.semihbkgr.corbeau.repository;

import com.semihbkgr.corbeau.model.Tag;
import com.semihbkgr.corbeau.model.projection.TagDeep;
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
public class TagRepositoryImpl implements TagRepository {

    static final String SQL_SELECT_ALL_BY_POST_ID =
            "SELECT tags.id, tags.name FROM tags_posts_join " +
                    "LEFT JOIN tags ON tags.id=tags_posts_join.tag_id " +
                    "WHERE tags_posts_join.post_id=?";

    static final String SQL_SELECT_ALL_TAG_DEEP =
            "SELECT tags.id, tags.name, tags.created_by, tags.updated_by, " +
                    "tags.created_at,tags.updated_at, " +
                    "(SELECT COUNT(*) FROM tags_posts_join WHERE tag_id=id) as post_count " +
                    "FROM tags";

    static final String SQL_SELECT_ALL_TAG_BY_ACTIVATED_DEEP =
            "SELECT tags.id,tags.name,tags.created_by,tags.updated_by, " +
                    "tags.created_at, tags.updated_at, " +
                    "(SELECT COUNT(*) FROM tags_posts_join " +
                    "JOIN posts ON posts.id = post_id " +
                    "WHERE tag_id = tags.id " +
                    "AND activated = ?) as post_count " +
                    "FROM tags ORDER BY post_count DESC";

    static final String SQL_SELECT_TAG_BY_NAME_AND_POST_ACTIVATED_DEEP =
            "SELECT tags.id,tags.name,tags.created_by,tags.updated_by, " +
                    "tags.created_at, tags.updated_at, " +
                    "(SELECT COUNT(*) FROM tags_posts_join " +
                    "JOIN posts ON posts.id = post_id " +
                    "WHERE tag_id = tags.id " +
                    "AND activated = ?) as post_count " +
                    "FROM tags WHERE name=?";

    static final BiFunction<Row, RowMetadata, TagDeep> TAG_DEEP_MAPPER = (row, rowMetadata) ->
            TagDeep.builder()
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
    public Mono<Tag> save(@NonNull Tag tag) {
        return template.insert(tag);
    }

    @Override
    public Mono<Tag> update(Tag tag) {
        return template.update(tag);
    }

    @Override
    public Mono<TagDeep> findByNameAndPostActivatedDeep(String name, boolean activated) {
        return template.getDatabaseClient()
                .sql(SQL_SELECT_TAG_BY_NAME_AND_POST_ACTIVATED_DEEP)
                .bind(0, activated)
                .bind(1, name)
                .map(TAG_DEEP_MAPPER)
                .all()
                .single();
    }

    @Override
    public Mono<Tag> findById(int id) {
        return template.selectOne(Query.query(Criteria.where("id").is(id)), Tag.class);
    }

    @Override
    public Mono<Tag> findByName(@NonNull String name) {
        return template.selectOne(Query.query(Criteria.where("name").is(name)), Tag.class);
    }

    @Override
    public Flux<Tag> findAll() {
        return template.select(Query.query(Criteria.empty()), Tag.class);
    }

    @Override
    public Flux<Tag> findAllByPostId(int portId) {
        return template.getDatabaseClient()
                .sql(SQL_SELECT_ALL_BY_POST_ID)
                .bind(0, portId)
                .map((row, rowMetadata) ->
                        Tag.builder()
                                .id(row.get("id", Integer.class))
                                .name(row.get("name", String.class))
                                .build())
                .all();
    }

    @Override
    public Flux<TagDeep> findAllDeep() {
        return template.getDatabaseClient()
                .sql(SQL_SELECT_ALL_TAG_DEEP)
                .map(TAG_DEEP_MAPPER)
                .all();
    }

    @Override
    public Flux<TagDeep> findAllByActivatedDeep(boolean activated) {
        return template.getDatabaseClient()
                .sql(SQL_SELECT_ALL_TAG_BY_ACTIVATED_DEEP)
                .bind(0, activated)
                .map(TAG_DEEP_MAPPER)
                .all();
    }

    @Override
    public Mono<Integer> deleteById(int id) {
        return template.delete(Query.query(Criteria.where("id").is(id)), Tag.class);
    }

}
