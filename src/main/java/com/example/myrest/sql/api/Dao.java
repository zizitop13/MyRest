package com.example.myrest.sql.api;

import java.util.List;
import java.util.Map;

public interface Dao {

    int select(MyTable table, InsertRecord insertRecord);
    List<Map<String, Object>> select(MyTable table, Filter filter);
    int update(MyTable table, UpdateRecord updateRecord);
    int delete(MyTable table, Filter filter);

}
