package com.example.myrest.sql.mysql;

import com.example.myrest.sql.api.Filter;
import com.example.myrest.sql.api.InsertRecord;
import com.example.myrest.sql.api.MyTable;
import com.example.myrest.sql.api.Dao;
import com.example.myrest.sql.api.UpdateRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class MySQLDao implements Dao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public int select(MyTable table, InsertRecord insertRecord) {
        return 0;
    }

    @Override
    public List<Map<String, Object>> select(MyTable table, Filter filter) {
        return null;
    }

    @Override
    public int update(MyTable table, UpdateRecord updateRecord) {
        return 0;
    }

    @Override
    public int delete(MyTable table, Filter filter) {
        return 0;
    }
}
