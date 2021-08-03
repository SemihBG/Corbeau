package com.semihbkgr.corbeau.service;

import com.semihbkgr.corbeau.test.DatasourceUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.nio.file.Path;

public class sad {

    @BeforeAll
    static void asd() {
         var p= Path.of("./src/test/resources/populator.sql");
         var url="r2dbc:h2:file:///./testdb;DATABASE_TO_UPPER=false;AUTO_SERVER=TRUE;USER=sa;PASSWORD=sa";
         DatasourceUtils.executeSQL(url,p);

    }

}
