package com.semihbkgr.corbeau.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.boot.test.context.SpringBootTest;

@DataR2dbcTest
class RoleRepositoryTest {

    @Autowired
    RoleRepository roleRepository;

    @Test
    void test(){
        roleRepository.findByName("asd").subscribe(i->{
            System.out.println(i);
        });
    }


}