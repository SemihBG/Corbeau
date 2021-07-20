package com.semihbkgr.corbeau.repository;

import com.semihbkgr.corbeau.model.Post;
import com.semihbkgr.corbeau.model.projection.PostShallow;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.CriteriaDefinition;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {

    private final R2dbcEntityTemplate template;

    @Override
    public Mono<Post> save(@NonNull Post post) {
        return template.insert(post);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public Flux<PostShallow> findAllPostShallow(Pageable pageable) {
        return template.getDatabaseClient().sql("SELECT db.posts.id, db.posts.title, db.posts.subject_id, db.subjects.name as subject_name, db.posts.created_by,db.posts.updated_by, db.posts.created_at, db.posts.updated_at FROm db.posts LEFT JOIN db.subjects ON db.posts.subject_id=db.subjects.id ORDER BY ? LIMIT ? OFFSET ?").bind(0,"id").bind(1,pageable.getPageSize()).bind(2,pageable.getOffset()).map((row, rowMetadata) -> PostShallow.builder()
                .id(row.get("id",Integer.class))
                .title(row.get("title",String.class))
                .subjectId(row.get("subject_id",Integer.class))
                .subjectName(row.get("subject_name",String.class))
                .createdBy(row.get("created_by",String.class))
                .updatedBy(row.get("updated_by",String.class))
                .createdAt(row.get("created_at",Long.class))
                .updatedAt(row.get("updated_at",Long.class))
                .build()).all();
    }

}
