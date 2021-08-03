package com.semihbkgr.corbeau.test;

import io.r2dbc.spi.ConnectionFactories;
import lombok.SneakyThrows;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.StreamSupport;

public class DataSourceUtils {

    public static void executeSQL(String url, String sql) {
        Mono.from(ConnectionFactories.get(url).create())
                .flatMapMany(c -> c.createStatement(sql).execute())
                .blockLast();
    }

    public static void executeSQL(String url, String... sqls) {
        Mono.from(ConnectionFactories.get(url).create())
                .flatMapMany(c ->
                        Flux.fromStream(Arrays.stream(sqls))
                        .flatMap(sql-> c.createStatement(sql).execute()))
                .blockLast();
    }

    @SneakyThrows
    public static void executeSQL(String url, Path path) {
        var lineList = Files.readAllLines(path);
        var sqlList = extractSqlList(lineList);
        executeSQL(url,sqlList.toArray(new String[0]));
    }

    private static List<String> extractSqlList(List<String> lineList) {
        var sqlList = new ArrayList<String>();
        var sqlSB = new StringBuilder();
        for (String line : lineList) {
            if (line.contains(";")) {
                var index = line.indexOf(";");
                sqlSB.append(line.substring(0, index));
                sqlList.add(sqlSB.toString().trim());
                sqlSB.setLength(0);
                if (index + 1 < line.length())
                    sqlSB.append(line.substring(index));
            } else {
                sqlSB.append(line);
            }
        }
        return sqlList;
    }

}
