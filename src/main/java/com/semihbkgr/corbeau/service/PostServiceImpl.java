package com.semihbkgr.corbeau.service;

import com.semihbkgr.corbeau.error.IllegalValueException;
import com.semihbkgr.corbeau.model.Post;
import com.semihbkgr.corbeau.model.projection.PostDeep;
import com.semihbkgr.corbeau.model.projection.PostInfo;
import com.semihbkgr.corbeau.repository.PostRepository;
import com.semihbkgr.corbeau.repository.TagRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public Mono<Post> save(@NonNull Post post) {
        return postRepository.save(post.withId(0));
    }

    @Override
    public Mono<Post> update(int id, @NonNull Post post) throws IllegalValueException {
        if (id <= 0)
            throw new IllegalValueException("id must be positive value", PostRepository.TABLE_NAME, "id", id);
        return postRepository.findById(id)
                .switchIfEmpty(Mono.error(() ->
                        new IllegalValueException("No post fond by given id", PostRepository.TABLE_NAME, "id", id)))
                .map(savedPost -> {
                    savedPost.setTitle(post.getTitle());
                    savedPost.setContent(post.getContent());
                    savedPost.setSubjectId(post.getSubjectId());
                    savedPost.setActivated(post.isActivated());
                    savedPost.setEndpoint(post.getEndpoint());
                    savedPost.setThumbnailEndpoint(post.getThumbnailEndpoint());
                    savedPost.setDescription(post.getDescription());
                    return savedPost;
                })
                .flatMap(postRepository::update);
    }

    @Override
    public Mono<Post> findByEndpoint(@NonNull String endpoint) {
        return postRepository.findByEndpoint(endpoint);
    }

    @Override
    public Flux<PostDeep> findAllDeep(@NonNull Pageable pageable) {
        return postRepository.findAllDeep(pageable);
    }

    @Override
    public Flux<PostDeep> findAllByActivatedDeep(boolean activated, @NonNull Pageable pageable) {
        return postRepository.findAllByActivatedDeep(activated, pageable);
    }

    @Override
    public Flux<PostDeep> findAllByTagIdAndActivatedDeep(int tagId, boolean activated, @NonNull Pageable pageable) throws IllegalValueException {
        if (tagId <= 0)
            throw new IllegalValueException("tag_id must be positive value", TagRepository.TABLE_NAME, "id", tagId);
        return postRepository.findAllByTagIdAndActivatedDeep(tagId, activated, pageable);
    }

    @Override
    public Flux<PostInfo> findAllActivatedBySubjectIdInfo(int subjectId, @NonNull Pageable pageable) throws IllegalValueException {
        if (subjectId <= 0)
            throw new IllegalValueException("subject_id must be positive value", PostRepository.TABLE_NAME, "subject_id", subjectId);
        return postRepository.findAllActivatedBySubjectIdInfo(subjectId, pageable);
    }

    @Override
    public Flux<PostDeep> searchByTitleAndActivatedDeep(@NonNull String title, boolean acitavated) {
        return postRepository.searchByTitleAndActivatedDeep(title, acitavated);
    }

    @Override
    public Mono<Long> count() {
        return postRepository.count();
    }

    @Override
    public Mono<Long> countBySubjectId(int subjectId) throws IllegalValueException {
        if (subjectId <= 0)
            throw new IllegalValueException("subject_id must be positive value", PostRepository.TABLE_NAME, "subject_id", subjectId);
        return postRepository.countBySubjectId(subjectId);
    }

    @Override
    public Mono<Long> countBySubjectIdAndActivated(int subjectId, boolean activated) throws IllegalValueException {
        if (subjectId <= 0)
            throw new IllegalValueException("subject_id must be positive value", PostRepository.TABLE_NAME, "subject_id", subjectId);
        return postRepository.countBySubjectIdAndActivated(subjectId, activated);
    }

    @Override
    public Mono<Integer> deletePost(int id) throws IllegalValueException {
        if (id <= 0)
            throw new IllegalValueException("id must be positive value", PostRepository.TABLE_NAME, "id", id);
        return postRepository.deleteByPostId(id);
    }

    @Override
    public Mono<Void> updateTagPostJoin(int postId, @NonNull List<Integer> tagsId) {
        return postRepository.updateTagPostJoin(postId, tagsId);
    }

}
