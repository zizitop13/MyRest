package com.example.myrest.sql.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class MyColumn {
    private final String name;
    private final String type;
    private final int size;
    private final boolean nullable;
    private final boolean primaryKey;
}
