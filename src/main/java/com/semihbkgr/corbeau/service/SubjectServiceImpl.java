package com.semihbkgr.corbeau.service;

import com.semihbkgr.corbeau.model.Subject;
import com.semihbkgr.corbeau.repository.SubjectRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;

    @Override
    public Mono<Subject> save(@NonNull Subject subject) {
        return subjectRepository.save(subject);
    }

    /*
    private static final String CACHE_NAME="subject";
    private static final String CACHE_ALL_NAME="subjectAll";

    @Autowired
    private SubjectRepository subjectRepository;

    @Caching(
            evict = {
                    @CacheEvict(cacheNames = CACHE_ALL_NAME,allEntries = true)
            },
            put = {
                    @CachePut(cacheNames = CACHE_NAME,key = "#subject.name"),
                    @CachePut(cacheNames = CACHE_NAME,key = "#subject.urlEndpoint")
            }
    )
    @Override
    public Subject save(Subject subject) {
        return subjectRepository.save(Objects.requireNonNull(subject));
    }

    @Cacheable(cacheNames = CACHE_ALL_NAME)
    @Override
    public List<Subject> findAll() {
        return StreamSupport.stream(subjectRepository.findAll().spliterator(),false).collect(Collectors.toList());
    }

    @Cacheable(cacheNames = CACHE_NAME)
    @Nullable
    @Override
    public Subject findByUrlEndpoint(String url) {
        return subjectRepository.findByUrlEndpoint(Objects.requireNonNull(url));
    }

    @Cacheable(cacheNames = CACHE_NAME)
    @Nullable
    @Override
    public Subject findByName(String name) {
        return subjectRepository.findByName(Objects.requireNonNull(name));
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = CACHE_ALL_NAME,allEntries = true),
            @CacheEvict(cacheNames = CACHE_NAME)
        }
    )
    @Transactional
    @Override
    public void deleteByName(String name) {
        subjectRepository.deleteByName(Objects.requireNonNull(name));
    }
    */
}
