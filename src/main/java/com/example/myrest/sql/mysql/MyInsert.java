package com.example.myrest.sql.mysql;

import com.example.myrest.sql.api.MyTable;
import com.example.myrest.sql.api.InsertRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MyInsert {

    private final JdbcTemplate jdbcTemplate;

    public int execute(MyTable myTable, InsertRecord insertRecord) {
        String insert = myTable.insert(insertRecord);
        log.info("Insert query: {}", insert);
        return jdbcTemplate.update(insert);
    }

}
