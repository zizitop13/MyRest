package com.example.myrest.sql.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class MyTable {

    private final String name;
    private List<MyColumn> columns;
    private List<MyConstraint> constraints;
    private MyPrimaryKey pk;

    public void setColumns(List<MyColumn> columns){
        this.columns = columns;
        this.pk = new MyPrimaryKey(columns);
    }


}
