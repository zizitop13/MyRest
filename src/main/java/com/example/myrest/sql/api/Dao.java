package com.example.myrest.sql.api;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface Dao {

    int insert(MyTable table, InsertRecord insertRecord);
    List<Map<String, Object>> select(MyTable table, Filter filter, Pageable pageable);
    int update(MyTable table, UpdateRecord updateRecord);
    int delete(MyTable table, Filter filter);

}
