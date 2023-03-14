package com.example.myrest.sql.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Getter
@Setter
@Slf4j
@ToString
@RequiredArgsConstructor
public class MySchema {

    private final String name;
    private List<MyTable> tables;

    public MySchema print(){
        log.info("Database schema: {}", this);
        return this;
    }

}