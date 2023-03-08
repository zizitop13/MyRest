package com.example.myrest.sql.model;

import com.example.myrest.sql.model.MyColumn;
import com.example.myrest.sql.model.MyConstraint;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class MyTable {

    private final String name;
    private List<MyColumn> columns;
    private List<MyConstraint> constraints;
}
