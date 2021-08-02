package com.semihbkgr.corbeau.repository;

import com.semihbkgr.corbeau.test.ModelUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import reactor.test.StepVerifier;

import static com.semihbkgr.corbeau.test.ModelUtils.*;

@SuppressWarnings("SqlResolve")
@DataR2dbcTest
@Import(SubjectRepositoryImpl.class)
@DisplayName("Role Repository Test")
@Slf4j
class SubjectRepositoryImplTest {

    static final String SQL_SUBJECT_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS subjects\n" +
                    "(\n" +
                    "    id         INT PRIMARY KEY AUTO_INCREMENT,\n" +
                    "    name       VARCHAR(32)     NOT NULL UNIQUE,\n" +
                    "    created_by VARCHAR(32),\n" +
                    "    updated_by VARCHAR(32),\n" +
                    "    created_at BIGINT NOT NULL DEFAULT 0,\n" +
                    "    updated_at BIGINT NOT NULL DEFAULT 0\n" +
                    ")";


    static final String SQL_POST_TABLE_CREATE=
            "CREATE TABLE IF NOT EXISTS posts\n" +
                    "(\n" +
                    "    id         INT PRIMARY KEY AUTO_INCREMENT,\n" +
                    "    title      VARCHAR(64)     NOT NULL,\n" +
                    "    content    LONGTEXT,\n" +
                    "    subject_id INT             NOT NULL,\n" +
                    "    activated  BOOLEAN                  DEFAULT FALSE,\n" +
                    "    endpoint   VARCHAR(64)     NOT NULL UNIQUE,\n" +
                    "    created_by VARCHAR(32),\n" +
                    "    updated_by VARCHAR(32),\n" +
                    "    created_at BIGINT NOT NULL DEFAULT 0,\n" +
                    "    updated_at BIGINT NOT NULL DEFAULT 0,\n" +
                    "    UNIQUE (title, subject_id),\n" +
                    "    FOREIGN KEY (subject_id) REFERENCES subjects (id)\n" +
                    ")";

    static final String SQL_SUBJECT_TABLE_DROP = "DROP TABLE subjects";

    static final String SQL_POST_TABLE_DROP="DROP TABLE posts";

    @Autowired
    SubjectRepositoryImpl subjectRepository;

    @Autowired
    R2dbcEntityTemplate template;

    @BeforeEach
    void createSubjectsTable() {
        template.getDatabaseClient()
                .sql(SQL_SUBJECT_TABLE_CREATE)
                .fetch()
                .all()
                .blockFirst();
        log.info("subjects table created");
    }

    @AfterEach
    void dropSubjectsTable() {
        template.getDatabaseClient()
                .sql(SQL_SUBJECT_TABLE_DROP)
                .fetch()
                .all()
                .blockFirst();
        log.info("subjects table dropped");
    }

    @Test
    @DisplayName("save(Subject) return Mono<Subject> saved Subject Mono")
    void saveSubjectReturnSavedSubjectMono(){
        var subject= defaultSaveSubject();
        ModelUtils.setAuditsOfAuditableModel(subject);
        var subjectMono=subjectRepository.save(subject)
                .log();
        StepVerifier.create(subjectMono)
                .expectSubscription()
                .expectNext(defaultSavedSubject())
                .verifyComplete();
    }

    @Test
    @DisplayName("update(Subject) return Mono<Subject> error Mono")
    void updateSubjectReturnErrorMono(){
        var sujbect=defaultUpdateSubject();
        var errorMono=subjectRepository.update(sujbect)
                .log();
        StepVerifier.create(errorMono)
                .expectSubscription()
                .expectError(TransientDataAccessResourceException.class)
                .verify();
    }

    @Test
    @DisplayName("save(Subject) and update(Subject) return Mono<Subject> saved Subject Mono")
    void saveSubjectAndUpdateSubjectReturnSubjectMono(){
        var subject=defaultSaveSubject();
        var subjectMono=subjectRepository.save(subject)
                .then(subjectRepository.update(defaultSavedSubject()))
                .log();
        StepVerifier.create(subjectMono)
                .expectSubscription()
                .expectNext(defaultSavedSubject())
                .verifyComplete();
    }

    @Test
    @DisplayName("findById(int) return Mono<Subject> empty Mono")
    void findByIdRetrunEmptyMono(){
        var emptyMono=subjectRepository.findById(1)
                .log();
        StepVerifier.create(emptyMono)
                .expectSubscription()
                .verifyComplete();
    }

    @Test
    @DisplayName("save(Subject) and findById(int) return Mono<Subject> saved Subject Mono")
    void saveSubjectAndfindByIdRetrunSavedSubjectMono(){
        var subject=defaultSaveSubject();
        var subjectMono=subjectRepository.save(subject)
                .then(subjectRepository.findById(1))
                .log();
        StepVerifier.create(subjectMono)
                .expectSubscription()
                .expectNext(defaultSavedSubject())
                .verifyComplete();
    }

    @Test
    @DisplayName("findByNameDeep(String) return Mono<Subject> empty Mono")
    void findByNameDeepReturnEmptyMono(){
        createPostsTable();
        var emptyMono=subjectRepository.findByNameDeep("name")
                .log();
        StepVerifier.create(emptyMono)
                .expectSubscription()
                .verifyComplete();
        dropPostsTable();
    }

    @Test
    @DisplayName("save(Subject) and findByNameDeep(String) return Mono<Subject> saved SubjectDeep Mono")
    void saveAndfindByNameDeepReturnSavedSubjectDeepMono(){
        createPostsTable();
        var subject = defaultSaveSubject();
        var emptyMono=subjectRepository.save(subject)
                .then(subjectRepository.findByNameDeep(subject.getName()))
                .log();
        StepVerifier.create(emptyMono)
                .expectSubscription()
                .expectNextMatches(subjectDeep -> subjectDeep.getName().equals(subject.getName()) && subjectDeep.getPostCount()==0L)
                .verifyComplete();
        dropPostsTable();
    }

    @Test
    @DisplayName("findAll() return Flux<Subject> empty Flux")
    void findAllReturnEmptyFlux(){
        var subjectFlux=subjectRepository.findAll()
                .log();
        StepVerifier.create(subjectFlux)
                .expectSubscription()
                .verifyComplete();
    }

    @Test
    @DisplayName("save(Subject) and findAll() return Flux<Subject> saved Subject Mono")
    void saveSubjectAndFindAllReturnSavedSubjectsFlux(){
        var subject= defaultSaveSubject();
        ModelUtils.setAuditsOfAuditableModel(subject);
        var subjectFlux = subjectRepository.save(subject)
                .thenMany(subjectRepository.findAll())
                .log();
        StepVerifier.create(subjectFlux)
                .expectSubscription()
                .expectNext(defaultSavedSubject())
                .verifyComplete();
    }

    private void createPostsTable(){
        template.getDatabaseClient()
                .sql(SQL_POST_TABLE_CREATE)
                .fetch()
                .all()
                .blockFirst();
        log.info("posts table created");
    }

    private void dropPostsTable(){
        template.getDatabaseClient()
                .sql(SQL_POST_TABLE_DROP)
                .fetch()
                .all()
                .blockFirst();
        log.info("posts table dropped");
    }


}