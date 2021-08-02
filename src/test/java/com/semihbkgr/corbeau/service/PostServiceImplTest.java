package com.semihbkgr.corbeau.service;

import com.semihbkgr.corbeau.repository.PostRepository;
import com.semihbkgr.corbeau.repository.PostRepositoryImpl;
import com.semihbkgr.corbeau.test.ModelUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;

import static com.semihbkgr.corbeau.test.ModelUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SuppressWarnings("SpringJavaAutowiredMembersInspection")
@ExtendWith(SpringExtension.class)
@Import(PostServiceImpl.class)
@DisplayName("PostServiceImpl Test")
@Slf4j
class PostServiceImplTest {

    @Autowired
    PostServiceImpl postService;

    @MockBean
    PostRepository postRepository;

    @BeforeEach
    void initializeMockObjects(){
        when(postRepository.save(defaultPost()))
                .thenReturn(defaultSavedPostMono());
        log.info("Mock objects initialized");
    }

    @Test
    @DisplayName("save returns saved post mono")
    void saveReturnSavedPostMono(){
        var postMono=postService.save(defaultPost());
        StepVerifier.create(postMono)
                .expectSubscription()
                .expectNext(defaultPost())
                .verifyComplete();
    }


}