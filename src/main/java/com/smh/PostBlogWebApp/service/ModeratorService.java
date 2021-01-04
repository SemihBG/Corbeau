package com.smh.PostBlogWebApp.service;

import com.smh.PostBlogWebApp.entity.Moderator;

public interface ModeratorService {

    Moderator findById(int id);
    Moderator save(Moderator moderator);

}
