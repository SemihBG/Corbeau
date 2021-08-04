package com.semihbkgr.corbeau.repository;

import com.semihbkgr.corbeau.test.DataSourceExtension;
import com.semihbkgr.corbeau.test.ModelUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import reactor.test.StepVerifier;

import static com.semihbkgr.corbeau.test.ModelUtils.*;

@DataR2dbcTest
@Import(SubjectRepositoryImpl.class)
@ExtendWith(DataSourceExtension.class)
@DisplayName("Role Repository Test")
class SubjectRepositoryImplTest {

    @Autowired
    SubjectRepositoryImpl subjectRepository;


}