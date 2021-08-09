DROP TABLE IF EXISTS db.comments;
DROP TABLE IF EXISTS db.tags_posts_join;
DROP TABLE IF EXISTS db.posts;
DROP TABLE IF EXISTS db.tags;
DROP TABLE IF EXISTS db.subjects;
DROP TABLE IF EXISTS db.images;
DROP TABLE IF EXISTS db.moderators;
DROP TABLE IF EXISTS db.roles;

CREATE TABLE IF NOT EXISTS db.roles
(
    id   INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(32) NOT NULL UNIQUE
);
CREATE TABLE IF NOT EXISTS db.moderators
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    name       VARCHAR(32)     NOT NULL UNIQUE,
    password   VARCHAR(256)    NOT NULL,
    email      VARCHAR(128)    NOT NULL UNIQUE,
    role_id    INT             NOT NULL,
    created_at BIGINT UNSIGNED NOT NULL DEFAULT 0,
    updated_at BIGINT UNSIGNED NOT NULL DEFAULT 0,
    FOREIGN KEY (role_id) REFERENCES db.roles (id)
);
CREATE TABLE IF NOT EXISTS db.subjects
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    name       VARCHAR(32)     NOT NULL UNIQUE,
    created_by VARCHAR(32),
    updated_by VARCHAR(32),
    created_at BIGINT UNSIGNED NOT NULL DEFAULT 0,
    updated_at BIGINT UNSIGNED NOT NULL DEFAULT 0
);
CREATE TABLE IF NOT EXISTS db.posts
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    title      VARCHAR(64)     NOT NULL,
    content    LONGTEXT,
    subject_id INT             NOT NULL,
    activated  BOOLEAN                  DEFAULT FALSE,
    endpoint   VARCHAR(64)     NOT NULL UNIQUE,
    created_by VARCHAR(32),
    updated_by VARCHAR(32),
    created_at BIGINT UNSIGNED NOT NULL DEFAULT 0,
    updated_at BIGINT UNSIGNED NOT NULL DEFAULT 0,
    UNIQUE KEY title_subject (title, subject_id),
    FOREIGN KEY (subject_id) REFERENCES db.subjects (id)
);
CREATE TABLE IF NOT EXISTS db.tags
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    name       VARCHAR(32)     NOT NULL,
    created_by VARCHAR(32),
    updated_by VARCHAR(32),
    created_at BIGINT UNSIGNED NOT NULL DEFAULT 0,
    updated_at BIGINT UNSIGNED NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS db.tags_posts_join
(
    tag_id  INT NOT NULL,
    post_id INT NOT NULL,
    UNIQUE KEY tag_post (tag_id, post_id)
);
CREATE TABLE IF NOT EXISTS db.images
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    name       VARCHAR(64)     NOT NULL,
    extension  VARCHAR(4)      NOT NULL,
    width      INT             NOT NULL,
    height     INT             NOT NULL,
    size       LONG            NOT NULL,
    created_by VARCHAR(32),
    updated_by VARCHAR(32),
    created_at BIGINT UNSIGNED NOT NULL DEFAULT 0,
    updated_at BIGINT UNSIGNED NOT NULL DEFAULT 0,
    UNIQUE KEY name_extension (name, extension)
);
CREATE TABLE IF NOT EXISTS db.comments
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    name       VARCHAR(32)     NOT NULL,
    surname    VARCHAR(32)     NOT NULL,
    email      VARCHAR(64)     NOT NULL,
    content    VARCHAR(256)    NOT NULL,
    post_id    INT             NOT NULL,
    created_at BIGINT UNSIGNED NOT NULL DEFAULT 0,
    updated_at BIGINT UNSIGNED NOT NULL DEFAULT 0,
    FOREIGN KEY (post_id) REFERENCES db.posts (id)
);

INSERT INTO db.subjects (id, name)
VALUES (1, 'Java'),
       (2, 'Go'),
       (3, 'Kotlin');

