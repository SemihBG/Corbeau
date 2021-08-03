package com.semihbkgr.corbeau.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class DataSourceExtension implements BeforeAllCallback, AfterEachCallback, ExtensionContext.Store.CloseableResource {

    private static final AtomicBoolean DATASOURCE_DDL_EXECUTED = new AtomicBoolean(false);

    @Override
    public void beforeAll(ExtensionContext extensionContext) {
        if (!DATASOURCE_DDL_EXECUTED.get()) {
            DATASOURCE_DDL_EXECUTED.set(true);
            DataSourceUtils.executeSQL(
                    TestConstnats.DATA_SOURCE_URL,
                    Path.of(TestConstnats.TEST_RESOURCE_RELATIVE_DIRECTORY,
                            TestConstnats.DATA_SOURCE_DDL_SQL_FILE)
            );
            log.info("Datasource tables created");
        }
    }

    @Override
    public void afterEach(ExtensionContext extensionContext) {
        DataSourceUtils.executeSQL(
                TestConstnats.DATA_SOURCE_URL,
                Path.of(TestConstnats.TEST_RESOURCE_RELATIVE_DIRECTORY,
                        TestConstnats.DATA_SOURCE_CLEAR_SQL_FILE)
        );
        log.info("Datasource tables cleared");
    }

    @Override
    public void close() {
        DataSourceUtils.executeSQL(
                TestConstnats.DATA_SOURCE_URL,
                Path.of(TestConstnats.TEST_RESOURCE_RELATIVE_DIRECTORY,
                        TestConstnats.DATA_SOURCE_DROP_SQL_FILE)
        );
        log.info("Datasource tables dropped");
    }

}
