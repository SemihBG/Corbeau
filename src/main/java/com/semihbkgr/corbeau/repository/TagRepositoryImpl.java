package com.semihbkgr.corbeau.repository;

import com.semihbkgr.corbeau.model.Tag;
import com.semihbkgr.corbeau.model.projection.TagDeep;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SuppressWarnings("ConstantConditions")
@Repository
@RequiredArgsConstructor
public class TagRepositoryImpl implements TagRepository {

    static final String SQL_SELECT_ALL_BY_POST_ID =
            "SELECT tags.id, tags.name FROM tags_posts_join " +
                    "LEFT JOIN tags ON tags.id=tags_posts_join.tag_id " +
                    "WHERE tags_posts_join.post_id=?";

    static final String SQL_SELECT_ALL_TAG_DEEP =
            "SELECT tags.id, tags.name, tags.created_by, tags.updated_by, tags.created_at,tags.updated_at, (SELECT COUNT(*) FROM db.tags_posts_join WHERE post_id=id) as post_count FROM db.tags";

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
    public Mono<Tag> findById(int id) {
        return template.selectOne(Query.query(Criteria.where("id").is(id)), Tag.class);
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
                .map((row, rowMetadata) ->
                        TagDeep.builder()
                                .id(row.get("id", Integer.class))
                                .name(row.get("name", String.class))
                                .createdBy(row.get("created_by", String.class))
                                .updatedBy(row.get("updated_by", String.class))
                                .createdAt(row.get("created_at", Long.class))
                                .updatedAt(row.get("updated_at", Long.class))
                                .postCount(row.get("post_count", Long.class))
                                .build()
                )
                .all();
    }

    @Override
    public Mono<Integer> deleteById(int id) {
        return template.delete(Query.query(Criteria.where("id").is(id)), Tag.class);
    }

}
