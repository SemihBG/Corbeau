package com.smh.PostBlogWebApp.service;

import com.smh.PostBlogWebApp.entity.Moderator;
import com.smh.PostBlogWebApp.repository.ModeratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ModeratorServiceImpl implements ModeratorService {

    @Autowired
    private ModeratorRepository moderatorRepository;

    @Nullable
    @Override
    public Moderator findById(int id) {
        return moderatorRepository.findById(id).orElse(null);
    }

    @Override
    public Moderator save(Moderator moderator) {
        return moderatorRepository.save(Objects.requireNonNull(moderator));
    }

}
