package com.example.myrest.sql.api;

import org.springframework.data.domain.Pageable;

public interface SQL {
    String select(MyTable table, Filter filter, Pageable pageable);
    String insert(MyTable table, InsertRecord insertRecord);
    String update(MyTable table, UpdateRecord updateRecord);
    String delete(MyTable table, Filter filter);
}
