package com.example.myrest.sql.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class MySchema {

    private final String name;
    private List<MyTable> tables;

}