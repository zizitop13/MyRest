package com.example.myrest.sql.api;

import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

public class MyPrimaryKey {

    @Getter
    private final List<MyColumn> columns;

    public MyPrimaryKey(List<MyColumn> columns) {
        this.columns = columns.stream()
                .filter(MyColumn::isPrimaryKey)
                .collect(Collectors.toList());
    }
}
