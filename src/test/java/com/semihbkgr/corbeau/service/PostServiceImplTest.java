package com.semihbkgr.corbeau.service;

import com.semihbkgr.corbeau.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;

import static com.semihbkgr.corbeau.test.ModelUtils.*;
import static org.mockito.Mockito.*;

@Slf4j
@SuppressWarnings("SpringJavaAutowiredMembersInspection")
@ExtendWith(SpringExtension.class)
@Import(PostServiceImpl.class)
@DisplayName("PostServiceImpl Test")
@MockitoSettings(strictness = Strictness.LENIENT)
class PostServiceImplTest {

    //Constant parameters
    static final int POST_UPDATE_ID=1;

    @Autowired
    PostServiceImpl postService;

    @MockBean
    PostRepository postRepository;

    @BeforeEach
    void initializeMockObjects(){
        when(postRepository.save(defaultSavePost()))
                .thenReturn(defaultSavedPostMono(POST_UPDATE_ID));
        when(postRepository.save(defaultUpdatedPost(POST_UPDATE_ID)))
                .thenReturn(defaultUpdatedPostMono(POST_UPDATE_ID));
        when(postRepository.findById(POST_UPDATE_ID))
                .thenReturn(defaultSavedPostMono(POST_UPDATE_ID));
        log.info("Mock objects initialized");
    }

    @Test
    @DisplayName("save post returns saved post mono")
    void savePostReturnsSavedPostMono(){
        var postMono=postService.save(defaultSavePost());
        StepVerifier.create(postMono)
                .expectSubscription()
                .expectNext(defaultSavedPost(POST_UPDATE_ID))
                .verifyComplete();
    }

    @Test
    @DisplayName("save null returns mono error")
    void saveMonoReturnsMonoError(){
        var monoError=postService.save(null);
        StepVerifier.create(monoError)
                .expectSubscription()
                .expectError(NullPointerException.class)
                .verify();
    }

    @Test
    @DisplayName("update post returns updated post mono")
    void updateMonoReturnsUpdatedPostMono(){
        var postMono=postService.update(POST_UPDATE_ID,defaultUpdatePost());
        StepVerifier.create(postMono)
                .expectSubscription()
                .expectNext(defaultUpdatedPost(POST_UPDATE_ID))
                .verifyComplete();
    }

}