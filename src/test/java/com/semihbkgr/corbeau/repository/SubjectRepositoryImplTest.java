package com.semihbkgr.corbeau.repository;

import com.semihbkgr.corbeau.model.Subject;
import com.semihbkgr.corbeau.test.ModelUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import reactor.test.StepVerifier;

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

    static final String SQL_SUBJECT_TABLE_DROP =
            "DROP TABLE IF EXISTS subjects";

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    R2dbcEntityTemplate template;

    @BeforeEach
    void createSubjectTable() {
        template.getDatabaseClient()
                .sql(SQL_SUBJECT_TABLE_CREATE)
                .fetch()
                .all()
                .blockFirst();
        log.info("{} table created", SubjectRepository.TABLE_NAME);
    }

    @AfterEach
    void dropSubjectTable() {
        template.getDatabaseClient()
                .sql(SQL_SUBJECT_TABLE_DROP)
                .fetch()
                .all()
                .blockFirst();
        log.info("{} table dropped", SubjectRepository.TABLE_NAME);
    }

    @Test
    @DisplayName("save returns saved subejct mono")
    void saveReturnsSavedSubjectMono(){
        var subject= Subject.builder()
                .name("subejct")
                .build();
        ModelUtils.setAuditsOfAuditableModel(subject);
        var subjectMono=subjectRepository.save(subject)
                .log();
        StepVerifier.create(subjectMono)
                .expectSubscription()
                .expectNextMatches(savedSubject->
                        savedSubject.getName().equals(subject.getName()))
                .verifyComplete();
    }

    @Test
    @DisplayName("findAll returns empty flux")
    void findAllReturnsEmptyFlux(){
        var subjectFlux=subjectRepository.findAll()
                .log();
        StepVerifier.create(subjectFlux)
                .expectSubscription()
                .verifyComplete();
    }

    @Test
    @DisplayName("save and findAll return saved subjects flux")
    void saveAndFindAllReturnSavedSubjectsFlux(){
        var subject= Subject.builder()
                .name("subejct")
                .build();
        ModelUtils.setAuditsOfAuditableModel(subject);
        var subjectFlux = subjectRepository.save(subject)
                .thenMany(subjectRepository.findAll())
                .log();
        StepVerifier.create(subjectFlux)
                .expectSubscription()
                .expectNextMatches(savedSubject->
                        savedSubject.getName().equals(subject.getName()))
                .verifyComplete();
    }

}