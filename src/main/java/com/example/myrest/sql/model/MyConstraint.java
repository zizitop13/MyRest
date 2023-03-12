package com.example.myrest.sql.model;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor
public class MyConstraint {
    private final String name;
    private final String referencedTableName;
    private final String referencedColumnName;
    private final String columnName;
}
