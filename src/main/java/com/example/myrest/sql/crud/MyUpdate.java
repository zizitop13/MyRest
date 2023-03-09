package com.example.myrest.sql.crud;

import com.example.myrest.sql.model.MyTable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class MyUpdate {

    private final JdbcTemplate jdbcTemplate;

    public int execute(MyTable myTable, Map<String, String> updateMap) {
        String update = myTable.update(updateMap);
        log.info("Update query: {}", update);
        return jdbcTemplate.update(update);
    }
}
