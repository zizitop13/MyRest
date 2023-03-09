package com.example.myrest.sql.crud;

import com.example.myrest.sql.model.MyTable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class MyInsert {

    private final JdbcTemplate jdbcTemplate;

    public int execute(MyTable myTable, Map<String, String> updateMap) {
        String insert = myTable.insert(updateMap);
        log.info("Insert query: {}", insert);
        return jdbcTemplate.update(insert);
    }

}
