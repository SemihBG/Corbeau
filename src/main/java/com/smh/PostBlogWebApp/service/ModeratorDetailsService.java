package com.smh.PostBlogWebApp.service;

import com.smh.PostBlogWebApp.entity.Moderator;
import com.smh.PostBlogWebApp.repository.ModeratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class ModeratorDetailsService implements UserDetailsService {

    @Autowired
    private ModeratorRepository moderatorRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        Moderator moderator=moderatorRepository.findByUsername(s).orElseThrow(
                () -> new UsernameNotFoundException("No such moderator with username '"+s+"'")
        );

        return new User(moderator.getUsername(),moderator.getPassword(),moderator.isEnabled(),
                true,true,true,getGrantedAuthorities(moderator));

    }

    private Collection<GrantedAuthority> getGrantedAuthorities(Moderator moderator){
        List<GrantedAuthority> authorities=new ArrayList<>();
        moderator.getPermissionList().forEach(permission -> {
            authorities.add(new SimpleGrantedAuthority(permission.getName()));
        });
        return authorities;
    }

}
