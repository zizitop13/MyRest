package com.example.myrest.sql.mysql;

import com.example.myrest.sql.api.Filter;
import com.example.myrest.sql.api.MyTable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MyDelete {

    private final JdbcTemplate jdbcTemplate;

    public int execute(MyTable myTable, String pk) {
        String update = myTable.delete(pk);
        log.info("Delete query: {}", update);
        return jdbcTemplate.update(update);
    }
}