INSERT INTO db.posts(title, content, subject_id, endpoint, created_at, updated_at, activated)
VALUES ('Post Java 01', 'content', 1, 'post-java-01', UNIX_TIMESTAMP() * 1000,
        UNIX_TIMESTAMP() * 1000 + (RAND() * 3600000), true),
       ('Post Go 01', 'content', 2, 'post-go-01', UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000 + (RAND() * 3600000),
        true),
       ('Post Kotlin 01', 'content', 3, 'post-kotlin-01', UNIX_TIMESTAMP() * 1000,
        UNIX_TIMESTAMP() * 1000 + (RAND() * 3600000), true),
       ('Post Java 02', 'content', 1, 'post-java-02', UNIX_TIMESTAMP() * 1000,
        UNIX_TIMESTAMP() * 1000 + (RAND() * 3600000), true),
       ('Post Go 02', 'content', 2, 'post-go-02', UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000 + (RAND() * 3600000),
        true),
       ('Post Kotlin 02', 'content', 3, 'post-kotli-02', UNIX_TIMESTAMP() * 1000,
        UNIX_TIMESTAMP() * 1000 + (RAND() * 3600000), true),
       ('Post Java 03', 'content', 1, 'post-java-03', UNIX_TIMESTAMP() * 1000,
        UNIX_TIMESTAMP() * 1000 + (RAND() * 3600000), true),
       ('Post Go 03', 'content', 2, 'post-go-03', UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000 + (RAND() * 3600000),
        true),
       ('Post Kotlin 03', 'content', 3, 'post-kotli-03', UNIX_TIMESTAMP() * 1000,
        UNIX_TIMESTAMP() * 1000 + (RAND() * 3600000), true),
       ('Post Java 04', 'content', 1, 'post-java-04', UNIX_TIMESTAMP() * 1000,
        UNIX_TIMESTAMP() * 1000 + (RAND() * 3600000), true),
       ('Post Go0 4', 'content', 2, 'post-go-04', UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000 + (RAND() * 3600000),
        true),
       ('Post Kotlin 04', 'content', 3, 'post-kotli-04', UNIX_TIMESTAMP() * 1000,
        UNIX_TIMESTAMP() * 1000 + (RAND() * 3600000), true),
       ('Post Java 05', 'content', 1, 'post-java-05', UNIX_TIMESTAMP() * 1000,
        UNIX_TIMESTAMP() * 1000 + (RAND() * 3600000), false),
       ('Post Go 05', 'content', 2, 'post-go-05', UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000 + (RAND() * 3600000),
        false),
       ('Post Kotlin 05', 'content', 3, 'post-kotli-05', UNIX_TIMESTAMP() * 1000,
        UNIX_TIMESTAMP() * 1000 + (RAND() * 3600000), false),
       ('Post Java 06', 'content', 1, 'post-java-06', UNIX_TIMESTAMP() * 1000,
        UNIX_TIMESTAMP() * 1000 + (RAND() * 3600000), false),
       ('Post Go 06', 'content', 2, 'post-go-06', UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000 + (RAND() * 3600000),
        false),
       ('Post Kotlin 06', 'content', 3, 'post-kotli-06', UNIX_TIMESTAMP() * 1000,
        UNIX_TIMESTAMP() * 1000 + (RAND() * 3600000), false),
       ('Post Java 07', 'content', 1, 'post-java-7', UNIX_TIMESTAMP() * 1000,
        UNIX_TIMESTAMP() * 1000 + (RAND() * 3600000), false),
       ('Post Go 07', 'content', 2, 'post-go-07', UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000 + (RAND() * 3600000),
        false),
       ('Post Kotlin 07', 'content', 3, 'post-kotli-07', UNIX_TIMESTAMP() * 1000,
        UNIX_TIMESTAMP() * 1000 + (RAND() * 3600000), false),
       ('Post Java 08', 'content', 1, 'post-java-08', UNIX_TIMESTAMP() * 1000,
        UNIX_TIMESTAMP() * 1000 + (RAND() * 3600000), false),
       ('Post Go 08', 'content', 2, 'post-go-08', UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000 + (RAND() * 3600000),
        false);


