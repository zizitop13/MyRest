package com.example.myrest.sql.common;

import com.example.myrest.sql.api.Dao;
import com.example.myrest.sql.api.Filter;
import com.example.myrest.sql.api.InsertRecord;
import com.example.myrest.sql.api.MyTable;
import com.example.myrest.sql.api.SQL;
import com.example.myrest.sql.api.UpdateRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommonDao implements Dao {

    private final JdbcTemplate jdbcTemplate;

    private final SQL sqlFactory;

    @Override
    public int insert(MyTable table, InsertRecord insertRecord) {
        String insert = sqlFactory.insert(table, insertRecord);
        log.info("Insert query: {}", insert);
        return jdbcTemplate.update(insert);
    }

    @Override
    public List<Map<String, Object>> select(MyTable table, Filter filter, Pageable pageable) {
        String selectQuery = sqlFactory.select(table, filter, pageable);
        log.info("Select query: {}", selectQuery);
        return jdbcTemplate.queryForList(selectQuery);
    }

    @Override
    public int update(MyTable table, UpdateRecord updateRecord) {
        String update = sqlFactory.update(table, updateRecord);
        log.info("Update query: {}", update);
        return jdbcTemplate.update(update);
    }

    @Override
    public int delete(MyTable table, Filter filter) {
        String delete = sqlFactory.delete(table, filter);
        log.info("Delete query: {}", delete);
        return jdbcTemplate.update(delete);
    }
}
