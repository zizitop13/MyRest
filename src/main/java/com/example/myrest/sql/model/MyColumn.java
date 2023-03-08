package com.example.myrest.sql.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MyColumn {
    private final String name;
    private final String type;
    private final int size;
    private final boolean nullable;
}