INSERT INTO db.posts(id, title, content, subject_id, endpoint, created_at, updated_at, activated)
VALUES (50, 'Dummy Test Post Sample Written By Lorem Ipsum', '
<h1 class="content-title-primary">1.0 Title of Something</h1>
Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec id iaculis neque. Phasellus eget odio ut arcu sollicitudin maximus. Donec eget diam eros. Nunc gravida, nunc a blandit porta, sem massa finibus turpis, nec sagittis metus felis sed dolor. Mauris at augue ut ante facilisis laoreet. Suspendisse sit amet turpis ac leo placerat condimentum sit amet sed est. Vestibulum cursus lacus eget ipsum sollicitudin lobortis. Nunc elit dui, hendrerit quis fermentum vitae, euismod sed felis. Nam tempor faucibus tincidunt. Ut condimentum velit sit amet tincidunt viverra. Fusce sed volutpat quam.
<h1 class="content-title-secondary">1.1 Subtitle of Something</h1>
<div class="content-image-inline">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec id iaculis neque. Phasellus eget odio ut arcu sollicitudin maximus. Donec eget diam eros. <p class="content-text-emphasized">Nunc gravida, nunc a blandit porta, sem massa finibus turpis</p>, nec sagittis metus felis sed dolor. Mauris at augue ut ante facilisis laoreet. Suspendisse sit amet turpis ac leo placerat condimentum sit amet sed est. Vestibulum cursus lacus eget ipsum sollicitudin lobortis. Nunc elit dui, hendrerit quis fermentum vitae, euismod sed felis. Nam tempor faucibus tincidunt. Ut condimentum velit sit amet tincidunt viverra. Fusce sed volutpat quam.
<img class="content-image-left" src="/image/image-not-found.png"></div>
<h1 class="content-title-secondary">1.2 Other Subtitle of Something</h1>
Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec id iaculis neque. Phasellus eget odio ut arcu sollicitudin maximus. Donec eget diam eros. <p class="content-text-trivial">Nunc gravida, nunc a blandit porta, sem massa finibus turpis</p>, nec sagittis metus felis sed dolor. Mauris at augue ut ante facilisis laoreet. Suspendisse sit amet turpis ac leo placerat condimentum sit amet sed est. Vestibulum cursus lacus eget ipsum sollicitudin lobortis. Nunc elit dui, hendrerit quis fermentum vitae, euismod sed felis. Nam tempor faucibus tincidunt. Ut condimentum velit sit amet tincidunt viverra. Fusce sed volutpat quam.
<h1 class="content-title-secondary">1.2 Another Subtitle of Something</h1>
Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec id iaculis neque. Phasellus eget odio ut arcu sollicitudin maximus. Donec eget diam eros. <p class="content-text-quote">Nunc gravida, nunc a blandit porta, sem massa finibus turpis</p>, nec sagittis metus felis sed dolor. Mauris at augue ut ante facilisis laoreet. Suspendisse sit amet turpis ac leo placerat condimentum sit amet sed est. Vestibulum cursus lacus eget ipsum sollicitudin lobortis. Nunc elit dui, hendrerit quis fermentum vitae, euismod sed felis. Nam tempor faucibus tincidunt. Ut condimentum velit sit amet tincidunt viverra. Fusce sed volutpat quam.
<h1 class="content-title-secondary">1.3 Another Subtitle of Something</h1>
Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec id iaculis neque. Phasellus eget odio ut arcu sollicitudin maximus. Donec eget diam eros. <p class="content-text-warning">Nunc gravida, nunc a blandit porta, sem massa finibus turpis</p>, nec sagittis metus felis sed dolor. Mauris at augue ut ante facilisis laoreet. Suspendisse sit amet turpis ac leo placerat condimentum sit amet sed est. Vestibulum cursus lacus eget ipsum sollicitudin lobortis. Nunc elit dui, hendrerit quis fermentum vitae, euismod sed felis. Nam tempor faucibus tincidunt. Ut condimentum velit sit amet tincidunt viverra. Fusce sed volutpat quam.
<h1 class="content-title-primary">2.0 Title of A tags</h1>
<a class="content-link">Link</a>
<a class="content-fragment">Fragment</a>
<h1 class="content-title-primary">3.0 Title of Code area</h1>
<pre><code class="language-java">
@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;

    @Override
    public Mono<Subject> save(@NonNull Subject subject) {
        return subjectRepository.save(subject.withId(0));
    }

    @Override
    public Flux<Subject> findAll() {
        return subjectRepository.findAll();
    }

    @Override
    public Flux<SubjectDeep> findAllDeep() {
        return subjectRepository.findAllDeep();
    }

    @Override
    public Mono<Subject> findById(int id) throws IllegalValueException {
        return subjectRepository.findById(id)
                .switchIfEmpty(Mono.error(() ->
                        new IllegalValueException("No subject found by given id", SubjectRepository.TABLE_NAME, "id", id)));
    }

    @Override
    public Mono<SubjectDeep> findByNameDeep(@NonNull String name) throws IllegalValueException {
        return subjectRepository.findByNameDeep(name)
                .switchIfEmpty(Mono.error(() ->
                        new IllegalValueException("No subject found by given id", SubjectRepository.TABLE_NAME, "name", name)));
    }

    @Override
    public Mono<Subject> update(int id, @NonNull Subject subject) throws IllegalValueException {
        if (id < 1 || subject.getName() == null) throw new IllegalArgumentException();
        return subjectRepository.findById(id)
                .switchIfEmpty(Mono.error(() ->
                        new IllegalValueException("Subject not available by given id", SubjectRepository.TABLE_NAME, "id", id)))
                .flatMap(savedSubject -> {
                    savedSubject.setName(subject.getName());
                    return subjectRepository.update(savedSubject);
                });
    }

    @Override
    public Mono<Void> deleteById(int id) throws IllegalValueException {
        if (id < 1) throw new IllegalArgumentException();
        return subjectRepository.findById(id)
                .switchIfEmpty(Mono.error(() ->
                        new IllegalValueException("Image not available by given id", SubjectRepository.TABLE_NAME, "id", id)))
                .then(subjectRepository.deleteById(id));
    }

}
</code></pre>
', 1, 'dummy-test-post-example', UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000 + (3600000), true);


INSERT INTO db.tags (id, name)
VALUES (1, 'tag1'),
       (2, 'tag2'),
       (3, 'tag3');

INSERT INTO db.tags_posts_join
VALUES (50, 1),
       (50, 2);

